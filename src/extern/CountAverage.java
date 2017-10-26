package extern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import entities.Database;
import entities.Station;

public class CountAverage
{
  public CountAverage() {}
  
  public static void main(String[] argv) throws SQLException
  {
    String url = "jdbc:mysql://10.50.137.150:3306/";
    String dbName = "jamafa";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "vagrant";
    
    Database db = new Database();
    Connection con = db.getDatabase();
    Connection conn = null;
    
    double gLengte = 0.0D;double gSteelDikte = 0.0D;double gKnopHoogte = 0.0D;double gKnopBreede = 0.0D;
    double gKleurWaarde = 0.0D;double gRijpheid = 0.0D;int stationNr = 0;
    
    for (int temp = 1; temp < db.getAmountStations(con); temp++) {
      try {
        Station s = new Station(temp);
        Class.forName(driver).newInstance();
        conn = DriverManager.getConnection(url + dbName, userName, password);
        Statement st = null;
        st = conn.createStatement();
        gLengte = db.countAverage(temp, "GemetenLengte", conn);
        gSteelDikte = db.countAverage(temp, "GemetenSteeldikte", conn);
        gKnopHoogte = db.countAverage(temp, "GemetenKnophoogte", conn);
        gKnopBreede = db.countAverage(temp, "GemetenKnopbreedte", conn);
        gKleurWaarde = db.countAverage(temp, "GemetenKleurwaarde", conn);
        gRijpheid = db.countAverage(temp, "GemetenRijpheid", conn);
        stationNr = temp;
        
        String query = " insert into jamafagemiddelde (GemetenLengte, GemetenSteeldikte, GemetenKnopHoogte, GemetenKnopBreedte, GemetenKleurwaarde, GemetenRijpheid, StatationNr, datum) values (?, ?, ?, ?, ?, ?, ?, ?)";
        

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(7, -1);
        Date onedayback = cal.getTime();
        
        String currentTime = sdf.format(onedayback);
        
        System.out.println(currentTime);
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setDouble(1, gLengte);
        preparedStmt.setDouble(2, gSteelDikte);
        preparedStmt.setDouble(3, gKnopHoogte);
        preparedStmt.setDouble(4, gKnopBreede);
        preparedStmt.setDouble(5, gKleurWaarde);
        preparedStmt.setDouble(6, gRijpheid);
        preparedStmt.setInt(7, stationNr);
        preparedStmt.setString(8, currentTime);
        preparedStmt.execute();
        

        double total = db.TelAantalInStation(temp, con, true);
        double dSteel = db.DoorgestuurdSteelDikte(temp, con, true);
        double dKnopH = db.DoorgestuurdKnopHoogte(temp, con, true);
        double dKnopB = db.DoorgestuurdKnopBreedte(temp, con, true);
        double dKleurMin = db.DoorgestuurdOnderKnopMin(temp, con, true);
        double dKleurMax = db.DoorgestuurdBovenKnopMax(temp, con, true);
        double dRijpMin = db.DoorgestuurdOnderRijpMin(temp, con, true);
        double dRijpMax = db.DoorgestuurdBovenRijpMax(temp, con, true);
        double codering = s.getiCodering();
        double totaldgest = db.TotalDoorgestuurd(temp, con, true, codering);
        
        dSteel = dSteel * 100.0D / total;
        dKnopH = dKnopH * 100.0D / total;
        dKnopB = dKnopB * 100.0D / total;
        dKleurMin = dKleurMin * 100.0D / total;
        dKleurMax = dKleurMax * 100.0D / total;
        dRijpMax = dRijpMax * 100.0D / total;
        dRijpMin = dRijpMin * 100.0D / total;
        
        Statement st2 = null;
        st2 = conn.createStatement();
        String query2 = " insert into jamafagemiddeldep (station, steeldikte, knophoogte, knopbreeedte, onderkleur, bovenkleur, bovenrijp, onderrijp, datum) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        

        PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
        preparedStmt2.setInt(1, temp);
        preparedStmt2.setInt(2, (int)dSteel);
        preparedStmt2.setInt(3, (int)dKnopH);
        preparedStmt2.setInt(4, (int)dKnopB);
        preparedStmt2.setInt(5, (int)dKleurMin);
        preparedStmt2.setInt(6, (int)dKleurMax);
        preparedStmt2.setInt(7, (int)dRijpMax);
        preparedStmt2.setInt(8, (int)dRijpMin);
        preparedStmt2.setString(9, currentTime);
        preparedStmt2.execute();

      }
      catch (InstantiationException|IllegalAccessException|ClassNotFoundException e)
      {

        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    Statement st = null;
    st = conn.createStatement();
    st.executeQuery("TRUNCATE TABLE jamafa");
  }
}