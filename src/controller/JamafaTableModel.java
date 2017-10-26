package controller;

import javax.swing.table.AbstractTableModel;

class JamafaTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 1L;
  
  JamafaTableModel() {}
  
  private String[] columnNames = { "Station", "Steel Dikte", "Knop Hoogte", "Knop Breedte", "Onder Kleur", "Boven Kleur", "Boven Rijpheid", "Onder Rijpheid", "Totaal Doorgestuurd" };
  private Object[][] data = new Object[0][];
  
  public int getColumnCount() {
    return columnNames.length;
  }
  
  public int getRowCount() {
    return data.length;
  }
  
  public String getColumnName(int col) {
    return columnNames[col];
  }
  

  public Object getValueAt(int row, int col) { return data[row][col]; }
  
  public boolean isCellEditable(int row, int col) {
    if (col < 2) {
      return false;
    }
    return true;
  }
  
  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }
  
  public void setRowCount(int i) {}
}