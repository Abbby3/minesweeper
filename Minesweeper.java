import java.util.*;

public class Minesweeper {
  private static final int SIZE = 8; // Size of the grid
  private static final int MINES = 10; // Number of mines

  private char[][] grid; // The grid to display
  private boolean[][] revealed; // To track revealed cells
  private Set<Integer> mines; // Set to store mine positions

  private Scanner scanner;

  public Minesweeper() {
    grid = new char[SIZE][SIZE];
    revealed = new boolean[SIZE][SIZE];
    mines = new HashSet<>();
    scanner = new Scanner(System.in);
    initializeGrid();
    placeMines();
  }

  // Initialize the grid with hidden cells
  private void initializeGrid() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        grid[i][j] = '-';
        revealed[i][j] = false;
      }
    }
  }

  // Place mines randomly on the grid
  private void placeMines() {
    Random random = new Random();
    int count = 0;
    while (count < MINES) {
      int position = random.nextInt(SIZE * SIZE);
      if (!mines.contains(position)) {
        mines.add(position);
        count++;
      }
    }
  }

  // Display the grid to the player with numeric column labels starting from 1
  private void displayGrid() {
    System.out.print("   ");
    for (int i = 1; i <= SIZE; i++) {
      if (i < 10) {
        System.out.print(" ");
      }
      System.out.print(i + " ");
    }
    System.out.println();

    for (int i = 0; i < SIZE; i++) {
      System.out.print((char) ('A' + i) + " |");
      for (int j = 0; j < SIZE; j++) {
        if (revealed[i][j]) {
          System.out.print(" " + grid[i][j] + " ");
        } else {
          System.out.print(" - ");
        }
      }
      System.out.println();
    }
  }

  // Main game loop
  public void play() {
    boolean gameOver = false;

    while (!gameOver) {
      displayGrid();
      System.out.print("Enter row and column (e.g., A10): ");
      String input = scanner.nextLine().trim();

      if (input.length() < 2) {
        System.out.println("Invalid input. Please enter both row and column.");
        continue;
      }

      char rowChar = Character.toUpperCase(input.charAt(0));
      String colStr = input.substring(1);
      int col;
      try {
        col = Integer.parseInt(colStr) - 1; // Adjust column to zero-based index
      } catch (NumberFormatException e) {
        System.out.println("Invalid column. Please enter a valid number.");
        continue;
      }

      int row = rowChar - 'A'; // Convert row character to index

      if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
        System.out.println("Invalid input. Please enter valid coordinates.");
        continue;
      }

      if (mines.contains(row * SIZE + col)) {
        System.out.println("Game Over! You hit a mine.");
        gameOver = true;
      } else {
        revealCell(row, col);
        if (checkWin()) {
          System.out.println("Congratulations! You cleared all safe cells.");
          gameOver = true;
        }
      }
    }
  }

  // Reveal the selected cell
  private void revealCell(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || revealed[row][col]) {
      return;
    }

    revealed[row][col] = true;

    // Count adjacent mines
    int count = 0;
    for (int dr = -1; dr <= 1; dr++) {
      for (int dc = -1; dc <= 1; dc++) {
        int r = row + dr;
        int c = col + dc;
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && mines.contains(r * SIZE + c)) {
          count++;
        }
      }
    }

    if (count > 0) {
      grid[row][col] = (char) (count + '0'); // Convert count to char
    } else {
      grid[row][col] = ' ';
      // Auto-reveal adjacent cells if no adjacent mines
      for (int dr = -1; dr <= 1; dr++) {
        for (int dc = -1; dc <= 1; dc++) {
          revealCell(row + dr, col + dc);
        }
      }
    }
  }

  // Check if all non-mine cells are revealed
  private boolean checkWin() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (!revealed[i][j] && !mines.contains(i * SIZE + j)) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Minesweeper game = new Minesweeper();
    game.play();
  }
}
