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

    static char[] gameBoard;
    static boolean[] revealGameBoard;
    static int boardWidth;
    static int boardHeight;

    static int mines;
    static char mineChar = 'X';

    static int difficulty;

    static boolean gameOver = false;
    // track this so user cant lose on turn 1
    static boolean isFirstClick = true;
    static List<Integer> mineLocations = new ArrayList<>();

    public static void main(String[] args) {
        getUserDifficultyChoice();

        initializeGameState(difficulty);

        populateMinefield();

        populateBoardNumbers();

        printGameBoard();

        revealBoardOnGameOver();
    }

    private static void revealBoardOnGameOver() {
        System.out.print("\n\n Opening Game Board...\n\n");
        Arrays.fill(revealGameBoard, true);
        printGameBoard();
    }

    private static void printGameBoard() {
        for (int i = -1; i < boardHeight; i++) {
            char c = 'a';
            for (int j = -1; j < boardWidth; j++) {
                if (i == -1) {
                    if (j == -1) {
                        System.out.print("   ");
                    } else {
                        System.out.print(underline + ' ' + c + ' ' + resetCode);
                        c++;
                    }
                } else if (j == -1) {
                    System.out.printf("%3s", i + "|");
                } else {
                    int index = (i * boardWidth) + j;
                    if (gameBoard[index] == mineChar) {
                        System.out.printf("%3s",
                                gameOver ? ' ' + mineChar : "   "
                        );
                    } else {
                        if (gameBoard[index] == ' ') {
                            System.out.print("   ");
                        } else {
                            if (revealGameBoard[index]) {
                                int num = Integer.parseInt(
                                        String.valueOf(gameBoard[index]));
                                System.out.print(colorNumberTile(num));
                            } else {
                                System.out.print("  ");
                            }
                        }
                    }
                }
            }
            System.out.println(); // newline after printing full board row
        }
    }

    private static String colorNumberTile(int num) {
        return switch (num) {
            case 1 -> tile1Code + ' ' + num + ' ' + resetCode;
            case 2 -> tile2Code + ' ' + num + ' ' + resetCode;
            case 3 -> tile3Code + ' ' + num + ' ' + resetCode;
            case 4 -> tile4Code + ' ' + num + ' ' + resetCode;
            case 5 -> tile5Code + ' ' + num + ' ' + resetCode;
            case 6 -> tile6Code + ' ' + num + ' ' + resetCode;
            case 7 -> tile7Code + ' ' + num + ' ' + resetCode;
            case 8 -> tile8Code + ' ' + num + ' ' + resetCode;
            default -> "  ";
        };
    }

    private static void populateBoardNumbers() {
        for (int i = 0; i < gameBoard.length; i++) {
            // skip if this square is a mine
            if (mineChar == gameBoard[i]) continue;

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
                    if (gameBoard[i + (l) + (w * boardWidth)] == mineChar) {
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
        List<Integer> emptyBoardSpaces = new ArrayList<>();
        for (int i = 0; i < gameBoard.length; i++) {
            emptyBoardSpaces.add(i);
        }
        for (int i = 0; i < mines; i++) {
            int index = emptyBoardSpaces.get(
                    (int) (Math.random() * emptyBoardSpaces.size()));
            gameBoard[index] = mineChar;
            emptyBoardSpaces.remove((Integer) index);
            mineLocations.add(index);
        }
    }

    private static void getUserDifficultyChoice() {
        String diffString;
        while (true) {
            System.out.println("Please select a difficulty (0-2): ");
            diffString = userIn.nextLine();
            if ("0".equals(diffString) || "1".equals(diffString) ||
                    "2".equals(diffString)) {
                break;
            }
            System.out.print("Invalid input: ");
        }
        difficulty = Integer.parseInt(diffString);
    }

    private static void initializeGameState(int difficulty) {
        switch (difficulty) {
            case 0 -> {
                boardHeight = EASY_HEIGHT;
                boardWidth = EASY_WIDTH;
                mines = EASY_MINES;
            }
            case 1 -> {
                boardHeight = MEDIUM_HEIGHT;
                boardWidth = MEDIUM_WIDTH;
                mines = MEDIUM_MINES;
            }
            default -> {
                boardHeight = HARD_HEIGHT;
                boardWidth = HARD_WIDTH;
                mines = HARD_MINES;
            }
        }
        gameBoard = new char[boardHeight * boardWidth];
        revealGameBoard = new boolean[boardHeight * boardWidth];
        Arrays.fill(gameBoard, ' ');
        Arrays.fill(revealGameBoard, false);
    }
}
