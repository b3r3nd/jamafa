package controller;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import entities.Database;
import entities.Station;

public class DoorsturenBackup //extends javax.swing.JFrame implements java.awt.event.ActionListener
{
/**  private javax.swing.JMenuBar menuBar;
  private JMenu vMenu;
  private JMenu gMenu;
  private JMenu dMenu;
  private JMenuItem gMenuItem;
  private JMenuItem aMenuItem;
  private JMenuItem gwMenuItem;
  private JMenuItem gpMenuItem;
  private JMenuItem st1MenuItem;
  private JMenuItem st2MenuItem;
  private JMenuItem st3MenuItem;
  private JMenuItem st4MenuItem;
  private JMenuItem st5MenuItem;
  private JMenuItem st6MenuItem;
  private JMenuItem st7MenuItem;
  private JMenuItem st8MenuItem;
  private static final long serialVersionUID = 1L;
  private javax.swing.JPanel topPanel;
  private javax.swing.JPanel listPanel;
  private JTable table;
  private javax.swing.JScrollPane scrollPane;
  private javax.swing.table.DefaultTableModel tableModel;
  private javax.swing.JButton button = new javax.swing.JButton("Refresh");
  private String[] listt = { "Afgelopen Uur", "Hele dag" };
  private javax.swing.JComboBox<?> listt2 = new javax.swing.JComboBox(listt);
  private int aStations = 8;
  private javax.swing.JLabel aRozen = new javax.swing.JLabel("  Aantal Rozen: ");
  private Database db = new Database();
  private String[] col = { "Station", "Steel Dikte", "Knop Hoogte", "Knop Breedte", "Onder Kleur", "Boven Kleur", "Boven Rijpheid", "Onder Rijpheid", "Totaal Doorgestuurd" };
  
  **/
  public DoorsturenBackup()
    throws SQLException, ParseException
  {
  /**  menuBar = new javax.swing.JMenuBar();
    
    dMenu = new JMenu("Doorsturen");
    
    st1MenuItem = new JMenuItem("Station 1");
    st2MenuItem = new JMenuItem("Station 2");
    st3MenuItem = new JMenuItem("Station 3");
    st4MenuItem = new JMenuItem("Station 4");
    st5MenuItem = new JMenuItem("Station 5");
    st6MenuItem = new JMenuItem("Station 6");
    st7MenuItem = new JMenuItem("Station 7");
    st8MenuItem = new JMenuItem("Station 8");
    
    st1MenuItem.addActionListener(this);
    st2MenuItem.addActionListener(this);
    st3MenuItem.addActionListener(this);
    st4MenuItem.addActionListener(this);
    st5MenuItem.addActionListener(this);
    st6MenuItem.addActionListener(this);
    st7MenuItem.addActionListener(this);
    st8MenuItem.addActionListener(this);
    
    dMenu.add(st1MenuItem);
    dMenu.add(st2MenuItem);
    dMenu.add(st3MenuItem);
    dMenu.add(st4MenuItem);
    dMenu.add(st5MenuItem);
    dMenu.add(st6MenuItem);
    dMenu.add(st7MenuItem);
    dMenu.add(st8MenuItem);
    

    vMenu = new JMenu("Vandaag");
    gMenuItem = new JMenuItem("Gehele Dag");
    aMenuItem = new JMenuItem("Afgelopen Uur");
    
    gMenuItem.addActionListener(this);
    aMenuItem.addActionListener(this);
    vMenu.add(gMenuItem);
    vMenu.add(aMenuItem);
    
    gMenu = new JMenu("Geschiedenis");
    gwMenuItem = new JMenuItem("Gemiddelde waardes");
    gpMenuItem = new JMenuItem("Gemiddelde Procenten");
    gwMenuItem.addActionListener(this);
    gpMenuItem.addActionListener(this);
    gMenu.add(gwMenuItem);
    gMenu.add(gpMenuItem);
    
    menuBar.add(vMenu);
    menuBar.add(gMenu);
    menuBar.add(dMenu);
    
    setJMenuBar(menuBar);
    
    setTitle("Doorsturen");
    setSize(1500, 465);
    setResizable(false);
    setLayout(new java.awt.BorderLayout());
    listPanel = new javax.swing.JPanel();
    listPanel.setLayout(new java.awt.BorderLayout());
    topPanel = new javax.swing.JPanel();
    topPanel.setLayout(new java.awt.BorderLayout());
    getContentPane().add(listPanel, "First");
    getContentPane().add(topPanel, "Center");
    

    tableModel = new javax.swing.table.DefaultTableModel(col, 0);
    table = new JTable(tableModel);
    table.setFont(new java.awt.Font("Serif", 0, 20));
    table.setRowHeight(45);
    button.addActionListener(this);
    javax.swing.table.JTableHeader header = table.getTableHeader();
    header.setBackground(Color.GREEN);
    header.setFont(new java.awt.Font("Arial", 1, 15));
    
    scrollPane = new javax.swing.JScrollPane(table);
    topPanel.add(scrollPane, "Center");
    
    aRozen.setPreferredSize(new java.awt.Dimension(1350, 20));
    
    listPanel.add(aRozen, "After");
    listPanel.add(button, "Center");
    
    calculate(1);
    
    aRozen.setForeground(Color.GREEN);
    table.setGridColor(Color.GREEN);
    listt2.setBackground(Color.BLACK);
    listt2.setForeground(Color.GREEN);
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
    table.setFocusable(false);
    table.setRowSelectionAllowed(false);
    TableColumn column = table.getColumnModel().getColumn(0);
    column.setMaxWidth(200); **/
  }
  
  public void calculate(int ie) throws SQLException, ParseException
  {
    /** Object result = null;
    



    java.util.Date currentDate = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    java.util.Calendar cal = java.util.Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(7, -1);
    java.util.Date onedayback = cal.getTime();
    System.out.println(onedayback);
    
    java.text.SimpleDateFormat sdff = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String datum = sdff.format(onedayback);
    
    System.out.println(datum);
    
    javax.swing.table.JTableHeader th = table.getTableHeader();
    TableColumnModel tcm = th.getColumnModel();
    TableColumn tc0 = tcm.getColumn(0);
    tc0.setHeaderValue("Station");
    TableColumn tc = tcm.getColumn(1);
    tc.setHeaderValue("Steel Dikte");
    TableColumn tc3 = tcm.getColumn(2);
    tc3.setHeaderValue("Knop Hoogte");
    TableColumn tc4 = tcm.getColumn(3);
    tc4.setHeaderValue("Knop Breedte");
    TableColumn tc5 = tcm.getColumn(4);
    tc5.setHeaderValue("Onder Kleur");
    TableColumn tc6 = tcm.getColumn(5);
    tc6.setHeaderValue("Boven Kleur");
    TableColumn tc7 = tcm.getColumn(6);
    tc7.setHeaderValue("Boven Rijpheid");
    TableColumn tc8 = tcm.getColumn(7);
    tc8.setHeaderValue("Onder Rijpheid");
    TableColumn tcc9 = tcm.getColumn(8);
    tcc9.setHeaderValue("Totaal");
    
    th.repaint();
    db = new Database();
    java.sql.Connection con = db.getDatabase();
    ResultSet rs = null;
    entities.Station s = null;
    ArrayList<String> tableProcenten = new ArrayList();
    tableModel.setRowCount(0);
    
    int total = 0;int dSteel = 0;int dKnopH = 0;int dKnopB = 0;int dKleurMin = 0;int dKleurMax = 0;int totaldgest = 0;int instat = 0;
    double codering = 0.0D;
    int dRijpMin = 0;int dRijpMax = 0;
    
    for (int temp = 1; temp < db.getAmountStations(con); temp++) {
      s = new entities.Station(temp);
      




      if (ie == 0) {
        total = db.TelAantalInStation(temp, con, true);
        dSteel = db.DoorgestuurdSteelDikte(temp, con, true);
        dKnopH = db.DoorgestuurdKnopHoogte(temp, con, true);
        dKnopB = db.DoorgestuurdKnopBreedte(temp, con, true);
        dKleurMin = db.DoorgestuurdOnderKnopMin(temp, con, true);
        dKleurMax = db.DoorgestuurdBovenKnopMax(temp, con, true);
        dRijpMin = db.DoorgestuurdOnderRijpMin(temp, con, true);
        dRijpMax = db.DoorgestuurdBovenRijpMax(temp, con, true);
        codering = s.getiCodering();
        totaldgest = db.TotalDoorgestuurd(temp, con, true, codering);
      }
      
      if (ie == 1) {
        total = db.TelAantalInStation(temp, con, false);
        dSteel = db.DoorgestuurdSteelDikte(temp, con, false);
        dKnopH = db.DoorgestuurdKnopHoogte(temp, con, false);
        dKnopB = db.DoorgestuurdKnopBreedte(temp, con, false);
        dKleurMin = db.DoorgestuurdOnderKnopMin(temp, con, false);
        dKleurMax = db.DoorgestuurdBovenKnopMax(temp, con, false);
        dRijpMin = db.DoorgestuurdOnderRijpMin(temp, con, false);
        dRijpMax = db.DoorgestuurdBovenRijpMax(temp, con, false);
        codering = s.getiCodering();
        totaldgest = db.TotalDoorgestuurd(temp, con, false, codering);
      }
      
      if (ie == 2) {
        javax.swing.table.JTableHeader thh = table.getTableHeader();
        TableColumnModel tcmm = thh.getColumnModel();
        TableColumn tccq9 = tcmm.getColumn(8);
        tccq9.setHeaderValue("Datum");
        
        thh.repaint();
        
        ResultSet testp = db.getGemWaardesp(con, temp, datum);
        testp.next();
        
        tableProcenten.add(" " + temp);
        tableProcenten.add(testp.getInt(2) + "%");
        tableProcenten.add(testp.getInt(3) + "%");
        tableProcenten.add(testp.getInt(4) + "%");
        tableProcenten.add(testp.getInt(5) + "%");
        tableProcenten.add(testp.getInt(6) + "%");
        tableProcenten.add(testp.getInt(7) + "%");
        tableProcenten.add(testp.getInt(8) + "%");
        tableProcenten.add(testp.getString(9));
        
        total = 0;dSteel = 0;dKnopH = 0;dKnopB = 0;dKleurMax = 0;dKleurMin = 0;dRijpMin = 0;dRijpMax = 0;totaldgest = 0;
      }
      

      if (ie == 3) {
        javax.swing.table.JTableHeader thh = table.getTableHeader();
        TableColumnModel tcmm = thh.getColumnModel();
        
        TableColumn tcc = tcmm.getColumn(1);
        tcc.setHeaderValue("Gemeten Lengthe");
        
        TableColumn tcc3 = tcmm.getColumn(2);
        tcc3.setHeaderValue("Gemeten Steeldikte");
        
        TableColumn tcc4 = tcmm.getColumn(3);
        tcc4.setHeaderValue("Gemeten Knophoogte");
        
        TableColumn tcc5 = tcmm.getColumn(4);
        tcc5.setHeaderValue("Gemeten Knopbreedte");
        
        TableColumn tcc6 = tcmm.getColumn(5);
        tcc6.setHeaderValue("Gemeten Kleurwaarde");
        
        TableColumn tcc7 = tcmm.getColumn(6);
        tcc7.setHeaderValue("Gemeten Rijpheid");
        
        TableColumn tcc8 = tcmm.getColumn(7);
        tcc8.setHeaderValue("Datum");
        
        TableColumn tccb9 = tcmm.getColumn(8);
        tccb9.setHeaderValue("Insert ID");
        
        thh.repaint();
        

        ResultSet test = db.getGemWaardes(con, temp, datum);
        
        test.next();
        tableProcenten.add(test.getInt(8) + "");
        tableProcenten.add(test.getInt(2) + "");
        tableProcenten.add(test.getInt(3) + "");
        tableProcenten.add(test.getInt(4) + "");
        tableProcenten.add(test.getInt(5) + "");
        tableProcenten.add(test.getInt(6) + "");
        tableProcenten.add(test.getInt(7) + "");
        tableProcenten.add(test.getString(9)+ "");
        tableProcenten.add(test.getInt(1)+ "" );
        
        total = 0;dSteel = 0;dKnopH = 0;dKnopB = 0;dKleurMax = 0;dKleurMin = 0;dRijpMin = 0;dRijpMax = 0;totaldgest = 0;
      }
      

      instat = total;
      total = total + 1 + totaldgest;
      if ((temp <= aStations) && (total != 0))
      {




        if ((ie != 2) && (ie != 3) && (ie != 4) && (ie != 5)) {
          tableProcenten.add(" " + temp);
          double temp2 = dSteel * 100 / total;
          Integer tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dKnopH * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dKnopB * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dKleurMin * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dKleurMax * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dRijpMax * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          temp2 = dRijpMin * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          
          temp2 = totaldgest * 100 / total;
          tempp2 = Integer.valueOf((int)temp2);
          tableProcenten.add(tempp2 + "%");
          
          total = 0;dSteel = 0;dKnopH = 0;dKnopB = 0;dKleurMax = 0;dKleurMin = 0;dRijpMin = 0;dRijpMax = 0;totaldgest = 0;
        }
      }
    }
    int size = tableProcenten.size() / 9;
    int i2 = 0;int i3 = 0;int i4 = 0;int ii = 0;int i5 = 0;int i6 = 0;int i7 = 0;int i8 = 0;int i9 = 0;
    for (int i = 0; i < size; i++) {
      if (i == 0) { ii = i;i2 = i + 1;i3 = i + 2;i4 = i + 3;i5 = i + 4;i6 = i + 5;i7 = i + 6;i8 = i + 7;i9 = i + 8;
      } else { ii += 9;i2 += 9;i3 += 9;i4 += 9;i5 += 9;i6 += 9;i7 += 9;i8 += 9;i9 += 9; }
     
      String st = tableProcenten.get(ii).replaceAll("\\s","");
      
      int stationId = Integer.parseInt(st);
      Station ss = new Station(stationId);
      double dLengteStation = ss.getiLengte();
      String sLengteStation = Double.toString(dLengteStation);
     
      Object[] objs = { tableProcenten.get(ii) + " - " + sLengteStation, tableProcenten.get(i2), tableProcenten.get(i3), tableProcenten.get(i4), tableProcenten.get(i5), tableProcenten.get(i6), tableProcenten.get(i7), tableProcenten.get(i8), tableProcenten.get(i9) };
      tableModel.addRow(objs);
    }
    int ai = db.TelRozen(con, ie);
    aRozen.setText("  Aantal Rozen: " + ai);
    int extraStation = 1;
    if ((ie == 5) || (ie == 6) || (ie == 7) || (ie == 8) || (ie == 9) || (ie == 10) || (ie == 11) || (ie == 12)) {
      if (ie == 5) extraStation = 1;
      if (ie == 6) extraStation = 2;
      if (ie == 7) extraStation = 3;
      if (ie == 8) extraStation = 4;
      if (ie == 9) extraStation = 5;
      if (ie == 10) extraStation = 6;
      if (ie == 11) extraStation = 7;
      if (ie == 12) { extraStation = 8;
      }
      tableModel.setRowCount(0);
      javax.swing.table.JTableHeader thh = table.getTableHeader();
      TableColumnModel tcmm = thh.getColumnModel();
      TableColumn tcd3 = tcmm.getColumn(0);
      tcd3.setHeaderValue("Station" + extraStation);
      TableColumn tcc = tcmm.getColumn(1);
      tcc.setHeaderValue("3-");
      TableColumn tcc3 = tcmm.getColumn(2);
      tcc3.setHeaderValue("2-");
      TableColumn tcc4 = tcmm.getColumn(3);
      tcc4.setHeaderValue("1-");
      TableColumn tcc5 = tcmm.getColumn(4);
      tcc5.setHeaderValue("Doorgestuurd");
      TableColumn tcc6 = tcmm.getColumn(5);
      tcc6.setHeaderValue("1+");
      TableColumn tcc7 = tcmm.getColumn(6);
      tcc7.setHeaderValue("2+");
      TableColumn tcc8 = tcmm.getColumn(7);
      tcc8.setHeaderValue("3+");
      TableColumn tccqw9 = tcmm.getColumn(8);
      tccqw9.setHeaderValue("Ingestelde Waarde");
      thh.repaint();
      
      ResultSet station = db.getStationInfo(extraStation, con);
      station.next();
      int t = db.TelAantalInStation(extraStation, con, false);
      int tg = db.TotalDoorgestuurd(extraStation, con, false, station.getInt(11));
      int at = t + tg;
      
      double dsd = db.DoorgestuurdSteelDikte(extraStation, con, false);dsd = dsd * 100.0D / at;
      double dsdMin1 = db.DoorgestuurdSteelDikteExtra(extraStation, con, -1);dsdMin1 = dsdMin1 * 100.0D / at;
      double dsdMin2 = db.DoorgestuurdSteelDikteExtra(extraStation, con, -2);dsdMin1 = dsdMin2 * 100.0D / at;
      double dsdMin3 = db.DoorgestuurdSteelDikteExtra(extraStation, con, -3);dsdMin1 = dsdMin3 * 100.0D / at;
      double dsdPlus1 = db.DoorgestuurdSteelDikteExtra(extraStation, con, 1);dsdMin1 = dsdPlus1 * 100.0D / at;
      double dsdPlus2 = db.DoorgestuurdSteelDikteExtra(extraStation, con, 2);dsdMin1 = dsdPlus2 * 100.0D / at;
      double dsdPlus3 = db.DoorgestuurdSteelDikteExtra(extraStation, con, 3);dsdMin1 = dsdPlus3 * 100.0D / at;
      
      double dskh = db.DoorgestuurdKnopHoogte(extraStation, con, false);dskh = dskh * 100.0D / at;
      double dskhMin1 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, -1);dskhMin1 = dskhMin1 * 100.0D / at;
      double dskhMin2 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, -2);dskhMin2 = dskhMin2 * 100.0D / at;
      double dskhMin3 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, -3);dskhMin3 = dskhMin3 * 100.0D / at;
      double dskhPlus1 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, 1);dskhPlus1 = dskhPlus1 * 100.0D / at;
      double dskhPlus2 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, 2);dskhPlus2 = dskhPlus2 * 100.0D / at;
      double dskhPlus3 = db.DoorgestuurdKnopHoogteExtra(extraStation, con, 3);dskhPlus3 = dskhPlus3 * 100.0D / at;
      

      double dskb = db.DoorgestuurdKnopBreedte(extraStation, con, false);dskb = dskb * 100.0D / at;
      double dskbMin1 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, -1);dskbMin1 = dskbMin1 * 100.0D / at;
      double dskbMin2 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, -2);dskbMin2 = dskbMin2 * 100.0D / at;
      double dskbMin3 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, -3);dskbMin3 = dskbMin3 * 100.0D / at;
      double dskbPlus1 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, 1);dskbPlus1 = dskbPlus1 * 100.0D / at;
      double dskbPlus2 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, 2);dskbPlus2 = dskbPlus2 * 100.0D / at;
      double dskbPlus3 = db.DoorgestuurdKnopBreedteExtra(extraStation, con, 3);dskbPlus3 = dskbPlus3 * 100.0D / at;
      

      double dsklb = db.DoorgestuurdOnderKnopMin(extraStation, con, false);dsklb = dsklb * 100.0D / at;
      double dsklbMin1 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -1);dsklbMin1 = dsklbMin1 * 100.0D / at;
      double dsklbMin2 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -2);dsklbMin2 = dsklbMin2 * 100.0D / at;
      double dsklbMin3 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -3);dsklbMin3 = dsklbMin3 * 100.0D / at;
      double dsklbPlus1 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 1);dsklbPlus1 = dsklbPlus1 * 100.0D / at;
      double dsklbPlus2 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 2);dsklbPlus2 = dsklbPlus2 * 100.0D / at;
      double dsklbPlus3 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 3);dsklbPlus3 = dsklbPlus3 * 100.0D / at;
      

      double dsklo = db.DoorgestuurdOnderKnopMin(extraStation, con, false);dsklo = dsklo * 100.0D / at;
      double dskloMin1 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -1);dskloMin1 = dskloMin1 * 100.0D / at;
      double dskloMin2 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -2);dskloMin2 = dskloMin2 * 100.0D / at;
      double dskloMin3 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, -3);dskloMin3 = dskloMin3 * 100.0D / at;
      double dskloPlus1 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 1);dskloPlus1 = dskloPlus1 * 100.0D / at;
      double dskloPlus2 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 2);dskloPlus2 = dskloPlus2 * 100.0D / at;
      double dskloPlus3 = db.DoorgestuurdOnderKnopMinExtra(extraStation, con, 3);dskloPlus3 = dskloPlus3 * 100.0D / at;
      

      double dsrb = db.DoorgestuurdBovenRijpMax(extraStation, con, false);dsrb = dsrb * 100.0D / at;
      double dsrbMin1 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, -1);dsrbMin1 = dsrbMin1 * 100.0D / at;
      double dsrbMin2 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, -2);dsrbMin2 = dsrbMin2 * 100.0D / at;
      double dsrbMin3 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, -3);dsrbMin3 = dsrbMin3 * 100.0D / at;
      double dsrbPlus1 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, 1);dsrbPlus1 = dsrbPlus1 * 100.0D / at;
      double dsrbPlus2 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, 2);dsrbPlus2 = dsrbPlus2 * 100.0D / at;
      double dsrbPlus3 = db.DoorgestuurdBovenRijpMaxExtra(extraStation, con, 3);dsrbPlus3 = dsrbPlus3 * 100.0D / at;
      

      double dsro = db.DoorgestuurdOnderRijpMin(extraStation, con, false);dsro = dsro * 100.0D / at;
      double dsroMin1 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, -1);dsroMin1 = dsroMin1 * 100.0D / at;
      double dsroMin2 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, -2);dsroMin2 = dsroMin2 * 100.0D / at;
      double dsroMin3 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, -3);dsroMin3 = dsroMin3 * 100.0D / at;
      double dsroPlus1 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, 1);dsroPlus1 = dsroPlus1 * 100.0D / at;
      double dsroPlus2 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, 2);dsroPlus2 = dsroPlus2 * 100.0D / at;
      double dsroPlus3 = db.DoorgestuurdOnderRijpMinExtra(extraStation, con, 3);dsroPlus3 = dsroPlus3 * 100.0D / at;
      



      Object[] objs2 = { "Steel Dikte", (int)dsdMin3 + "%", (int)dsdMin2 + "%", (int)dsdMin1 + "%", (int)dsd + "%", (int)dsdPlus1 + "%", (int)dsdPlus2 + "%", (int)dsdPlus3 + "%", station.getString(3) };
      tableModel.addRow(objs2);
      Object[] objs3 = { "Knop Hoogte", (int)dskhMin3 + "%", (int)dskhMin2 + "%", (int)dskhMin1 + "%", (int)dskh + "%", (int)dskhPlus1 + "%", (int)dskhPlus2 + "%", (int)dskhPlus3 + "%", station.getString(4) };
      tableModel.addRow(objs3);
      Object[] objs4 = { "Knop Breedte", (int)dskbMin3 + "%", (int)dskbMin2 + "%", (int)dskbMin1 + "%", (int)dskb + "%", (int)dskbPlus1 + "%", (int)dskbPlus2 + "%", (int)dskbPlus3 + "%", station.getString(5) };
      tableModel.addRow(objs4);
      Object[] objs5 = { "Kleur Boven", (int)dsklbMin3 + "%", (int)dsklbMin2 + "%", (int)dsklbMin1 + "%", (int)dsklb + "%", (int)dsklbPlus1 + "%", (int)dsklbPlus2 + "%", (int)dsklbPlus3 + "%", station.getString(6) };
      tableModel.addRow(objs5);
      Object[] objs6 = { "Kleur Onder", (int)dskloMin3 + "%", (int)dskloMin2 + "%", (int)dskloMin1 + "%", (int)dsklo + "%", (int)dskloPlus1 + "%", (int)dskloPlus2 + "%", (int)dskloPlus3 + "%", station.getString(7) };
      tableModel.addRow(objs6);
      Object[] objs7 = { "Rijpheid Boven", (int)dsrbMin3 + "%", (int)dsrbMin2 + "%", (int)dsrbMin1 + "%", (int)dsrb + "%", (int)dsrbPlus1 + "%", (int)dsrbPlus2 + "%", (int)dsrbPlus3 + "%", station.getString(8) };
      tableModel.addRow(objs7);
      Object[] objs8 = { "Rijpheid Onder", (int)dsroMin3 + "%", (int)dsroMin2 + "%", (int)dsroMin1 + "%", (int)dsro + "%", (int)dsroPlus1 + "%", (int)dsroPlus2 + "%", (int)dsroPlus3 + "%", station.getString(9) };
      tableModel.addRow(objs8);
      
      TableColumn column = table.getColumnModel().getColumn(0);
      column.setWidth(2000);
      column.setMaxWidth(250);
      column.setMinWidth(200);
    } **/
  }
  
  public void actionPerformed(java.awt.event.ActionEvent e) {
   /** if (e.getSource() == st1MenuItem) {
      try {
        calculate(5);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st2MenuItem) {
      try {
        calculate(6);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st3MenuItem) {
      try {
        calculate(7);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st4MenuItem) {
      try {
        calculate(8);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st5MenuItem) {
      try {
        calculate(9);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st6MenuItem) {
      try {
        calculate(10);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st7MenuItem) {
      try {
        calculate(11);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == st8MenuItem) {
      try {
        calculate(12);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    
    if (e.getSource() == gMenuItem) {
      try {
        calculate(1);
      } catch (SQLException e1) {
        e1.printStackTrace();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == aMenuItem) {
      try {
        calculate(0);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == gpMenuItem) {
      try {
        calculate(2);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == gwMenuItem) {
      try {
        calculate(3);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
    }
    if (e.getSource() == button)
    {


      try
      {


        calculate(1);
      } catch (SQLException|ParseException e1) {
        e1.printStackTrace();
      }
      tableModel.fireTableDataChanged();
      revalidate();
      repaint();
    }
    tableModel.fireTableDataChanged();
    revalidate();
    repaint();  **/
  }
}