package eu.arzania.ban;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {

	public static String HOST = "";
	public static String DATABASE = "";
	public static String USER = "";
	public static String PASSWORD = "";

	public static File file;
	public static FileConfiguration cfg;
	
	private static Connection con;

	public static Connection getConnection() {
        return MySQL.con;
    }
	
	public static boolean connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true",
					USER, PASSWORD);
			System.out.println("[BanSystem : MySQL] Die Verbindung zur MySQL wurde hergestellt!");
			return true;
		} catch (SQLException e) {
			System.out.println("[BanSystem : MySQL] Die Verbindung zur MySQL ist fehlgeschlagen! "+e.getMessage());
			return false;
		}
	}

	public static void close() {
		try {
			if (con != null) {
				con.close();
				System.out.println("[BanSystem : MySQL] Die Verbindung zur MySQL wurde Erfolgreich beendet!");
			}
		} catch (SQLException e) {
			System.out.println("[BanSystem : MySQL] Fehler beim beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
		}
	}

	public static void update(String qry) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
	}

	public static ResultSet query(String qry) {
		ResultSet rs = null;

		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}
}
