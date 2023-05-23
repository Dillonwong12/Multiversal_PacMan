// PacGrid.java
package src.game;

import ch.aplu.jgamegrid.Location;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.List;

import java.io.File;

public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;
  private final String ids = "abcdefghijkl";

  // default
  public PacManGameGrid(int nbHorzCells, int nbVertCells){
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new int[nbVertCells][nbHorzCells];
    String maze =
      "bbbbbbbbbbbbbbbbbbbb" + // 0
      "bccccbccccdcccbccccb" + // 1
      "bdbbcbcbbbbbbcbcbbcb" + // 2
      "bcbcccccccecdccccbcb" + // 3
      "bcbcbbcbbaabbcbbcbcb" + // 4
      "bccccccbaaaabccccccb" + // 5
      "bcbcbbcbbbbbbcbbcbcb" + // 6
      "bcbccccccdefcccccbcb" + // 7
      "bebbcbcbbbbbbcbcbbcb" + // 8
      "bcccdbccccdcccbccccb" + // 9
      "bbbbbbbbbbbbbbbbbbbb";// 10

            "bkbbbbbbbbibbbbbbblb" + // 0
            "bccccbccccdcccbccccb" + // 1
            "bdbbcbcbbbbbbcbcbbcb" + // 2
            "bcbcccccccecdccccbcb" + // 3
            "bcbcbbcbbaabbcbbcbcb" + // 4
            "jccccccbaaaabccccccj" + // 5
            "bcbcbbcbbbbbbcbbcbcb" + // 6
            "bcbccccccdefcccccbcb" + // 7
            "bebbcbcbbbbbbcbcbbcb" + // 8
            "bcccdbccccdcccbccccb" + // 9
            "blbbbbbbbbibbbbbbbkb";// 10

    populateMazeArray(maze);
  }

  public PacManGameGrid(int nbHorzCells, int nbVertCells, File mapXML) throws IOException, JDOMException {
    this(nbHorzCells, nbVertCells);
    String maze = XMLToMaze(mapXML);
    populateMazeArray(maze);
  }

  private void populateMazeArray(String maze) {
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

  private String XMLToMaze(File XMLMap) throws IOException, JDOMException {
    String maze = "";

    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(XMLMap);
    Element root = document.getRootElement();
    List<Element> rows = root.getChildren("row");

    for(Element row:rows){
        List<Element> cells = row.getChildren("cell");
        for(Element cell:cells){
            String cellValue = cell.getText();

            char mapping = getCellMapping(cellValue);
            maze += mapping;
        }
    }

    return maze;
  }

  private char getCellMapping(String cellValue) {
    switch (cellValue) {
      case "WallTile":
        return 'b';
      case "PillTile":
        return 'c';
      case "GoldTile":
        return 'd';
      case "IceTile":
        return 'e';
      case "PacTile":
        return 'f';
      case "TrollTile":
        return 'g';
      case "TX5Tile":
        return 'h';
      case "PortalWhiteTile":
        return 'i';
      case "PortalYellowTile":
        return 'j';
      case "PortalDarkGoldTile":
        return 'k';
      case "PortalDarkGrayTile":
        return 'l';
      case "PathTile":
        return 'a';
    }
    return '0';
  }





}
