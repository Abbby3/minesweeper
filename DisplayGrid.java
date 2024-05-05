public class DisplayGrid {
  private int SIZE = 10;

  public void displayGrid(char[][] grid, boolean[][] selected, boolean[][] marked) {
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
}
