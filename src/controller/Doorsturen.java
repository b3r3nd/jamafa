package controller;

import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import entities.Database;
import entities.Station;

public class Doorsturen extends javax.swing.JFrame implements java.awt.event.ActionListener {

	private static final long serialVersionUID = 1L;
	private Database database = new Database();
	private int aantalStations = 8;

	private javax.swing.JMenuBar menuBar;
	private JMenu vandaagMenu, doorsturenMenu;
	private ArrayList<JMenuItem> doorsturenMenuItems = new ArrayList<JMenuItem>();
	private JMenuItem geheleDagMenuItem, afgelopenUurMenuItem;

	private javax.swing.JPanel topPanel, listPanel;
	private JTable table;
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.table.DefaultTableModel tableModel;

	private javax.swing.JButton refreshButton = new javax.swing.JButton("Refresh");
	private javax.swing.JLabel aRozenLabel = new javax.swing.JLabel("  Aantal Rozen: ");
	private String[] tableHeadCols = { "Station", "Steel Dikte", "Knop Hoogte", "Knop Breedte", "Onder Kleur",
			"Boven Kleur", "Boven Rijpheid", "Onder Rijpheid", "Totaal Doorgestuurd" };

	/**
	 * @throws SQLException
	 * @throws ParseException
	 */
	public Doorsturen() throws SQLException, ParseException {
		this.menuInit();

		/**
		 * Set layout for the window
		 */
		setTitle("Doorsturen");
		setSize(1600, 465);
		setResizable(false);
		setLayout(new java.awt.BorderLayout());
		
		
		
		/**
		 * Setup Table model and layout
		 */
		tableModel = new javax.swing.table.DefaultTableModel(tableHeadCols, 0);
		table = new JTable(tableModel);
		table.setFont(new java.awt.Font("Arial", 1, 25));
		table.setRowHeight(45);
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		table.getColumnModel().getColumn(0).setWidth(200);
		
		
		/**
		 * Set layout table header
		 */
		javax.swing.table.JTableHeader header = table.getTableHeader();
		header.setBackground(Color.GREEN);
		header.setFont(new java.awt.Font("Arial", 1, 25));
		
		
		/**
		 * Add everything to screen
		 */
		listPanel = new javax.swing.JPanel();
		listPanel.setLayout(new java.awt.BorderLayout());
		
		topPanel = new javax.swing.JPanel();
		topPanel.setLayout(new java.awt.BorderLayout());
		
		getContentPane().add(listPanel, "First");
		getContentPane().add(topPanel, "Center");
		
		scrollPane = new javax.swing.JScrollPane(table);
		aRozenLabel.setPreferredSize(new java.awt.Dimension(1350, 35));
		aRozenLabel.setFont(new java.awt.Font("Arial", 1, 25));
		
		refreshButton.addActionListener(this);
		refreshButton.setFont(new java.awt.Font("Arial", 1, 25));
		topPanel.add(scrollPane, "Center");
		listPanel.add(aRozenLabel, "After");
		listPanel.add(refreshButton, "Center");

	
		this.calculateDoorgestuurd(false);
		this.setupColors();
	}

	/**
	 * Calculates the values and updates the table model
	 * 
	 * @param ie
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculateDoorgestuurd(boolean isAfgelopenUur) throws SQLException, ParseException {
		/**
		 * Setup Table
		 */
		javax.swing.table.JTableHeader th = table.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		tcm.getColumn(0).setHeaderValue("Station");
		tcm.getColumn(1).setHeaderValue("Steel Dikte");
		tcm.getColumn(2).setHeaderValue("Knop Hoogte");
		tcm.getColumn(3).setHeaderValue("Knop Breedte");
		tcm.getColumn(4).setHeaderValue("Onder Kleur");
		tcm.getColumn(5).setHeaderValue("Boven Kleur");
		tcm.getColumn(6).setHeaderValue("Boven Rijpheid");
		tcm.getColumn(7).setHeaderValue("Onder Rijpheid");
		tcm.getColumn(8).setHeaderValue("Totaal");
		th.repaint();

		java.sql.Connection con = database.getDatabase();
		entities.Station s = null;
		ArrayList<ArrayList<String>> tableProcenten = new ArrayList<ArrayList<String>>();
		tableModel.setRowCount(0);

		/**
		 * We receive for each station all the values, we create a multidimensional ArrayList with the first level being all the stations
		 * and the second level for each station the different values
		 */
		for (int temp = 1; temp < database.getAmountStations(con); temp++) {
			s = new entities.Station(temp);

			int total = database.TelAantalInStation(temp, con, isAfgelopenUur);
			int dSteel = database.DoorgestuurdSteelDikte(temp, con, isAfgelopenUur);
			int dKnopH = database.DoorgestuurdKnopHoogte(temp, con, isAfgelopenUur);
			int dKnopB = database.DoorgestuurdKnopBreedte(temp, con, isAfgelopenUur);
			int dKleurMin = database.DoorgestuurdOnderKnopMin(temp, con, isAfgelopenUur);
			int dKleurMax = database.DoorgestuurdBovenKnopMax(temp, con, isAfgelopenUur);
			int dRijpMin = database.DoorgestuurdOnderRijpMin(temp, con, isAfgelopenUur);
			int dRijpMax = database.DoorgestuurdBovenRijpMax(temp, con, isAfgelopenUur);
			double codering = s.getiCodering();
			int totaalDoorgestuurd = database.TotalDoorgestuurd(temp, con, isAfgelopenUur, codering);

			total = total + 1 + totaalDoorgestuurd;
			if ((temp <= aantalStations) && (total != 0)) {
				ArrayList<String> procentenRow = new ArrayList<String>();

				procentenRow.add(" " + temp + " - " + s.getiLengte());
				procentenRow.add(Integer.valueOf((int) (dSteel * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dKnopH * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dKnopB * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dKleurMin * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dKleurMax * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dRijpMax * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (dRijpMin * 100 / total)) + "%");
				procentenRow.add(Integer.valueOf((int) (totaalDoorgestuurd * 100 / total)) + "%");

				tableProcenten.add(procentenRow);

			}
		}

		int aantalStationsArray = tableProcenten.size();

		for (int i = 0; i < aantalStationsArray; i++) {
			int aantalRowsArray = tableProcenten.get(i).size();
			ArrayList<Object> rowColumnsArray = new ArrayList<Object>();

			for (int z = 0; z < aantalRowsArray; z++) {
				rowColumnsArray.add(tableProcenten.get(i).get(z));
			}

			Object[] rowColumnsObjects = rowColumnsArray.toArray();
			tableModel.addRow(rowColumnsObjects);

		}

		/**
		 * Update totaal aantal rozen
		 */
		int ai = database.TelRozen(con, isAfgelopenUur);
		aRozenLabel.setText("  Aantal Rozen: " + ai);
	}

	/**
	 * Action Performed runs when a button is pressed or menu item is clicked.
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		boolean isAfgelopenUur = true;
	
		if (e.getSource() == geheleDagMenuItem || e.getSource() == refreshButton) {
			isAfgelopenUur = false;
		}
		else if (e.getSource() == afgelopenUurMenuItem) {
			isAfgelopenUur = true;
		}
		
		try {
			calculateDoorgestuurd(isAfgelopenUur);
		} catch (SQLException | ParseException e2) {
			e2.printStackTrace();
		}

		tableModel.fireTableDataChanged();
		revalidate();
		repaint();
	}

	/**
	 * Sets up the Menu Bar at the top of the window. It adds three lists: - Vandaag
	 * - Gehele Dag - Afgelopen Uur - Geschiedenis - Waardes - Procenten -
	 * Doorsturen - Station 1- 8
	 */
	private void menuInit() {
		/**
		 * Vandaag Menu
		 */
		vandaagMenu = new JMenu("Vandaag");
		geheleDagMenuItem = new JMenuItem("Gehele Dag");
		afgelopenUurMenuItem = new JMenuItem("Afgelopen Uur");
		geheleDagMenuItem.addActionListener(this);
		afgelopenUurMenuItem.addActionListener(this);
		vandaagMenu.add(geheleDagMenuItem);
		vandaagMenu.add(afgelopenUurMenuItem);

		/**
		 * Doorsturen Menu
		 */
		doorsturenMenu = new JMenu("Doorsturen");
		for (int i = 1; i < aantalStations; i++) {
			JMenuItem tempMenuItem = new JMenuItem("Station " + i);
			doorsturenMenuItems.add(tempMenuItem);
			doorsturenMenu.add(tempMenuItem);
		}

		/**
		 * Create menu bar and add the three menus
		 */
		menuBar = new javax.swing.JMenuBar();
		menuBar.add(vandaagMenu);
		menuBar.add(doorsturenMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Function to add the Jamafa colors to the table.
	 */
	private void setupColors() {
		aRozenLabel.setForeground(Color.GREEN);
		table.setGridColor(Color.GREEN);

		listPanel.setBackground(Color.BLACK);
		listPanel.setForeground(Color.GREEN);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setForeground(Color.GREEN);
		table.setBackground(Color.BLACK);
		table.setForeground(Color.GREEN);
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
		topPanel.setBackground(Color.BLACK);
		topPanel.setForeground(Color.GREEN);
	}
}