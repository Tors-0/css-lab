import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Minesweeper {
    static Scanner userIn = new Scanner(System.in);

    // format string escape codes
    static String resetCode = "\033[0m";
    static String underline = "\033[37;4m";
    static String tile1Code = "\033[30;42;1m"; // green bg
    static String tile2Code = "\033[30;43;1m"; // yellow bg
    static String tile3Code = "\033[30;41;1m"; // red bg
    static String tile4Code = "\033[30;46;1m"; // cyan bg
    static String tile5Code = "\033[30;44;1m"; // blue bg
    static String tile6Code = "\033[30;45;1m"; // purple bg
    static String tile7Code = "\033[30;47;1m"; // white bg
    static String tile8Code = "\033[33;40;1m"; // nuclear

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
    static char[] gameBoard;
    static boolean[] revealGameBoard;
    static int boardWidth;
    static int boardHeight;

    // board icon info
    static int mines = 0;
    static int minesRemaining;
    static final char MINE_CHAR = 'X';
    static final char FLAG_CHAR = 'F';
    static final String UNOPENED_SPACE = "[ ]";

    static int difficulty;

    static boolean gameOver = false;
    // track this so user cant lose on turn 1
    static boolean isFirstClick = true;
    static boolean invalidInput = false;

    // hold spaces :O
    static List<Integer> mineLocations = new ArrayList<>();
    static List<Integer> emptyBoardSpaces = new ArrayList<>();

    public static void main(String[] args) {
        getUserDifficultyChoice();

        initializeGameState(difficulty);

        populateMinefield();

        populateBoardNumbers();

        do {
            gameLoop();
        } while (!gameOver);

        revealBoardOnGameOver();
    }

    private static void gameLoop() {
        if (invalidInput) {
            System.out.println("Invalid Input");
            invalidInput = false;
        }

        evaluateGameWin();

        printGameBoard();

        // collect user input
        System.out.println("Mines left: " + minesRemaining);
        System.out.print("What is your choice (\"help\" for help): ");
        String choice = userIn.nextLine();
        choice = choice.toLowerCase();

        // parse user input
        if ("help".equals(choice)) {
            printHelpGuide();
        } else if (choice.matches("(mark|dig|m|d) [a-z][0-9]{1,2}")) {
            evaluateUserAction(choice);
            isFirstClick = false;
        }
    }

    private static void evaluateGameWin() {
        for (Integer i : emptyBoardSpaces) {
            if (!revealGameBoard[i]) return;
        }

        System.out.println("You win!");
        System.exit(0);
    }

    private static void evaluateUserAction(String choice) {
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
        }
    }

    private static void executeMarkAction(int i) {
        if (gameBoard[i] == FLAG_CHAR) {
            minesRemaining++;
            gameBoard[i] = ' ';
            revealGameBoard[i] = false;
            populateBoardNumbers();
        } else {
            gameBoard[i] = FLAG_CHAR;
            minesRemaining--;
            revealGameBoard[i] = true;
        }
    }

    private static void executeDigAction(int index) {
        if (mineLocations.contains(index)) {
            if (isFirstClick) {
                moveMineOnFirstClick(index);
            } else {
                gameOver = true;
            }
        } else {
            if (!revealGameBoard[index] && gameBoard[index] != MINE_CHAR) {
                propagateBoardDiscovery(index);
            }
        }
    }

    private static void printHelpGuide() {
        System.out.println("""
                To flag a square, use "mark a5" or "m a5".\

                To dig a square, use "dig a5" or "d a5".\

                Capitalization does not matter.""");
    }

    private static void propagateBoardDiscovery(int i) {
        if (revealGameBoard[i]) return; // early return is tile already open
        revealGameBoard[i] = true;

        // early return if tile has number
        if (gameBoard[i] != ' ' && !isFirstClick) return;
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

    private static void moveMineOnFirstClick(int index) {
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

    private static void revealBoardOnGameOver() {
        System.out.print("\n\n Opening Final Game Board...\n\n");
        Arrays.fill(revealGameBoard, true);
        printGameBoard();
    }

    private static void printGameBoard() {
        char c = 'a';
        for (int i = -1; i < boardHeight; i++) {
            for (int j = -1; j < boardWidth; j++) {
                if (i == -1) {
                    if (j == -1) {
                        System.out.print("   ");
                    } else {
                        System.out.printf("%s%3s%s", underline, j, resetCode);
                    }
                } else if (j == -1) {
                    System.out.printf("%3s", c + "|");
                    c++;
                } else {
                    int index = (i * boardWidth) + j;
                    char current = gameBoard[index];
                    if (current == MINE_CHAR) {
                        System.out.printf("%3s",
                                gameOver ? formatTile(current) :
                                        UNOPENED_SPACE
                        );
                    } else {
                        if (revealGameBoard[index]) {
                            System.out.print(formatTile(current));
                        } else {
                            System.out.print(UNOPENED_SPACE);
                        }
                    }
                }
            }
            System.out.println(); // newline after printing full board row
        }
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
            case MINE_CHAR -> " " + MINE_CHAR + " ";
            case FLAG_CHAR -> " " + FLAG_CHAR + " ";
            default -> "   ";
        };
    }

    private static void populateBoardNumbers() {
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

    private static void populateMinefield() {
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

    private static void repopulateMinefield() {
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] != 'F') {
                gameBoard[i] = ' ';
            }
        }
        for (Integer mineLocation : mineLocations) {
            if (gameBoard[mineLocation] != FLAG_CHAR) {
                gameBoard[mineLocation] = MINE_CHAR;
            }
        }
    }

    private static void getUserDifficultyChoice() {
        String diffString;
        while (true) {
            System.out.println("Please select a difficulty (0-4): ");
            diffString = userIn.nextLine();
            String[] legalDifficulties = {"0", "1", "2", "3", "4"};
            if (Arrays.asList(legalDifficulties).contains(diffString)) {
                break;
            }
            System.out.print("Invalid input: ");
        }
        difficulty = Integer.parseInt(diffString);
    }

    private static void initializeGameState(int difficulty) {
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
        }
        gameBoard = new char[boardHeight * boardWidth];
        revealGameBoard = new boolean[boardHeight * boardWidth];
        Arrays.fill(gameBoard, ' ');
        Arrays.fill(revealGameBoard, false);

        minesRemaining = mines;

        System.out.printf("You have selected %s difficulty.\n", difficultyStr);
    }
}
