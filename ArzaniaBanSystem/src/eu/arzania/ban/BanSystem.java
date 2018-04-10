package eu.arzania.ban;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BanSystem extends JavaPlugin {

	public HashMap<String, Integer> BANS = new HashMap<>();
	boolean isPluginEnabled = true;
	boolean isMySQLConnected = false;
	private static BanSystem instance;
	public static String prefix = "§c§lArzaniaBans §8» §7";
	int cons = 1;

	public void onEnable() {
		loadMySQLDaten();
		if (isPluginEnabled) {
			instance = this;

			if (MySQL.connect()) {
				isMySQLConnected = true;
				MySQL.update("CREATE TABLE IF NOT EXISTS " + "ArzaniaBans"
						+ "(UUID varchar(64), BanLength bigint, BannedBy varchar(64), Reason varchar(64), BanID int, Name varchar(64));");
				MySQL.update("CREATE TABLE IF NOT EXISTS " + "ArzaniaMutes"
						+ "(UUID varchar(64), BanLength bigint, BannedBy varchar(64), Reason varchar(64), BanID int, Name varchar(64));");
				MySQL.update("CREATE TABLE IF NOT EXISTS " + "ArzaniaBansID" + "(Bans varchar(64), ID int);");
				if (cons == 0) {
					MySQL.update("INSERT INTO ArzaniaBansID (Bans, ID) VALUES ('BanID', 0);");
				}
				loadBans();

				getCommand("ban").setExecutor(new BanCmd());
				getCommand("checkid").setExecutor(new CheckCmd());
				getCommand("unban").setExecutor(new BanCmd());
				getCommand("kick").setExecutor(new KickCmd());
				getCommand("mute").setExecutor(new MuteCmd());
				getCommand("unmute").setExecutor(new MuteCmd());
				Bukkit.getPluginManager().registerEvents(new LoginEvent(), this);
			} else {
				System.err.println("Stoppe BanSystem...");
				Bukkit.getPluginManager().disablePlugin(this);
				isPluginEnabled = false;
				return;
			}
		}
	}

	private void loadBans() {

	}

	public static BanSystem getInstance() {
		return instance;
	}

	public void loadMySQLDaten() {
		MySQL.file = new File("plugins//BanSys//MySQL.yml");
		MySQL.cfg = YamlConfiguration.loadConfiguration(MySQL.file);

		if (!MySQL.cfg.contains("MySQL")) {
			MySQL.cfg.set("MySQL.Cons", 0);
			MySQL.cfg.set("MySQL.Host", "localhost");
			MySQL.cfg.set("MySQL.User", "root");
			MySQL.cfg.set("MySQL.Password", "Password");
			MySQL.cfg.set("MySQL.Database", "Database");
			try {
				MySQL.cfg.save(MySQL.file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println(
					"[BanSystem : MySQL] Die MySQL Daten wurden noch nicht eingetragen! Bitte ändere die Daten auf deine Zugangsdaten.");
			System.err.println("Stoppe BanSystem...");
			Bukkit.getPluginManager().disablePlugin(this);
			isPluginEnabled = false;
			return;
		}
		cons = MySQL.cfg.getInt("MySQL.cons");
		int i = cons;
		i++;
		MySQL.cfg.set("MySQL.Cons", i);
		try {
			MySQL.cfg.save(MySQL.file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MySQL.DATABASE = MySQL.cfg.getString("MySQL.Database");
		MySQL.HOST = MySQL.cfg.getString("MySQL.Host");
		MySQL.PASSWORD = MySQL.cfg.getString("MySQL.Password");
		MySQL.USER = MySQL.cfg.getString("MySQL.User");
	}

}
