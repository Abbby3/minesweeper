import java.util.*;

public class Play {
  private int SIZE = 8;
  private Scanner scanner;
  private Setup setup;
  private DisplayGrid displayGrid;

  public Play() {
    scanner = new Scanner(System.in);
    setup = new Setup();
    displayGrid = new DisplayGrid();
  }

  public void playGame() {
    boolean gameOver = false;

    while (!gameOver) {
      displayGrid.displayGrid(setup.getGrid(), setup.getSelected(), setup.getMarked());
      System.out.print("Enter coordinate: ");
      String input = scanner.nextLine().trim();

      try {
        char firstChar = input.charAt(0);

        if (firstChar == '!') {
          char rowChar = Character.toUpperCase(input.charAt(1));
          String colStr = input.substring(2);
          int col = Integer.parseInt(colStr) - 1;
          int row = rowChar - 'A';

          setup.getMarked()[row][col] = !setup.getMarked()[row][col];
        } else {
          char rowChar = Character.toUpperCase(firstChar);
          String colStr = input.substring(1);
          int col = Integer.parseInt(colStr) - 1;
          int row = rowChar - 'A';

          if (setup.getMines().contains(row * SIZE + col)) {
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
        System.out.println("Invalid input. Please enter a valid coordinate.");
        System.out.println();
      }
    }
  }

  private void selectSpace(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || setup.getSelected()[row][col]) {
      return;
    }

    setup.getSelected()[row][col] = true;

    int count = 0;
    for (int adjR = -1; adjR <= 1; adjR++) {
      for (int adjC = -1; adjC <= 1; adjC++) {
        int r = row + adjR;
        int c = col + adjC;
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && setup.getMines().contains(r * SIZE + c)) {
          count++;
        }
      }
    }

    if (count > 0) {
      setup.getGrid()[row][col] = (char) (count + '0');
    } else {
      setup.getGrid()[row][col] = ' ';
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
        if (!setup.getSelected()[i][j] && !setup.getMines().contains(i * SIZE + j)) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Play game = new Play();
    game.playGame();
  }
}
