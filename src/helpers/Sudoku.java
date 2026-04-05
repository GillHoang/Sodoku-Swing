package helpers;

import java.util.Random;

public class Sudoku {
    private static final Random rand = new Random();

    private Sudoku() {
    }


    // Returns false if given 3x3 block contains num
    // Ensure the number is not used in the box
    public static boolean unUsedInBox(int[][] grid, int rowStart, int colStart, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Fill a 3x3 matrix
    // Assign valid random numbers to the 3x3 subgrid
    public static void fillBox(int[][] grid, int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    // Generate a random number between 1 and 9
                    num = rand.nextInt(9) + 1;
                } while (!unUsedInBox(grid, row, col, num));
                grid[row + i][col + j] = num;
            }
        }
    }

    // Check if it's safe to put num in row i
    // Ensure num is not already used in the row
    public static boolean unUsedInRow(int[][] grid, int i, int num) {
        for (int j = 0; j < 9; j++) {
            if (grid[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // Check if it's safe to put num in column j
    // Ensure num is not already used in the column
    public static boolean unUsedInCol(int[][] grid, int j, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // Check if it's safe to put num in the cell (i, j)
    // Ensure num is not used in row, column, or box
    public static boolean checkIfSafe(int[][] grid, int i, int j, int num) {
        return (unUsedInRow(grid, i, num) && unUsedInCol(grid, j, num) && unUsedInBox(grid, i - i % 3, j - j % 3, num));
    }

    // Fill the diagonal 3x3 matrices
    // The diagonal blocks are filled to simplify the process
    public static void fillDiagonal(int[][] grid) {
        for (int i = 0; i < 9; i = i + 3) {
            // Fill each 3x3 subgrid diagonally
            fillBox(grid, i, i);
        }
    }

    // Fill remaining blocks in the grid
    // Recursively fill the remaining cells with valid numbers
    public static boolean fillRemaining(int[][] grid, int i, int j) {

        // If we've reached the end of the grid
        if (i == 9) {
            return true;
        }

        // Move to next row when current row is finished
        if (j == 9) {
            return fillRemaining(grid, i + 1, 0);
        }

        // Skip if cell is already filled
        if (grid[i][j] != 0) {
            return fillRemaining(grid, i, j + 1);
        }

        // Try numbers 1-9 in current cell
        for (int num = 1; num <= 9; num++) {
            if (checkIfSafe(grid, i, j, num)) {
                grid[i][j] = num;
                if (fillRemaining(grid, i, j + 1)) {
                    return true;
                }
                grid[i][j] = 0;
            }
        }
        return false;
    }

    // Remove K digits randomly from the grid
    // This will create a Sudoku puzzle by removing digits
    public static void removeKDigits(int[][] grid, int k) {
        while (k > 0) {
            // Pick a random cell
            int cellId = rand.nextInt(81);

            // Get the row index
            int i = cellId / 9;

            // Get the column index
            int j = cellId % 9;

            // Remove the digit if the cell is not already empty
            if (grid[i][j] != 0) {
                // Empty the cell
                grid[i][j] = 0;

                // Decrease the count of digits to remove
                k--;
            }
        }
    }

    // Generate a Sudoku grid with K empty cells
    public static int[][] sudokuGenerator() {
        // Initialize an empty 9x9 grid
        int[][] grid = new int[9][9];

        // Fill the diagonal 3x3 matrices
        fillDiagonal(grid);

        // Fill the remaining blocks in the grid
        fillRemaining(grid, 0, 0);

        return grid;
    }

    public static int[] sudokuGenerator(int[][] grid, int k) {
        removeKDigits(grid, k);

        return convertGridTo1D(grid);
    }

    private static int[] convertGridTo1D(int[][] grid) {
        int[] grid1D = new int[81];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(grid[i], 0, grid1D, i * 9, 9);
        }
        return grid1D;
    }

    public static int[][] extract3x3Blocks(int[] flatBoard) {
        // Khởi tạo mảng 2 chiều: 9 khối, mỗi khối chứa 9 phần tử
        int[][] blocks = new int[9][9];

        // Mảng phụ để theo dõi xem mỗi khối đã được điền bao nhiêu số
        int[] countInBlock = new int[9];

        for (int i = 0; i < 81; i++) {
            int row = i / 9; // Xác định tọa độ hàng (0-8)
            int col = i % 9; // Xác định tọa độ cột (0-8)

            // Công thức toán học để tìm ra phần tử đang nằm ở khối 3x3 nào (0-8)
            int blockIndex = (row / 3) * 3 + (col / 3);

            // Đưa phần tử vào đúng khối 3x3 của nó
            blocks[blockIndex][countInBlock[blockIndex]] = flatBoard[i];

            // Tăng biến đếm của khối đó lên 1
            countInBlock[blockIndex]++;
        }

        return blocks;
    }
}
