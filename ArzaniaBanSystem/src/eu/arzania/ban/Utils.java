package eu.arzania.ban;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class Utils {

	public static boolean isBanned(UUID uuid) {
		// boolean isBanned = false;
		// try {
		// final Statement st = MySQL.getConnection().createStatement();
		// final ResultSet rs = st.executeQuery("SELECT * FROM `ArzaniaBans`
		// WHERE UUID='" + uuid.toString() + "'");
		// if (rs.next()) {
		// long l = getBanlength(getBanID(uuid));
		//
		// isBanned = System.currentTimeMillis() >
		// getBanlength(Integer.valueOf(getState(uuid, "BanID")));
		//
		// }
		// } catch (SQLException ex) {
		// System.err.println("ERROR : SQL EXCEPTION : "+ex.getMessage());
		// }
		// System.out.println(System.currentTimeMillis() <
		// getBanlength(Integer.valueOf(getState(uuid, "BanID"))));
		// System.out.println(" / Lenth:
		// "+getBanlength(Integer.valueOf(getState(uuid, "BanID")))+" Curr:
		// "+System.currentTimeMillis());
		if(getBanlength(Integer.valueOf(getState(uuid, "BanID"))) == -1){
			return true;
		} else return System.currentTimeMillis() < getBanlength(Integer.valueOf(getState(uuid, "BanID")));
	}
	
	public static boolean isMuted(UUID uuid) {
		if(getMutelength(Integer.valueOf(getMuteState(uuid, "BanID"))) == -1){
			return true;
		} else return System.currentTimeMillis() < getMutelength(Integer.valueOf(getMuteState(uuid, "BanID")));
	}

	public static void addBan(UUID uuid, String reason, long lengthInMinutes, String bannedByName, String name) {
		int banID = generateBanID();
		if (lengthInMinutes > -1) {
			long length = lengthInMinutes;

			try {
				final Statement st = MySQL.getConnection().createStatement();
				st.executeUpdate("INSERT INTO ArzaniaBans (UUID, BanLength, BannedBy, Reason, BanID, Name) VALUES ('"
						+ uuid.toString().replace("-", "") + "', " + length + ", '" + bannedByName + "', '" + reason
						+ "', " + banID + ", '"+name+"');");
				//st.executeUpdate("UPDATE `ArzaniaBansID` SET `ID`='" + banID + "' WHERE Bans='BanID'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {

			try {
				final Statement st = MySQL.getConnection().createStatement();
				st.executeUpdate("INSERT INTO ArzaniaBans (UUID, BanLength, BannedBy, Reason, BanID, Name) VALUES ('"
						+ uuid.toString().replace("-", "") + "', " + Long.valueOf(-1) + ", '" + bannedByName + "', '" + reason
						+ "', " + banID + ", '"+name+"');");
				//st.executeUpdate("UPDATE `ArzaniaBansID` SET `ID`='" + banID + "' WHERE Bans='BanID'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//TYPE VonWem? Wer? Weshalb? BanID? WannEndetDieStrafe?
		//BANN bannedBy name reason banid BanCmd.getLength() Oder so
		String request = "BANN "+bannedByName+" "+name+" "+reason+" "+banID+" "+BanCmd.getLength(lengthInMinutes);
		PacketCustom packet = new PacketCustom("BanSysChannel", (Object) request);
		String answer = (String) packet.send();
		System.out.println("BUNGEE MESSAGE RECEIVED: "+answer);

	}
	
	public static void unban(String name){
		MySQL.update("DELETE FROM ArzaniaBans WHERE UUID='"+UUIDFetcher.getUUID(name).toString().replace("-", "")+"'");
	}
	
	public static void unmute(String name){
		MySQL.update("DELETE FROM ArzaniaMutes WHERE UUID='"+UUIDFetcher.getUUID(name).toString().replace("-", "")+"'");
	}
	
	public static void addMute(UUID uuid, String reason, long lengthInMinutes, String bannedByName, String name) {
		int banID = generateBanID();
		if (lengthInMinutes > -1) {
			long length = lengthInMinutes;

			try {
				final Statement st = MySQL.getConnection().createStatement();
				st.executeUpdate("INSERT INTO ArzaniaMutes (UUID, BanLength, BannedBy, Reason, BanID, Name) VALUES ('"
						+ uuid.toString().replace("-", "") + "', " + length + ", '" + bannedByName + "', '" + reason
						+ "', " + banID + ", '"+name+"');");
				//st.executeUpdate("UPDATE `ArzaniaBansID` SET `ID`='" + banID + "' WHERE Bans='BanID'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {

			try {
				final Statement st = MySQL.getConnection().createStatement();
				st.executeUpdate("INSERT INTO ArzaniaMutes (UUID, BanLength, BannedBy, Reason, BanID) VALUES ('"
						+ uuid.toString().replace("-", "") + "', " + Long.valueOf(-1) + ", '" + bannedByName + "', '" + reason
						+ "', " + banID + ", '"+name+"');");
				//st.executeUpdate("UPDATE `ArzaniaBansID` SET `ID`='" + banID + "' WHERE Bans='BanID'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//TYPE VonWem? Wer? Weshalb? BanID? WannEndetDieStrafe?
		//BANN bannedBy name reason banid BanCmd.getLength() Oder so
		String request = "MUTE "+bannedByName+" "+name+" "+reason+" "+banID+" "+BanCmd.getLength(lengthInMinutes);
		PacketCustom packet = new PacketCustom("BanSysChannel", (Object) request);
		String answer = (String) packet.send();
		System.out.println("BUNGEE MESSAGE RECEIVED: "+answer);

	}
	
	public static long getMutelength(final int banId) {
		long l = 0;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st.executeQuery("SELECT * FROM ArzaniaMutes WHERE BanID=" + banId + ";");
			while (rs.next()) {
				l = rs.getLong("BanLength");
			}
		} catch (SQLException ex) {
			System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return l;
	}

	public static long getBanlength(final int banId) {
		long l = 0;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st.executeQuery("SELECT * FROM ArzaniaBans WHERE BanID=" + banId + ";");
			while (rs.next()) {
				l = rs.getLong("BanLength");
			}
		} catch (SQLException ex) {
			System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return l;
	}

	public static int getBanID(final UUID uuid) {
		int l = 0;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st
					.executeQuery("SELECT * FROM ArzaniaBans WHERE UUID='" + uuid.toString().replace("-", "") + "';");
			while (rs.next()) {
				l = rs.getInt("BanID");
			}
		} catch (SQLException ex) {
			System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return l;
	}

	public static int getMuteID(final UUID uuid) {
		int l = 0;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st
					.executeQuery("SELECT * FROM ArzaniaMutes WHERE UUID='" + uuid.toString().replace("-", "") + "';");
			while (rs.next()) {
				l = rs.getInt("BanID");
			}
		} catch (SQLException ex) {
			System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return l;
	}
	
	public static String getState(final UUID uuid, final String state) {
		Object o = null;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st
					.executeQuery("SELECT * FROM ArzaniaBans WHERE UUID='" + uuid.toString().replace("-", "") + "';");
			if (rs.next()) {
				o = rs.getObject(state);
			}
		} catch (SQLException ex) {
			System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return String.valueOf(o);
	}
	
	public static String getMuteState(final UUID uuid, final String state) {
		Object o = null;
		try {
			final Statement st = MySQL.getConnection().createStatement();
			final ResultSet rs = st
					.executeQuery("SELECT * FROM ArzaniaMutes WHERE UUID='" + uuid.toString().replace("-", "") + "';");
			if (rs.next()) {
				o = rs.getObject(state);
			}
		} catch (SQLException ex) {
			//System.err.println("ERROR : SQL EXCEPTION : " + ex.getMessage());
		}
		return String.valueOf(o);
	}

//	@Deprecated
//	public static int getCurrentBanID() {
//		int i = 0;
//		try {
//			final Statement st = MySQL.getConnection().createStatement();
//			final ResultSet rs = st.executeQuery("SELECT * FROM `ArzaniaBansID` WHERE Bans='BanID'");
//			if (rs.next()) {
//				i = Math.addExact(i, rs.getInt("ID"));
//			}
//		} catch (SQLException ex) {
//		}
//		return i;
//	}

	public static int generateBanID(){
		StringBuilder builder = new StringBuilder();
		String s = "1234567890";
		for (int i = 0; i < 8; i++) {
			double index = Math.random() * s.length();
			builder.append(s.charAt((int) index));
		}
		String key = builder.toString();
		int returnment = Integer.parseInt(key);
		return returnment;
	}
	
}
