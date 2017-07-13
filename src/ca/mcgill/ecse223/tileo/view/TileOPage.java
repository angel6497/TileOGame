package ca.mcgill.ecse223.tileo.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.DesignModeController;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class TileOPage extends JFrame {

	// Components
	JLabel title = new JLabel("Tile-O");
	JLabel description = new JLabel("Saved Games:");
	JButton play = new JButton("Load");
	JButton create = new JButton("Create New Game");
	JList games;
	String[] gameNames = new String[10];
	JScrollPane scroll;
	TileO tileO;
	List<Game> tileOGames;
	DefaultListModel model;
	HashMap<String, Game> existingGames = new HashMap<String, Game>();
	HashMap<Game, Boolean> savedGames = new HashMap<Game, Boolean>();

	// Constructor
	public TileOPage(TileO aTileO) {
		tileO = aTileO;
		tileOGames = tileO.getGames();
		model = new DefaultListModel();
		games = new JList(model);
		TileOApplication.setMainMenu(this);
		initComponents();
	}

	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 270);
		setResizable(false);

		// Set initial location
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		title.setFont(new Font("Futura", Font.BOLD, 38));
		description.setFont(new Font("San Francisco", Font.PLAIN, 18));

		scroll = new JScrollPane(games);

		for (int i = 0; i < tileOGames.size(); i++) {
			Game current = tileOGames.get(i);
			int index = i + 1;
			String currentMode;
			if (current.getMode() == Game.Mode.DESIGN) {
				currentMode = "DESIGN MODE";
			} else if (current.getMode() == Game.Mode.GAME_WON) {
				currentMode = "GAME OVER";
			} else {
				currentMode = "GAME MODE";
			}
			existingGames.put("Game " + index + " - " + currentMode, current);
			savedGames.put(current, true);
		}

		for (String s : existingGames.keySet()) {
			model.addElement(s);
		}

		games.setFont(new Font("San Francisco", Font.PLAIN, 15));

		// Change layout manager
		GroupLayout layout = new GroupLayout(getContentPane());
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// Action Listener
		create.addActionListener(new CreateListener());

		play.addActionListener(new PlayListener());

		// Component positioning
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addGap(140, 140, 140).addComponent(title))
				.addComponent(description).addComponent(scroll).addGroup(layout.createSequentialGroup()
						.addGap(40, 40, 40).addComponent(play, 150, 150, 150).addComponent(create, 150, 150, 150)));
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(title).addGap(20, 20, 20)
				.addComponent(description).addComponent(scroll).addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup().addComponent(play).addComponent(create)));
	}

	public TileOPage getPage() {
		return this;
	}

	public void refresh() {
		tileO = TileOApplication.getTileO();
		tileOGames = tileO.getGames();
		model = new DefaultListModel();
		for (int i = 0; i < tileOGames.size(); i++) {
			Game current = tileOGames.get(i);
			int index = i + 1;
			String currentMode;
			if (current.getMode() == Game.Mode.DESIGN) {
				currentMode = "DESIGN MODE";
			} else if (current.getMode() == Game.Mode.GAME_WON) {
				currentMode = "GAME OVER";
			} else {
				currentMode = "GAME MODE";
			}
			existingGames.put("Game " + index + " - " + currentMode, current);
		}

		for (String s : existingGames.keySet()) {
			model.addElement(s);
		}

		games.setModel(model);
	}

	class CreateListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			new CreateGamePage(getPage()).setVisible(true);
		}
	}

	class PlayListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			DesignModeController dmc = new DesignModeController();
			Game g = null;
			if (!games.isSelectionEmpty()) {
				String gameName = (String) games.getSelectedValue();
				g = existingGames.get(gameName);
				if (g != null) {
					if (g.getMode() == Mode.DESIGN) {
						dmc.setTileOApplicationCurrentGame(g);
						DesignPage designPage = new DesignPage(getPage());
						designPage.setVisible(true);
						TileOApplication.setDesignGame(designPage);
					} else if (g.getMode() == Mode.GAME_WON){
						showMessage("That game is over.");
					}
					
					else {
						dmc.setTileOApplicationCurrentGame(g);
						GamePage gamePage = new GamePage(getPage());
						gamePage.setVisible(true);
						TileOApplication.setGamePage(gamePage);
					}
				}
			}

			else {
				showMessage("You must select or create a game to load.");
			}

		}
	}
	
	public void showMessage(String s){
		JOptionPane.showMessageDialog(null, s);
	}

}
