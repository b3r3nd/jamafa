package main;

import java.sql.SQLException;
import controller.Doorsturen;
import controller.DoorsturenBackup;

public class Main
{
  public Main() {}
  
  public static void main(String[] args) throws SQLException, java.text.ParseException {
      Doorsturen mainFrame = new Doorsturen();
	 // MainFrame mainFrame = new MainFrame();
    mainFrame.setVisible(true);
  }
}