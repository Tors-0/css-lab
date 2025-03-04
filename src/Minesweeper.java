import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A class that can be instantiated and given bindings for input and output
 * in order to play Minesweeper via text.
 * @author Rae Johnston
 */
public class Minesweeper {

    // format string escape codes
    static final String resetCode = "\033[0m";
    static final String underline = "\033[37;4m";
    static final String tile1Code = "\033[30;42;1m"; // green bg
    static final String tile2Code = "\033[30;43;1m"; // yellow bg
    static final String tile3Code = "\033[30;41;1m"; // red bg
    static final String tile4Code = "\033[30;46;1m"; // cyan bg
    static final String tile5Code = "\033[30;44;1m"; // blue bg
    static final String tile6Code = "\033[30;45;1m"; // purple bg
    static final String tile7Code = "\033[30;47;1m"; // white bg
    static final String tile8Code = "\033[33;40;1m"; // nuclear

    // easy difficulty
    public static final int EASY_MINES = 10;
    public static final int EASY_WIDTH = 9;
    public static final int EASY_HEIGHT = 9;

    // medium difficulty
    public static final int MEDIUM_MINES = 40;
    public static final int MEDIUM_WIDTH = 16;
    public static final int MEDIUM_HEIGHT = 16;

    // hard difficulty
    public static final int HARD_MINES = 99;
    public static final int HARD_WIDTH = 30;
    public static final int HARD_HEIGHT = 16;

    // bonus difficulties
    public static final int CHALLENGE_MINES = 127;
    public static final int LUDICROUS_MINES = 192;

    // board properties
    char[] gameBoard;
    boolean[] revealGameBoard;
    boolean[] debugRevealGameBoard;
    boolean isDebugMode = false;
    int boardWidth;
    int boardHeight;

    // board icon info
    int mines = 0;
    int minesRemaining;
    static final char MINE_CHAR = 'X';
    static final char FLAG_CHAR = '\u2691';
    final char BLANK_CHAR = ' ';
    final String UNOPENED_SPACE = "[\u25a0]";

    int difficulty;

    boolean gameOverBad = false;
    boolean gameOverWin = false;
    // track this so user cant lose on turn 1
    boolean isFirstClick = true;
    boolean invalidInput = false;

    // timekeeping for game score
    long startTime;
    long totalTime;

    // board tile indices for renumbering board
    List<Integer> mineLocations = new ArrayList<>();
    List<Integer> emptyBoardSpaces = new ArrayList<>();

    // bindings for input and output stream
    PrintStream[] output;
    Scanner input;

    /**
     * Creates a new game of Minesweeper that can be played in a terminal
     * @param inputBinding should be a form of live input, else the player will
     *                     struggle to play the game
     * @param outputBinding should be in a terminal/terminal emulator that
     *                      accepts ANSI color format codes
     */
    public Minesweeper(Scanner inputBinding, PrintStream... outputBinding) {
        input = inputBinding;
        output = outputBinding;
    }

    public static void main(String[] args) {
        Minesweeper myGame = new Minesweeper(
                new Scanner(System.in),
                System.out
        );
        myGame.startGame();
    }

    private void multiPrint(Object out) {
        for (PrintStream printer : output) {
            printer.print(out);
        }
    }

    private void multiPrintln(Object out) {
        for (PrintStream printer : output) {
            printer.println(out);
        }
    }

    private void multiPrintf(String format, Object... args) {
        for (PrintStream printer : output) {
            printer.printf(format, args);
        }
    }

    private void startGame() {
        getUserDifficultyChoice();

        initializeGameState(difficulty);

        populateMinefield();

        populateBoardNumbers();

        startTime = System.currentTimeMillis();

        do {
            // user input, input parsing, turn execution
            gameStateUpdate();

            if (!invalidInput) { // only if the update succeeded
                // check if all non-mine tiles have been discovered
                gameOverWin = evaluateGameWin();
            }
        } while (!gameOverBad && !gameOverWin);

        revealBoardOnGameOver();

        closeBindings();
    }

    private void closeBindings() {
        for (PrintStream printer : output) {
            // don't close system.out prematurely
            if (!printer.equals(System.out)) {
                printer.close();
            }
        }
        input.close();
    }

    /**
     * uses and modifies the instance variables of a {@link Minesweeper}
     * object in order to update the game state by one turn.
     * <br><br>
     * example:
     * <pre>
     * {@code
     *  while (gameNotOver) {
     *      gameStateUpdate();
     *
     *      // update gameNotOver
     *  }
     * }
     * </pre>
     */
    private void gameStateUpdate() {
        if (invalidInput) {
            multiPrintln("Invalid Input!");
            invalidInput = false;
        }

        if (!isDebugMode) {
            debugRevealGameBoard = revealGameBoard.clone();
        }

        printGameBoard();

        // collect user input
        multiPrintln("Mines left: " + minesRemaining);
        multiPrint("What is your choice (\"help\" for help): ");
        String choice = input.nextLine();
        choice = choice.toLowerCase();

        // parse user input
        if ("help".equals(choice)) {
            printHelpGuide();
        } else if (choice.matches("(mark|dig|m|d) [a-z][0-9]{1,2}")) {
            evaluateUserAction(choice);
        } else if (choice.startsWith("debug.")) {
            evaluateDebugAction(choice);
        } else {
            invalidInput = true;
        }
    }

    private void evaluateDebugAction(String choice) {
        if ("debug.open".equals(choice)) {
            debugRevealGameBoard = revealGameBoard.clone();
            Arrays.fill(revealGameBoard, true);
            isDebugMode = true;
        } else if ("debug.close".equals(choice)) {
            revealGameBoard = debugRevealGameBoard.clone();
            isDebugMode = false;
        }
    }

    private boolean evaluateGameWin() {
        if (isDebugMode) return false;

        for (Integer i : emptyBoardSpaces) {
            // if any empty space hasn't been turned, the game isn't over
            if (!revealGameBoard[i]) return false;
        }

        // if we pass every empty space without finding an unturned one
        return true;
    }

    private void evaluateUserAction(String choice) {
        String[] choices = choice.split(" ");
        String action = choices[0];
        int vertical = (int) (choices[1].charAt(0) - 'a');
        int horizontal = Integer.parseInt(choices[1].substring(1));

        if (vertical >= boardHeight) {
            invalidInput = true;
            return;
        }
        if (horizontal >= boardWidth) {
            invalidInput = true;
            return;
        }

        int index = vertical * boardWidth + horizontal;
        if ("m".equals(action) || "mark".equals(action)) {
            executeMarkAction(index);
        } else if ("d".equals(action) || "dig".equals(action)) {
            executeDigAction(index);
            isFirstClick = false;
        } else {
            invalidInput = true;
        }
    }

    private void executeMarkAction(int i) {
        if (gameBoard[i] == FLAG_CHAR) {
            minesRemaining++;
            gameBoard[i] = BLANK_CHAR;
            revealGameBoard[i] = false;
            populateBoardNumbers();
        } else {
            gameBoard[i] = FLAG_CHAR;
            minesRemaining--;
            revealGameBoard[i] = true;
        }
    }

    private void executeDigAction(int index) {
        if (mineLocations.contains(index)) {
            if (isFirstClick) {
                moveMineOnFirstClick(index);
            } else {
                gameOverBad = true;
            }
        } else {
            if (gameBoard[index] == FLAG_CHAR) {
                executeMarkAction(index);
            }
            if (!revealGameBoard[index] && gameBoard[index] != MINE_CHAR) {
                propagateBoardDiscovery(index);
            }
        }
    }

    private void printHelpGuide() {
        multiPrintln("""
                To flag a square, use "mark a5" or "m a5".\

                To dig a square, use "dig a5" or "d a5".\

                Capitalization does not matter.""");
    }

    private void propagateBoardDiscovery(int i) {
        if (revealGameBoard[i]) return; // early return if tile already open
        revealGameBoard[i] = true;

        // early return if tile has number
        if (gameBoard[i] != BLANK_CHAR && !isFirstClick) return;
        isFirstClick = false;

        boolean onLeftEdge = i % boardWidth == 0;
        boolean onRightEdge = (i + 1) % boardWidth == 0;
        boolean onTopEdge = i < boardWidth;
        boolean onBottomEdge = i + boardWidth >= gameBoard.length;

        int left = onLeftEdge ? 0 : -1; // how many left to search
        int top = onTopEdge ? 0 : -1; // how many up to search
        int right = onRightEdge ? 0 : 1; // how many right to search
        int bottom = onBottomEdge ? 0 : 1; // how many down to search

        for (int l = left; l <= right; l++) {
            for (int w = top; w <= bottom; w++) {
                int index = i + (l) + (w * boardWidth);
                if (gameBoard[index] != MINE_CHAR && !revealGameBoard[index]) {
                    propagateBoardDiscovery(index);
                }
            }
        }
    }

    private void moveMineOnFirstClick(int index) {
        char c;
        int i = -1;
        do { // find a non-mine spot on the board
            i++;
            c = gameBoard[i];
        } while (c == MINE_CHAR);

        // swap the empty spot with the dug spot
        char temp = gameBoard[index];
        gameBoard[index] = gameBoard[i];
        gameBoard[i] = temp;

        // remark the empty and mine locations on the board
        emptyBoardSpaces.add(index);
        emptyBoardSpaces.remove((Integer) i);
        mineLocations.add(i);
        mineLocations.remove((Integer) index);

        revealGameBoard[index] = true;

        // recalculate the board numbers
        populateBoardNumbers();
    }

    private void revealBoardOnGameOver() {
        multiPrint("\n\n Opening Final Game Board...\n\n");

        if (gameOverWin) {
            for (Integer i : emptyBoardSpaces) {
                gameBoard[i] = BLANK_CHAR;
            }
        }

        Arrays.fill(revealGameBoard, true);
        printGameBoard();

        if (gameOverWin) {
            totalTime = System.currentTimeMillis() - startTime;
            multiPrintln("You win!\nYour time: " + (totalTime / 1000));
            multiPrintln("Mines flagged: " +
                    (mines - minesRemaining) + " / " + mines);
            multiPrintln("Avg time/mine: " + (totalTime / 1000 / mines));

        } else if (gameOverBad) {
            multiPrintln("Game over.");
        }
    }

    private void printGameBoard() {
        char c = 'a';
        for (int i = -1; i < boardHeight; i++) {
            for (int j = -1; j < boardWidth; j++) {
                c = printGameBoardChar(i, j, c);
            }
            multiPrintln(""); // newline after printing full board row
        }
    }

    private char printGameBoardChar(int i, int j, char c) {
        if (i == -1) {
            if (j == -1) {
                multiPrint("   ");
            } else {
                multiPrintf("%s%3s%s", underline, j, resetCode);
            }
        } else if (j == -1) {
            multiPrintf("%3s", c + "|");
            c++;
        } else {
            int index = (i * boardWidth) + j;
            char current = gameBoard[index];
            if (current == MINE_CHAR) {
                multiPrintf("%3s",
                        gameOverBad ? formatTile(current) :
                                UNOPENED_SPACE
                );
            } else {
                if (revealGameBoard[index]) {
                    multiPrint(formatTile(current));
                } else {
                    multiPrint(UNOPENED_SPACE);
                }
            }
        }
        return c;
    }

    private static String formatTile(char num) {
        return switch (num) {
            case '1' -> tile1Code + " " + num + " " + resetCode;
            case '2' -> tile2Code + " " + num + " " + resetCode;
            case '3' -> tile3Code + " " + num + " " + resetCode;
            case '4' -> tile4Code + " " + num + " " + resetCode;
            case '5' -> tile5Code + " " + num + " " + resetCode;
            case '6' -> tile6Code + " " + num + " " + resetCode;
            case '7' -> tile7Code + " " + num + " " + resetCode;
            case '8' -> tile8Code + " " + num + " " + resetCode;
            case MINE_CHAR -> "{" + MINE_CHAR + "}";
            case FLAG_CHAR -> " " + FLAG_CHAR + " ";
            default -> "   ";
        };
    }

    private void populateBoardNumbers() {
        repopulateMinefield();

        for (int i = 0; i < gameBoard.length; i++) {
            // skip if this square is a mine
            if (MINE_CHAR == gameBoard[i] || FLAG_CHAR == gameBoard[i]) {
                continue;
            }

            boolean onLeftEdge = i % boardWidth == 0;
            boolean onRightEdge = (i + 1) % boardWidth == 0;
            boolean onTopEdge = i < boardWidth;
            boolean onBottomEdge = i + boardWidth >= gameBoard.length;

            int left = onLeftEdge ? 0 : -1; // how many left to search
            int top = onTopEdge ? 0 : -1; // how many up to search
            int right = onRightEdge ? 0 : 1; // how many right to search
            int bottom = onBottomEdge ? 0 : 1; // how many down to search

            int adjacentMines = 0;

            for (int l = left; l <= right; l++) {
                for (int w = top; w <= bottom; w++) {
                    if (mineLocations.contains(i + (l) + (w * boardWidth))) {
                        adjacentMines++;
                    }
                }
            }

            if (adjacentMines > 0) {
                gameBoard[i] = String.valueOf(adjacentMines).charAt(0);
            }
        }
    }

    private void populateMinefield() {
        for (int i = 0; i < gameBoard.length; i++) {
            emptyBoardSpaces.add(i);
        }
        for (int i = 0; i < mines; i++) {
            int index = emptyBoardSpaces.get(
                    (int) (Math.random() * emptyBoardSpaces.size()));
            gameBoard[index] = MINE_CHAR;
            emptyBoardSpaces.remove((Integer) index);
            mineLocations.add(index);
        }
    }

    private void repopulateMinefield() {
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] != FLAG_CHAR) {
                gameBoard[i] = BLANK_CHAR;
            }
        }
        for (Integer mineLocation : mineLocations) {
            if (gameBoard[mineLocation] != FLAG_CHAR) {
                gameBoard[mineLocation] = MINE_CHAR;
            }
        }
    }

    private void getUserDifficultyChoice() {
        String diffString;
        while (true) {
            multiPrintln("Please select a difficulty (0-4): ");
            diffString = input.nextLine();
            String[] legalDifficulties = {"0", "1", "2", "3", "4", "-1"};
            if (Arrays.asList(legalDifficulties).contains(diffString)) {
                break;
            }
            multiPrint("Invalid input: ");
        }
        difficulty = Integer.parseInt(diffString);
    }

    private void initializeGameState(int difficulty) {
        String difficultyStr = "";
        switch (difficulty) {
            case 0 -> {
                boardHeight = EASY_HEIGHT;
                boardWidth = EASY_WIDTH;
                mines = EASY_MINES;
                difficultyStr = "Easy";
            }
            case 1 -> {
                boardHeight = MEDIUM_HEIGHT;
                boardWidth = MEDIUM_WIDTH;
                mines = MEDIUM_MINES;
                difficultyStr = "Medium";
            }
            case 2 -> {
                boardHeight = HARD_HEIGHT;
                boardWidth = HARD_WIDTH;
                mines = HARD_MINES;
                difficultyStr = "Hard";
            }
            case 3 -> {
                boardHeight = HARD_HEIGHT;
                boardWidth = HARD_WIDTH;
                mines = CHALLENGE_MINES;
                difficultyStr = "Challenge";
            }
            case 4 -> {
                boardHeight = HARD_HEIGHT;
                boardWidth = HARD_WIDTH;
                mines = LUDICROUS_MINES;
                difficultyStr = "Ludicrous";
            }
            default -> {
                multiPrintln("Custom difficulty:\n" +
                        "Enter board height (1-26) and width (1-99)," +
                        "separated by spaces (whole numbers only):");
                boardHeight = input.nextInt();
                boardWidth = input.nextInt();
                input.nextLine();

                float minePercentage = (float) ((Math.random() * 0.28) + 0.12);
                mines = (int) (boardWidth * boardHeight * minePercentage);
                difficultyStr = "Custom";
            }
        }
        gameBoard = new char[boardHeight * boardWidth];
        revealGameBoard = new boolean[boardHeight * boardWidth];
        debugRevealGameBoard = new boolean[boardHeight * boardWidth];
        Arrays.fill(gameBoard, BLANK_CHAR);
        Arrays.fill(revealGameBoard, false);

        minesRemaining = mines;

        multiPrintf("You have selected %s difficulty.\n", difficultyStr);
    }
}
