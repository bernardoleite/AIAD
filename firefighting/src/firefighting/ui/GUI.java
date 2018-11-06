package firefighting.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import firefighting.aircraft.AircraftAgent;
import firefighting.firestation.FireStationAgent;
import firefighting.nature.Fire;
import firefighting.nature.WaterResource;
import firefighting.utils.Config;
import firefighting.world.WorldAgent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;

public class GUI {

	private static WorldAgent worldAgent;
	private JFrame frame;
	private static JLabel[][] grid;
    private static JTextArea textArea;

	/**
	 * Creates the graphic user interface
	 * @param worldAgent the world agent
	 */
	public GUI(WorldAgent worldAgent) {
		GUI.worldAgent = worldAgent;
		
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();

		frameInitialize(panel, panel_1, panel_2);
	    
	    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
	    flowLayout.setHgap(100);
	    flowLayout.setAlignment(FlowLayout.LEFT);
	    
	    captionInitialize(panel, panel_1);

        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);

        scrollPaneInitialize(panel_2);
	    gridInitialize(worldAgent, panel);
	}

	/**
	 * Initializes the grid
	 * @param worldAgent world agent
	 * @param panel main panel
	 */
	private void gridInitialize(WorldAgent worldAgent, JPanel panel) {
		grid= new JLabel[Config.GRID_WIDTH][Config.GRID_HEIGHT];
	    for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i] = new JLabel();
	            grid[j][i].setBorder(new LineBorder(Color.BLACK));
	            grid[j][i].setHorizontalAlignment(SwingConstants.CENTER);
	            grid[j][i].setVerticalAlignment(SwingConstants.CENTER);
	            grid[j][i].setOpaque(true);
	            
	            setCell(worldAgent.getWorldMap()[j][i], grid[j][i]);
	            panel.add(grid[j][i]);
	        }
	    }
	}

	/**
	 * Initializes the capiton area
	 * @param panel main panel
	 * @param panel_1 captionPanel
	 */
	private void captionInitialize(JPanel panel, JPanel panel_1) {
		JLabel lblCaption = new JLabel("<html>Caption:<br>1) ST - Fire Station<br>2) W[c] - Filling Station, where [c] is its capacity"
	    		+ "<br>3) F[i] - Fire, where [i] is its intensity<br>4) A[t] - Aircraft, where [t] is its tank capacity</html>");
	    lblCaption.setHorizontalAlignment(SwingConstants.LEFT);
	    panel_1.add(lblCaption);
		panel.setLayout(new GridLayout(Config.GRID_HEIGHT, Config.GRID_WIDTH));
	}

	/**
	 * Initializes the frame
	 * @param panel main panel
	 * @param panel_1 caption panel
	 * @param panel_2 info panel
	 */
	private void frameInitialize(JPanel panel, JPanel panel_1, JPanel panel_2) {
		frame = new JFrame();
		frame.setTitle("Firefighting");
	    frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 500);
	    frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		frame.getContentPane().add(panel_2, BorderLayout.EAST);
	}

	/**
	 * Initalizes the info scroll panel
	 * @param panel_2 info panel
	 */
	private void scrollPaneInitialize(JPanel panel_2) {
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(scrollPane); //We add the scroll, since the scroll already contains the textArea
		panel_2.setPreferredSize(new Dimension(600,500));
	}
	
	/**
	 * Sets a given cell of the GUI grid
	 * @param worldCell cell of the worldMap being processed
	 * @param gridCell cell of the grid being processed
	 */
	private static void setCell(Object worldCell, JLabel gridCell) {
		if (worldCell != null) {
			gridCell.setText(worldCell.toString());
        	if (worldCell instanceof Fire) {
        		gridCell.setBackground(Color.orange);
        	}
        	if (worldCell instanceof AircraftAgent) {
        		gridCell.setBackground(Color.yellow);
        	}
        	if (worldCell instanceof WaterResource) {
        		gridCell.setBackground(Color.cyan);
        	}
        	if (worldCell instanceof FireStationAgent) {
        		gridCell.setBackground(Color.red);
        	}
		}
        else {
        	gridCell.setBackground(null);
        	gridCell.setText("");
        }
	}
	
	/**
	 * Called on tick to fill the grid with the updated positions of the objects
	 */
	public static void fillGrid() {
		for (int i = 0; i < Config.GRID_HEIGHT; i++){
	        for (int j = 0; j < Config.GRID_WIDTH; j++){
	            grid[j][i].setOpaque(true);
	            setCell(worldAgent.getWorldMap()[j][i], grid[j][i]);
	        }
	    }
	}
	
	/**
	 * Logs a message in the GUI
	 * @param text Message to log
	 */
	public static void log(String text) {
		textArea.append(text);
	}
	
	/**
	 * Gets the GUI frame
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	
}
