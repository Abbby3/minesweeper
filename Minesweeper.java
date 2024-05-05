import java.util.*;

public class Minesweeper {
  private static final int SIZE = 8;
  private static final int MINES = 10;

  private char[][] grid;
  private boolean[][] selected;
  private boolean[][] marked;
  private Set<Integer> mines;

  private Scanner scanner;

  public Minesweeper() {
    grid = new char[SIZE][SIZE];
    selected = new boolean[SIZE][SIZE];
    marked = new boolean[SIZE][SIZE];
    mines = new HashSet<>();
    scanner = new Scanner(System.in);
    setupGrid();
    setupMines();
  }

  private void setupGrid() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        grid[i][j] = '-';
        selected[i][j] = false;
        marked[i][j] = false;
      }
    }
  }

  private void setupMines() {
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

  private void displayGrid() {
    System.out.println();

    System.out.print("  ");
    for (int i = 1; i <= SIZE; i++) {
      if (i < 10) {
        System.out.print(" ");
      }
      System.out.print(i + " ");
    }
    System.out.println();

    for (int i = 0; i < SIZE; i++) {
      System.out.print((char) ('A' + i) + " ");
      for (int j = 0; j < SIZE; j++) {
        if (selected[i][j]) {
          System.out.print(" " + grid[i][j] + " ");
        } else if (marked[i][j]) {
          System.out.print(" ! ");
        } else {
          System.out.print(" - ");
        }
      }
      System.out.println();
    }

  }

  public void play() {
    boolean gameOver = false;

    while (!gameOver) {
      displayGrid();
      System.out.print("Enter coordinate: ");
      String input = scanner.nextLine().trim();

      try {
        char firstChar = input.charAt(0);

        if (firstChar == '!') {
          char rowChar = Character.toUpperCase(input.charAt(1));
          String colStr = input.substring(2);
          int col = Integer.parseInt(colStr) - 1;
          int row = rowChar - 'A';

          marked[row][col] = !marked[row][col];
        } else {
          char rowChar = Character.toUpperCase(firstChar);
          String colStr = input.substring(1);
          int col = Integer.parseInt(colStr) - 1;
          int row = rowChar - 'A';

          if (mines.contains(row * SIZE + col)) {
            System.out.println();
            System.out.println("Boom! Game Over.");
            System.out.println();
            gameOver = true;
          } else {
            selectSpace(row, col);
            if (checkWin()) {
              System.out.println();
              System.out.println("You win!");
              System.out.println();
              gameOver = true;
            }
          }
        }
      } catch (Exception e) {
        System.out.println();
        System.out.println("Invalid input. Please input <row><column> to select a space, or !<row><column> to mark a space. For example, 'a12', '!b4', 'f2', etc.");
        System.out.println();
      }
    }
  }

  private void selectSpace(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || selected[row][col]) {
      return;
    }

    selected[row][col] = true;

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
      grid[row][col] = (char) (count + '0');
    } else {
      grid[row][col] = ' ';
      for (int dr = -1; dr <= 1; dr++) {
        for (int dc = -1; dc <= 1; dc++) {
          selectSpace(row + dr, col + dc);
        }
      }
    }
  }

  private boolean checkWin() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (!selected[i][j] && !mines.contains(i * SIZE + j)) {
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