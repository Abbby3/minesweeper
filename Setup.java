import java.util.*;

public class Setup {
  private int SIZE = 8;
  private int MINES = 10;

  private char[][] grid;
  private boolean[][] selected;
  private boolean[][] marked;
  private Set<Integer> mines;

  public Setup() {
    grid = new char[SIZE][SIZE];
    selected = new boolean[SIZE][SIZE];
    marked = new boolean[SIZE][SIZE];
    mines = new HashSet<>();
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

  public char[][] getGrid() {
    return grid;
  }

  public boolean[][] getSelected() {
    return selected;
  }

  public boolean[][] getMarked() {
    return marked;
  }

  public Set<Integer> getMines() {
    return mines;
  }
}
