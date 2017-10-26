package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import entities.Station;
import utils.Utils;

public class Database {
	private static String url = "jdbc:mysql://10.50.137.150:3306/";
	private static String dbName = "jamafa";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "root";
	private static String password = "vagrant";

	public Database() {
	}

	/**
	 * Haal de database instantie op
	 * @return
	 */
	public Connection getDatabase() {
		Connection con = null;
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Haalt de gemiddelde waardes van vandaag op
	 * @param conn
	 * @param stnr
	 * @param result
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public ResultSet getGemWaardes(Connection conn, int stnr, Object result)
			throws ParseException, SQLException {
		ResultSet rs = null;
		Statement st = null;
		String dateInString = (String) result;
		st = conn.createStatement();
		rs = st.executeQuery("select * from jamafagemiddelde where datum = '"
				+ dateInString + "' AND StatationNr = " + stnr);

		return rs;
	}

	/**
	 * Haalt de gemiddelde procenten van vandaag op
	 * @param conn
	 * @param stnr
	 * @param result
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public ResultSet getGemWaardesp(Connection conn, int stnr, Object result)
			throws ParseException, SQLException {
		ResultSet rs = null;
		Statement st = null;
		String dateInString = (String) result;
		st = conn.createStatement();
		rs = st.executeQuery("select * from jamafagemiddeldep where datum = '"
				+ dateInString + "' AND station = " + stnr);

		return rs;
	}

	/**
	 * Controleerd of het station in gebruik is
	 * @param stationNr
	 * @param conn
	 * @return
	 */
	public boolean isActive(int stationNr, Connection conn) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		boolean active = true;
		int temp = 0;
		try {
			Statement st = conn.createStatement();

			rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR) and Stationnummer = "
					+

					station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
			if (temp == 0) {
				active = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return active;
	}

	/**
	 * Haal het ID van het huidige programma op
	 * @param conn
	 * @return
	 */
	public ResultSet getHuidigProgramma(Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT * from config");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Halle alle informatie van een station op
	 * @param stationNr
	 * @param conn
	 * @return
	 */
	public ResultSet getStationInfo(int stationNr, Connection conn) {
		ResultSet rs = null;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT * from jamafaStations where stationnr="
					+ stationNr);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * Reken gemiddelde uit voor bepaalde types.
	 * @param stationNr
	 * @param cName
	 * @param conn
	 * @return
	 */
	public double countAverage(int stationNr, String cName, Connection conn) {
		ResultSet rs = null;
		double temp = 0.0D;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT AVG(" + cName
					+ ") as avg from jamafa where Stationnummer=" + stationNr);
			rs.next();
			temp = rs.getDouble("avg");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return temp;
	}

	/**
	 * Controleert of de codering niet null is.
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public boolean ifConderingisNull(int stationNr, Connection conn, boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		boolean b = false;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("select IngesteldeCodering as codering from jamafaStations where stationnr = "
					+ station.getStationNummer());

			rs.next();
			temp = rs.getInt("codering");

			if (temp == 0) {
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * Tel aantal rozen totaal
	 * @param conn
	 * @param ie
	 * @return
	 */
	public int TelRozen(Connection conn, boolean afgelopenUur) {
		Statement st = null;
		ResultSet rs = null;
		int temp = 0;
		try {
			st = conn.createStatement();
			if (afgelopenUur) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)");
			} else {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa");
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * Tel aantal rozen in station
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int TelAantalInStation(int stationNr, Connection conn, boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		try {
			Statement st = conn.createStatement();
			if (uur) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR) and Stationnummer = "
						+

						station.getStationNummer());
			} else {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where Stationnummer = "
						+ station.getStationNummer());
			}

			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}


	/**
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdKnopBreedte(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}

			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);

			rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnopbreedte < "
					+ station.getiKnopBreede()
					+ " and GemetenLengte > "
					+ station.getiLengte()
					+ " and GemetenCoderingBoven= '"
					+ coderingBoven
					+ "' "
					+ " and GemetenCoderingOnder = '"
					+ coderingOnder
					+ "'"
					+ hourInterval
					+ " and Stationnummer > " + station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdKnopHoogte(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnophoogte < "
							+ station.getiKnopHoogte()
							+ " and GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '"+ coderingBoven +"' and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdOnderKnopMin(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			
				
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and "
							+ "GemetenKleurwaarde < "
							+ station.getiKeurMin()
							+ " and "
							+ "GemetenCoderingBoven= '"+coderingBoven+"'"
							+ " and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
			
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdBovenKnopMax(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenKleurwaarde > "
							+ station.getiKleurMax()
							+ " and GemetenCoderingBoven= '"+coderingBoven+"'"
							+ " and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdOnderRijpMin(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			

					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and "
							+ "GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ " and "
							+ "GemetenCoderingBoven= '"+coderingBoven+"'"
							+ " and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
	
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdBovenRijpMax(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		String hourInterval = "";
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ " and GemetenCoderingBoven= '"+coderingBoven+"'"
							+ " and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int DoorgestuurdSteelDikte(int stationNr, Connection conn,
			boolean uur) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		double codering = 0.0D;
		String hourInterval = "";
		int temp = 0;
		try {
			Statement st = conn.createStatement();
			if (uur) {
				hourInterval = " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)";
			}
			
			codering = station.getiCodering();
			int coderingOnder = Utils.getCoderingOnder(codering);
			int coderingBoven = Utils.getCoderingBoven(codering);
			
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenSteeldikte < "
							+ station.getiSteeldikte()
							+ " and GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '"+coderingBoven+"'"
							+ " and GemetenCoderingOnder = '"+coderingOnder+"'"
							+ hourInterval
							+ " and Stationnummer > "
							+ station.getStationNummer());
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * Ik snap echt niet waarom ik deze queries zo opbouw.. lang geleden.
	 * Ga het ook niet meer aanpassen - het werkt.
	 * @param stationNr
	 * @param conn
	 * @param uur
	 * @param codering
	 * @return
	 */
	public int TotalDoorgestuurd(int stationNr, Connection conn, boolean uur,
			double codering) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		try {
			Statement st = conn.createStatement();
			if (uur) {
				if (codering == 0.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '0'"
							+ " and GemetenCoderingOnder = '0'"
							+ " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 1.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '1'"
							+ " and GemetenCoderingOnder = '0'"
							+ " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 2.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '0'"
							+ " and GemetenCoderingOnder = '1'"
							+ " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 3.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '1'"
							+ " and GemetenCoderingOnder = '1'"
							+ " and InstertTime > SUBDATE( CURRENT_TIMESTAMP, INTERVAL 1 HOUR)"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
			} else {
				if (codering == 0.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '0'"
							+ " and GemetenCoderingOnder = '0'"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 1.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '1'"
							+ " and GemetenCoderingOnder = '0'"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 2.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '0'"
							+ " and GemetenCoderingOnder = '1'"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
				if (codering == 3.0D) {
					rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
							+ station.getiLengte()
							+ " and GemetenCoderingBoven= '1'"
							+ " and GemetenCoderingOnder = '1'"
							+ " and (GemetenKnophoogte <"
							+ station.getiKnopHoogte()
							+ "OR GemetenKnopbreedte <"
							+ station.getiKnopBreede()
							+ "OR GemetenSteelDikte<"
							+ station.getiSteeldikte()
							+ "OR GemetenRijpheid > "
							+ station.getiRijpheidMax()
							+ "OR GemetenRijpheid < "
							+ station.getiRijpheidMin()
							+ "OR GemetenKleurWaarde < "
							+ station.getiKleurMax()
							+ "OR GemetenKleurWaarde > "
							+ station.getiKeurMin()
							+ ")"
							+ " and Stationnummer > "
							+ station.getStationNummer());
				}
			}

			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param conn
	 * @param uur
	 * @return
	 */
	public int countEntries(Connection conn, boolean uur) {
		ResultSet rs = null;
		int temp = 0;
		try {
			Statement st = conn.createStatement();
			if (uur) {
				Date dNow = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(dNow);
				cal.add(10, -1);
				Date onehourback = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datetime = sdf.format(onehourback);

				System.out.println(datetime);
				rs = st.executeQuery("SELECT COUNT(*) AS rowcount FROM jamafa WHERE InstertTime > "
						+ datetime);
			} else {
				rs = st.executeQuery("SELECT COUNT(*) AS rowcount FROM jamafa");
			}
			rs.next();
			temp = rs.getInt("rowcount");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return temp;
	}

	/**
	 * 
	 * @param conn
	 * @return
	 */
	public int getAmountStations(Connection conn) {
		int temp = 9;

		return temp;
	}

	/**
	 * 
	 * @param con
	 */
	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdSteelDikteExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		double codering = 0.0D;
		int temp = 0;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiSteeldikte();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenSteeldikte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenSteeldikte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenSteeldikte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenSteeldikte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdKnopHoogteExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiKnopHoogte();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnophoogte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '0' and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnophoogte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '1' and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnophoogte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '0' and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnophoogte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '1' and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}

			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdKnopBreedteExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiKnopBreede();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnopbreedte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingBoven= '0' "
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnopbreedte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingOnder = '0'"
						+ " and GemetenCoderingBoven= '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnopbreedte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingOnder = '1'"
						+ " and GemetenCoderingBoven= '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenKnopbreedte < "
						+ sCodering2
						+ " and GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenCoderingOnder = '1'"
						+ " and GemetenCoderingBoven= '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdOnderKnopMinExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiKeurMin();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdBovenKnopMaxExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiKleurMax();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenKleurwaarde > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}

			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdOnderRijpMinExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiRijpheidMin();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid < "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @param stationNr
	 * @param conn
	 * @param extrawaarde
	 * @return
	 */
	public int DoorgestuurdBovenRijpMaxExtra(int stationNr, Connection conn,
			int extrawaarde) {
		Station station = new Station(stationNr);

		ResultSet rs = null;
		int temp = 0;
		double codering = 0.0D;
		try {
			Statement st = conn.createStatement();
			codering = station.getiCodering();
			double sCodering = station.getiRijpheidMax();
			double sCodering2 = sCodering + extrawaarde;
			if (codering == 0.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 1.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '0'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 2.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '0'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			if (codering == 3.0D) {
				rs = st.executeQuery("select count(StationNummer) as aantal from jamafa where GemetenLengte > "
						+ station.getiLengte()
						+ " and GemetenRijpheid > "
						+ sCodering2
						+ " and GemetenCoderingBoven= '1'"
						+ " and GemetenCoderingOnder = '1'"
						+ " and Stationnummer > " + station.getStationNummer());
			}
			rs.next();
			temp = rs.getInt("aantal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
}