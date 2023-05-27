package src.editor.matachi.mapeditor.editor;

/**
 * Modified to be a Singleton, support map testing with the 'start_game' button, and load `Files` given a `File`.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.JDOMException;
import src.editor.matachi.mapeditor.grid.Camera;
import src.editor.matachi.mapeditor.grid.Grid;
import src.editor.matachi.mapeditor.grid.GridCamera;
import src.editor.matachi.mapeditor.grid.GridModel;
import src.editor.matachi.mapeditor.grid.GridView;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import src.game.Game;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

// import processbuilder
import java.io.File;
import java.nio.file.Path;
/**
 * Controller of the application.
 * 
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 * 
 */
public class Controller  extends SwingWorker<Void, Void> implements ActionListener, GUIInformation {

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	private Tile selectedTile;
	private Camera camera;

	private List<Tile> tiles;

	private GridView grid;
	private View view;

	private int gridWith = Constants.MAP_WIDTH;
	private int gridHeight = Constants.MAP_HEIGHT;

	private File currentFile = null;


	// Attribute which stores the composite level check that we will perform on every map
	private CompositeLevelCheck levelCheckFunction = new CompositeABCDLevelCheck();
	private Properties properties;

	private String propertiesPath;

	// Controller is a Singleton
	private static Controller instance = null;

	private Game game = null;
	private SwingWorker<Void, Void> gameWorker = new SwingWorker<Void, Void>() {
		@Override
		protected Void doInBackground() throws Exception {
			System.out.println("Entering doInBackground in SwingWorker");
			if (game != null) {
				System.out.println("Game is not null");
				game.getFrame().dispose();
				game = null;
				System.out.println("Game disposed");
			} else {
				System.out.println("Game is null");
				String DEFAULT_PROPERTIES_PATH = "pacman/properties/test2.properties";
				final Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
				GameCallback gameCallback = new GameCallback();
				File testFile = new File("pacman/testFolder/sample_map2.xml");
				game = new Game(gameCallback, properties, testFile);

				game.getFrame().dispose();

			}
			return null;
		}
	};

	/**
	 * Singleton constructor
	 */
	private Controller() {
		init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);

	}

	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}

	/**
	 * Returns the single instance of the Controller class.
	 * Instantiates the controller if it has not been created.
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}


	public void init(int width, int height) {
		this.tiles = TileManager.getTilesFromFolder("pacman/sprites/data/");
		this.model = new GridModel(width, height, tiles.get(0).getCharacter());
		this.camera = new GridCamera(model, Constants.GRID_WIDTH,
				Constants.GRID_HEIGHT);

		grid = new GridView(this, camera, tiles); // Every tile is
													// 30x30 pixels

		this.view = new View(this, camera, grid, tiles);
	}


	/**
	 * Different commands that come from the `View`.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(
					Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
			// view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();

		} else if (e.getActionCommand().equals("load")) {
			loadFile();
			// Perform level check on loaded file
			levelCheckFunction.checkLevel(model, currentFile.getName());
		} else if (e.getActionCommand().equals("update")) {
			updateGrid(gridWith, gridHeight);
		}else if (e.getActionCommand().equals("start_game")) {

			System.out.println("Executing SwingWorker");
			gameWorker.execute();

		}
	}

	public void updateGrid(int width, int height) {
		view.close();
		init(width, height);
		view.setSize(width, height);
	}

	DocumentListener updateSizeFields = new DocumentListener() {

		public void changedUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void removeUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void insertUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

	};

	private void saveFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"xml files", "xml");
		chooser.setFileFilter(filter);
		File workingDirectory = new File(System.getProperty("user.dir"));
		chooser.setCurrentDirectory(workingDirectory);

		int returnVal = chooser.showSaveDialog(null);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				Element level = new Element("level");
				Document doc = new Document(level);
				doc.setRootElement(level);

				Element size = new Element("size");
				int height = model.getHeight();
				int width = model.getWidth();
				size.addContent(new Element("width").setText(width + ""));
				size.addContent(new Element("height").setText(height + ""));
				doc.getRootElement().addContent(size);

				for (int y = 0; y < height; y++) {
					Element row = new Element("row");
					for (int x = 0; x < width; x++) {
						char tileChar = model.getTile(x,y);
						String type = "PathTile";

						if (tileChar == 'b')
							type = "WallTile";
						else if (tileChar == 'c')
							type = "PillTile";
						else if (tileChar == 'd')
							type = "GoldTile";
						else if (tileChar == 'e')
							type = "IceTile";
						else if (tileChar == 'f')
							type = "PacTile";
						else if (tileChar == 'g')
							type = "TrollTile";
						else if (tileChar == 'h')
							type = "TX5Tile";
						else if (tileChar == 'i')
							type = "PortalWhiteTile";
						else if (tileChar == 'j')
							type = "PortalYellowTile";
						else if (tileChar == 'k')
							type = "PortalDarkGoldTile";
						else if (tileChar == 'l')
							type = "PortalDarkGrayTile";

						Element e = new Element("cell");
						row.addContent(e.setText(type));
					}
					doc.getRootElement().addContent(row);
				}
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(doc, new FileWriter(chooser.getSelectedFile()));
				// Perform level check
				levelCheckFunction.checkLevel(model, chooser.getSelectedFile().getName());
				currentFile = chooser.getSelectedFile();
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file!", "error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
		}
	}

	/**
	 * Loads a `File` with `JFileChooser` into the Editor
	 */
	public void loadFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;
			File workingDirectory = new File(System.getProperty("user.dir"));
			chooser.setCurrentDirectory(workingDirectory);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				loadSelectedFile(selectedFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a `File` into the Editor
	 * @param selectedFile the `File` to load
	 */
	public void loadSelectedFile(File selectedFile){
		SAXBuilder builder = new SAXBuilder();
		try {
			if (selectedFile.canRead() && selectedFile.exists()) {
				Document document = (Document) builder.build(selectedFile);
				Element rootNode = document.getRootElement();

				List sizeList = rootNode.getChildren("size");
				Element sizeElem = (Element) sizeList.get(0);
				int height = Integer.parseInt(sizeElem
						.getChildText("height"));
				int width = Integer
						.parseInt(sizeElem.getChildText("width"));
				updateGrid(width, height);

				List rows = rootNode.getChildren("row");
				for (int y = 0; y < rows.size(); y++) {
					Element cellsElem = (Element) rows.get(y);
					List cells = cellsElem.getChildren("cell");

					for (int x = 0; x < cells.size(); x++) {
						Element cell = (Element) cells.get(x);
						String cellValue = cell.getText();

						char tileNr = 'a';
						if (cellValue.equals("PathTile"))
							tileNr = 'a';
						else if (cellValue.equals("WallTile"))
							tileNr = 'b';
						else if (cellValue.equals("PillTile"))
							tileNr = 'c';
						else if (cellValue.equals("GoldTile"))
							tileNr = 'd';
						else if (cellValue.equals("IceTile"))
							tileNr = 'e';
						else if (cellValue.equals("PacTile"))
							tileNr = 'f';
						else if (cellValue.equals("TrollTile"))
							tileNr = 'g';
						else if (cellValue.equals("TX5Tile"))
							tileNr = 'h';
						else if (cellValue.equals("PortalWhiteTile"))
							tileNr = 'i';
						else if (cellValue.equals("PortalYellowTile"))
							tileNr = 'j';
						else if (cellValue.equals("PortalDarkGoldTile"))
							tileNr = 'k';
						else if (cellValue.equals("PortalDarkGrayTile"))
							tileNr = 'l';
						else
							tileNr = '0';

						model.setTile(x, y, tileNr);
					}
				}
				String mapString = model.getMapAsString();
				grid.redrawGrid();
				currentFile = selectedFile;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}

	/**
	 * Function to set the properties file path for the controller
	 * @param propertiesPath properties file path
	 */
	public void setProperties(String propertiesPath) {
		this.propertiesPath = propertiesPath;
		this.properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

	}

	public Properties getProperties() {
		return properties;
	}

	public String getPropertiesPath() {
		return propertiesPath;
	}

	public Grid getModel() {
		return model;
	}

	/**
	 * Sets the `currentFile` to null
	 */
	public void setCurrentFileNull(){
		this.currentFile = null;
	}

	/**
	 * Empties and clears the Editor
	 */
	public void resetEditor() {
		Controller.instance = null;
		updateGrid(this.gridWith, this.gridHeight);
	}
}
