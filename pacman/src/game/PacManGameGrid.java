// PacGrid.java
package src.game;

import ch.aplu.jgamegrid.Location;

public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;
  private final String ids = "abcdefghijkl";

  public PacManGameGrid(int nbHorzCells, int nbVertCells)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new int[nbVertCells][nbHorzCells];
    String maze =
      "bkbbbbbbbbibbbbbbblb" + // 0
      "bccccbccccdcccbccccb" + // 1
      "bdbbcbcbbbbbbcbcbbcb" + // 2
      "bcbcccccccecdccccbcb" + // 3
      "bcbcbbcbbaabbcbbcbcb" + // 4
      "jccccccbahagbccccccj" + // 5
      "bcbcbbcbbbbbbcbbcbcb" + // 6
      "bcbccccccdefcccccbcb" + // 7
      "bebbcbcbbbbbbcbcbbcb" + // 8
      "bcccdbccccdcccbccccb" + // 9
      "blbbbbbbbbibbbbbbbkb";// 10

    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        int value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  public int getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }

  private int toInt(char c)
  {
    return ids.indexOf(c);
  }
}
