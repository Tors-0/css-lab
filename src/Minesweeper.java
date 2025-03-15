import java.io.File;
import java.io.IOException;
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
    boolean[][] revealBoards;
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
    double totalTime;

    // board tile indices for renumbering board
    List<Integer> mineLocations = new ArrayList<>();
    List<Integer> emptyBoardSpaces = new ArrayList<>();

    // bindings for input and output stream
    PrintStream[] output;
    Scanner input;

    // magic number avoidance
    private final int REVEAL_BOARD_INDEX = 0;
    private final int DEBUG_REVEAL_BOARD_INDEX = 1;

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

    /**
     * .print() functionality for multiple print streams
     *
     * @param out the value to be printed
     */
    private void multiPrint(Object out) {
        for (PrintStream printer : output) {
            printer.print(out);
        }
    }

    /**
     * .println() functionality for multiple print streams
     *
     * @param out the value to be printed
     */
    private void multiPrintln(Object out) {
        for (PrintStream printer : output) {
            printer.println(out);
        }
    }

    /**
     * .printf() functionality for multiple print streams
     *
     * @param format the string to be formatted and printed
     * @param args the arguments to the format string
     */
    private void multiPrintf(String format, Object... args) {
        for (PrintStream printer : output) {
            printer.printf(format, args);
        }
    }

    /**
     * runs the full scope of the game. creates the board, picks difficulty,
     * executes multiple turns until the game is won or lost, and then reveals
     * the board and closes the print streams and input stream.
     */
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

    /**
     * Sensitively closes the bound {@link PrintStream}s and input
     * {@link Scanner}. <br>
     * Will not close the PrintStream if it is {@link System#out} to allow
     * the program to continue printing to console if necessary
     */
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
            revealBoards[DEBUG_REVEAL_BOARD_INDEX] =
                    revealBoards[REVEAL_BOARD_INDEX].clone();
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

    /**
     * If the user enters a String starting with "{@code debug.}" for their move,
     * this method will validate that action and modify the board to reflect it
     * @param choice the user-entered String to evaluate
     */
    private void evaluateDebugAction(String choice) {
        if ("debug.open".equals(choice)) {
            revealBoards[DEBUG_REVEAL_BOARD_INDEX] =
                    revealBoards[REVEAL_BOARD_INDEX].clone();
            Arrays.fill(revealBoards[REVEAL_BOARD_INDEX], true);
            isDebugMode = true;
        } else if ("debug.close".equals(choice)) {
            revealBoards[REVEAL_BOARD_INDEX] =
                    revealBoards[DEBUG_REVEAL_BOARD_INDEX].clone();
            isDebugMode = false;
        }
    }

    /**
     * Checks if the player has won the game.
     * <br><br>
     * Win Condition: All empty spaces (those without bombs) have been dug
     * @return true if the game is won, false otherwise
     */
    private boolean evaluateGameWin() {
        if (isDebugMode) return false;

        for (Integer i : emptyBoardSpaces) {
            // if any empty space hasn't been turned, the game isn't over
            if (!revealBoards[REVEAL_BOARD_INDEX][i]) return false;
        }

        // if we pass every empty space without finding an unturned one
        return true;
    }

    /**
     * Takes in a String representing the user's action this turn, then
     * evaluates the outcome of that action and executes it
     * @param choice the String from user input
     */
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

    /**
     * Flag the tile number passed into this method. tiles are numbered from 0,
     * starting at the top left tile and increasing rightwards, with each row
     * looping down to the next row
     * @param index tile number (should be parsed from user action, otherwise
     *              good luck figuring out what tile is what number)
     */
    private void executeMarkAction(int index) {
        if (gameBoard[index] == FLAG_CHAR) {
            minesRemaining++;
            gameBoard[index] = BLANK_CHAR;
            revealBoards[REVEAL_BOARD_INDEX][index] = false;
            populateBoardNumbers();
        } else {
            gameBoard[index] = FLAG_CHAR;
            minesRemaining--;
            revealBoards[REVEAL_BOARD_INDEX][index] = true;
        }
    }

    /**
     * Dig up the tile number passed into this method. tiles are numbered from
     * 0, starting at the top left tile and increasing rightwards, with each row
     * looping down to the next row
     * @param index tile number (should be parsed from user action, otherwise
     *             good luck figuring out what tile is what number)
     */
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
            if (!revealBoards[REVEAL_BOARD_INDEX][index] &&
                    gameBoard[index] != MINE_CHAR) {
                propagateBoardDiscovery(index);
            }
        }
    }

    /**
     * A simple guide that tells the player how to format their input to
     * match the parsing syntax
     */
    private void printHelpGuide() {
        multiPrintln("""
                To flag a square, use "mark a5" or "m a5".\

                To dig a square, use "dig a5" or "d a5".\

                Capitalization does not matter.""");
    }

    /**
     * When a player digs up a tile, if that tile has no mines and no number,
     * any adjacent empty or number tiles should be revealed in order to reduce
     * the amount of blind digging required and also speed up the game.
     * <br>
     * This method is called recursively to create a depth-first search, however
     * this method also checks whether a tile was revealed already in order to
     * avoid an infinite recursion loop that would create a memory leak and
     * crash the program.
     * <br>
     * If the user clicks on a tile with a number, but it is the first turn
     * still, we should reveal nearby tiles anyway in order to avoid requiring
     * the player to blindly dig around their first guess in following turns.
     * <br><br>
     * (This is done because I didn't feel like implementing a better solution)
     * <br>
     * (For future reference that solution would be to not place any mines
     * until after the user makes their first click, and blacklisting the area
     * around the first tile from getting any mines, then revealing as normal)
     * @param i the starting tile for this propagation operation.
     */
    private void propagateBoardDiscovery(int i) {
        // early return if tile already open
        if (revealBoards[REVEAL_BOARD_INDEX][i]) return;
        revealBoards[REVEAL_BOARD_INDEX][i] = true;

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
                if (gameBoard[index] != MINE_CHAR &&
                        !revealBoards[REVEAL_BOARD_INDEX][index]) {
                    propagateBoardDiscovery(index);
                }
            }
        }
    }

    /**
     * Because sometimes you just get really, really unlucky. Most versions of
     * Minesweeper have some mechanism to prevent you from accidentally ending
     * the entire game on your very first move according to
     * <a href="https://en.wikipedia.org/wiki/Minesweeper_(video_game)
     * #Objective_and_strategy">Minesweeper (Wikipedia)</a>
     * (&nbsp;accessed Mar 13, 2025&nbsp;)
     * @param index the index of the tile containing the unfortunate mine
     */
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

        revealBoards[REVEAL_BOARD_INDEX][index] = true;

        // recalculate the board numbers
        populateBoardNumbers();
    }

    /**
     * Because the game is over, there's no reason to hide anything now. All the
     * cards are on the table, which is a silly expression, because this game
     * has no cards in it. But if it did you could see them now. Even though
     * this game isn't played on a table.
     * <br><br>
     * Reveals every mine (but leaves existing flag markers in place) and
     * removes the covers on any undiscovered blank or numbered tiles.
     */
    private void revealBoardOnGameOver() {
        multiPrint("\n\n Opening Final Game Board...\n\n");

        if (gameOverWin) {
            for (Integer i : emptyBoardSpaces) {
                gameBoard[i] = BLANK_CHAR;
            }
        }

        Arrays.fill(revealBoards[REVEAL_BOARD_INDEX], true);
        printGameBoard();

        if (gameOverWin) {
            // count up the total game time and print your score data

            totalTime = System.currentTimeMillis() - startTime;
            multiPrintf("You win!\nYour time: %.1f%n", (totalTime / 1000));
            multiPrintln("Mines flagged: " +
                    (mines - minesRemaining) + " / " + mines);
            multiPrintf("Avg time/mine: %.1f%n", (totalTime / 1000 / mines));

            PrintStream fileOutput = null;
            try {
                File file = new File("minesweeper_highscores.csv");
                // ensure file exists
                if (file.createNewFile()) {
                    fileOutput = new PrintStream(file);
                    fileOutput.println("Name, Difficulty, Time, Time/mine");
                } else {
                    fileOutput = new PrintStream(file);
                }
            } catch (Exception e) {
                System.out.println("Error occurred while saving high-score " +
                        e.getMessage());
                return;
            }
            System.out.print("\nPlease enter your name (limit 10 characters): ");
            String name = input.nextLine().trim();
            if (name.length() > 10) {
                // cut length to 10
                name = name.substring(0, 10);
            }

            fileOutput.printf("%s, %d, %.1f, %.1f%n",
                    name,
                    difficulty,
                    totalTime / 1000,
                    ( totalTime / 1000 / mines));

            fileOutput.close();
        } else if (gameOverBad) {
            multiPrintln("Game over.");
        }
    }

    /**
     * Goes through each tile on the board, as well as the tiles outside the
     * board on the left and top edge in order to print the coordinate system.
     * (In order to help the player select tiles more easily)
     * <br>
     * Calls the helper method {@link Minesweeper#printGameBoardChar} for each
     * tile.
     */
    private void printGameBoard() {
        char c = 'a';
        for (int i = -1; i < boardHeight; i++) {
            for (int j = -1; j < boardWidth; j++) {
                c = printGameBoardChar(i, j, c);
            }
            multiPrintln(""); // newline after printing full board row
        }
    }

    /**
     * Prints out the character from the game board at the current coordinates.
     * This method and this method alone use 1-based indexing, as the 0 indices
     * are assumed to be the edges of the board and print the board coordinates.
     * {@code a,b,c...} are printed downwards along the left edge of the board,
     * and {@code 0,1,2,3...} are printed rightwards along the top edge of the
     * board.
     * @param i Horizontal position of the tile being printed
     * @param j Vertical position of the tile being printed
     * @param c current character for the board-edge coordinates
     * @return updated character for the board-edge coordinates
     */
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
                if (revealBoards[REVEAL_BOARD_INDEX][index]) {
                    multiPrint(formatTile(current));
                } else {
                    multiPrint(UNOPENED_SPACE);
                }
            }
        }
        return c;
    }

    /**
     * Formats the tile character with the appropriate color and/or surrounding
     * characters. This is done to allow the board to be kept as characters, but
     * still output looking slightly nicer.
     * @param c The character to be formatted. This is checked against class
     *          constants for the mine and flag characters to determine if it is
     *          one of those types.
     * @return The 3 (visually) character length String of formatted text. May
     * contain more than 3 characters, but any characters over 3 are format tags
     * and do not take up any width to display.
     */
    private static String formatTile(char c) {
        return switch (c) {
            case '1' -> tile1Code + " " + c + " " + resetCode;
            case '2' -> tile2Code + " " + c + " " + resetCode;
            case '3' -> tile3Code + " " + c + " " + resetCode;
            case '4' -> tile4Code + " " + c + " " + resetCode;
            case '5' -> tile5Code + " " + c + " " + resetCode;
            case '6' -> tile6Code + " " + c + " " + resetCode;
            case '7' -> tile7Code + " " + c + " " + resetCode;
            case '8' -> tile8Code + " " + c + " " + resetCode;
            case MINE_CHAR -> "{" + MINE_CHAR + "}";
            case FLAG_CHAR -> " " + FLAG_CHAR + " ";
            default -> "   ";
        };
    }

    /**
     * Must be called after the mines have been placed on the board already.
     * Places the number tiles around mines.
     * <br>
     * Prefers using more computation rather than more memory.
     */
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

    /**
     * Should be called before {@link Minesweeper#populateBoardNumbers()},
     * places mines onto the board with probably random distribution.
     * <br>
     * I do not know if placing them at coordinates that are uniformly random in
     * one axis results in a location that is still uniformly distributed in two
     * axes.
     */
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

    /**
     * 
     */
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
        revealBoards = new boolean[][]{revealGameBoard, debugRevealGameBoard};

        Arrays.fill(gameBoard, BLANK_CHAR);
        Arrays.fill(revealBoards[REVEAL_BOARD_INDEX], false);


        minesRemaining = mines;

        multiPrintf("You have selected %s difficulty.\n", difficultyStr);
    }
}
