package entities;

import java.sql.ResultSet;

public class Station {
  private int stationNummer;
  private double iLengte;
  private double iSteeldikte;
  private double iKnopHoogte;
  private double iKnopBreede;
  private double iKleurMax;
  private double iKeurMin;
  private double iRijpheid;
  private double iRijpheidMax;
  private double iRijpheidMin;
  private double iCodering;
  
  public Station(int stationNr) { entities.Database db = new entities.Database();
    java.sql.Connection con = db.getDatabase();
    ResultSet rs = db.getStationInfo(stationNr, con);
    stationNummer = stationNr;
    try {
      rs.next();
      iLengte = rs.getDouble(2);
      iSteeldikte = rs.getDouble(3);
      iKnopHoogte = rs.getDouble(4);
      iKnopBreede = rs.getDouble(5);
      iKleurMax = rs.getDouble(7);
      iKeurMin = rs.getDouble(6);
      iRijpheid = rs.getDouble(8);
      iRijpheidMax = rs.getDouble(10);
      iRijpheidMin = rs.getDouble(9);
      iCodering = rs.getDouble(11);
    } catch (java.sql.SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection(con);
  }
  
  public int getStationNummer() {
    return stationNummer;
  }
  
  public void setStationNummer(int stationNummer) {
    this.stationNummer = stationNummer;
  }
  
  public double getiLengte() { return iLengte; }
  
  public void setiLengte(int iLengte) {
    this.iLengte = iLengte;
  }
  
  public double getiSteeldikte() { return iSteeldikte; }
  
  public void setiSteeldikte(double iSteeldikte) {
    this.iSteeldikte = iSteeldikte;
  }
  
  public double getiKnopHoogte() { return iKnopHoogte; }
  
  public void setiKnopHoogte(double iKnopHoogte) {
    this.iKnopHoogte = iKnopHoogte;
  }
  
  public double getiKnopBreede() { return iKnopBreede; }
  
  public void setiKnopBreede(double iKnopBreede) {
    this.iKnopBreede = iKnopBreede;
  }
  
  public double getiKleurMax() { return iKleurMax; }
  
  public void setiKleurMax(double iKleurMax) {
    this.iKleurMax = iKleurMax;
  }
  
  public double getiKeurMin() { return iKeurMin; }
  
  public void setiKeurMin(double iKeurMin) {
    this.iKeurMin = iKeurMin;
  }
  
  public double getiRijpheid() { return iRijpheid; }
  
  public void setiRijpheid(double iRijpheid) {
    this.iRijpheid = iRijpheid;
  }
  
  public double getiRijpheidMax() { return iRijpheidMax; }
  
  public void setiRijpheidMax(double iRijpheidMax) {
    this.iRijpheidMax = iRijpheidMax;
  }
  
  public double getiRijpheidMin() { return iRijpheidMin; }
  
  public void setiRijpheidMin(double iRijpheidMin) {
    this.iRijpheidMin = iRijpheidMin;
  }
  
  public double getiCodering() { return iCodering; }
  
  public void setiCodering(double iCodering) {
    this.iCodering = iCodering;
  }
}