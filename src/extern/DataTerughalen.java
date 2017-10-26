package extern;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataTerughalen
{
  public DataTerughalen() {}
  
  public static void main(String[] argv)
  {
    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "jamafa";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "berend";
    String password = "super123";
    String fileLocation = "\\\\BJP\\SorteerData\\";
    String fileLocation2 = "\\\\BJP\\SorteerData\\";
    
    try
    {
      Date dNow = new Date();
      SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd_k-m");
      String test = ft.format(dNow);
      String[] parts = test.split("-");
      String[] parts2 = parts[0].split("_");
      
      int uur = Integer.parseInt(parts2[1]);
      int minute = Integer.parseInt(parts[1]);
      int iLoops = uur * 60 + minute - 60;
      
      for (int i = 0; i < iLoops; i++) {
        fileLocation = "\\\\BJP\\SorteerData\\";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dNow);
        cal.add(12, -i);
        Date oneminback = cal.getTime();
        
        fileLocation = fileLocation + ft.format(oneminback) + ".xml";
        System.out.println(fileLocation);
        
        boolean check = new File(fileLocation2, ft.format(oneminback) + ".xml").exists();
        if (check) {
          File fXmlFile = new File(fileLocation);
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(fXmlFile);
          
          doc.getDocumentElement().normalize();
          
          NodeList nList = doc.getElementsByTagName("product");
          
          double element1 = 0.0D;double element2 = 0.0D;double element3 = 0.0D;double element4 = 0.0D;double element5 = 0.0D;double element6 = 0.0D;double element7 = 0.0D;double element8 = 0.0D;
          double element10 = 0.0D;double element11 = 0.0D;double element12 = 0.0D;double element13 = 0.0D;double element14 = 0.0D;double element15 = 0.0D;double element16 = 0.0D;
          double element17 = 0.0D;double element18 = 0.0D;double element19 = 0.0D;double element20 = 0.0D;
          String element21 = "";
          int element9 = 0;
          
          Class.forName(driver).newInstance();
          Connection conn = DriverManager.getConnection(url + dbName, userName, password);
          ResultSet rs = null;
          Statement st = null;
          st = conn.createStatement();
          ArrayList<Integer> stations = new ArrayList();
          
          for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == 1)
            {
              Element eElement = (Element)nNode;
              
              element1 = Double.parseDouble(eElement.getAttribute("GemetenLengte"));
              element2 = Double.parseDouble(eElement.getAttribute("GemetenSteeldikte"));
              element3 = Double.parseDouble(eElement.getAttribute("GemetenKnophoogte"));
              element4 = Double.parseDouble(eElement.getAttribute("GemetenKnopbreedte"));
              element5 = Double.parseDouble(eElement.getAttribute("GemetenKleurwaarde"));
              element6 = Double.parseDouble(eElement.getAttribute("GemetenRijpheid"));
              element7 = Double.parseDouble(eElement.getAttribute("GemetenCoderingBoven"));
              element8 = Double.parseDouble(eElement.getAttribute("GemetenCoderingOnder"));
              element9 = Integer.parseInt(eElement.getAttribute("Stationnummer"));
              element10 = Double.parseDouble(eElement.getAttribute("IngesteldeLengte"));
              element11 = Double.parseDouble(eElement.getAttribute("IngesteldeSteeldikte"));
              element12 = Double.parseDouble(eElement.getAttribute("IngesteldeKnophoogte"));
              element13 = Double.parseDouble(eElement.getAttribute("IngesteldeKnopbreedte"));
              element14 = Double.parseDouble(eElement.getAttribute("IngesteldeKleurMin"));
              element15 = Double.parseDouble(eElement.getAttribute("IngesteldeKleurMax"));
              element16 = Double.parseDouble(eElement.getAttribute("IngesteldeRijpheid"));
              element17 = Double.parseDouble(eElement.getAttribute("IngesteldeRijpheidMin"));
              element18 = Double.parseDouble(eElement.getAttribute("IngesteldeRijpheidMax"));
              element19 = Double.parseDouble(eElement.getAttribute("IngesteldeCodering"));
              element20 = Double.parseDouble(eElement.getAttribute("InsertId"));
              element21 = eElement.getAttribute("InsertTime");
              
              String query = " insert into jamafa (GemetenLengte, GemetenSteeldikte, GemetenKnophoogte, GemetenKnopbreedte, GemetenKleurwaarde, GemetenRijpheid, GemetenCoderingBoven,GemetenCoderingOnder, Stationnummer, InstertTime, InsertId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
              


              Date today = new Date();
              DateFormat format1 = new SimpleDateFormat("yyyy-m-d HH:mm:ss");
              Date date1 = format1.parse(element21);
              
              SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
              SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
              String currentTime = sdf.format(date1);
              String currentDate = sdf2.format(today);
              
              String datetime = currentDate + " " + currentTime;
              
              System.out.println(datetime);
              
              PreparedStatement preparedStmt = conn.prepareStatement(query);
              preparedStmt.setDouble(1, element1);
              preparedStmt.setDouble(2, element2);
              preparedStmt.setDouble(3, element3);
              preparedStmt.setDouble(4, element4);
              preparedStmt.setDouble(5, element5);
              preparedStmt.setDouble(6, element6);
              preparedStmt.setDouble(7, element7);
              preparedStmt.setDouble(8, element8);
              preparedStmt.setInt(9, element9);
              preparedStmt.setString(10, datetime);
              preparedStmt.setDouble(11, element20);
              preparedStmt.execute();
              

              rs = st.executeQuery("SELECT stationnr from jamafaStations");
              while (rs.next()) {
                stations.add(Integer.valueOf(rs.getInt(1)));
              }
              
              if (!stations.contains(Integer.valueOf(element9))) {
                String query2 = "insert into jamafaStations (stationnr, IngesteldeLengte, IngesteldeSteeldikte, IngesteldeKnophoogte, IngesteldeKnopBreede, IngesteldeKleurMin, IngesteldeKleurMax, IngesteldeRijpheid, IngesteldeRijpheidMin, IngesteldeRijpheidMax, IngesteldeCodering) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                

                PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                preparedStmt2.setInt(1, element9);
                preparedStmt2.setDouble(2, element10);
                preparedStmt2.setDouble(3, element11);
                preparedStmt2.setDouble(4, element12);
                preparedStmt2.setDouble(5, element13);
                preparedStmt2.setDouble(6, element14);
                preparedStmt2.setDouble(7, element15);
                preparedStmt2.setDouble(8, element16);
                preparedStmt2.setDouble(9, element17);
                preparedStmt2.setDouble(10, element18);
                preparedStmt2.setDouble(11, element19);
                preparedStmt2.execute();
              } else {
                String query2 = "UPDATE jamafaStations SET IngesteldeLengte = ?, IngesteldeSteeldikte = ?, IngesteldeKnophoogte =?, IngesteldeKnopBreede = ? , IngesteldeKleurMin = ?, IngesteldeKleurMax = ?, IngesteldeRijpheid = ?, IngesteldeRijpheidMin = ?, IngesteldeRijpheidMax = ?, IngesteldeCodering = ? WHERE stationnr=" + 
                  element9;
                PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                preparedStmt2.setDouble(1, element10);
                preparedStmt2.setDouble(2, element11);
                preparedStmt2.setDouble(3, element12);
                preparedStmt2.setDouble(4, element13);
                preparedStmt2.setDouble(5, element14);
                preparedStmt2.setDouble(6, element15);
                preparedStmt2.setDouble(7, element16);
                preparedStmt2.setDouble(8, element17);
                preparedStmt2.setDouble(9, element18);
                preparedStmt2.setDouble(10, element19);
                preparedStmt2.execute();
              }
            }
          }
          conn.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}