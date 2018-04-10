package eu.arzania.ban;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CheckCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("checkid")) {
			if (sender.hasPermission("ban.checkid")) {
				if (args.length == 0) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: §e/checkid <ID>");
				} else if (args.length == 1) {
					try {
						final Statement st = MySQL.getConnection().createStatement();
						final ResultSet rs = st.executeQuery(
								"SELECT * FROM ArzaniaBans WHERE BanID=" + Integer.parseInt(args[0]) + ";");
						String reason = "";
						String uuid = "";
						String name = "";
						String bannedBy = "";
						while (rs.next()) {
							reason = rs.getString("Reason");
							uuid = rs.getString("UUID");
							name = rs.getString("Name");
							bannedBy = rs.getString("BannedBy");
						}
						if (reason.equalsIgnoreCase("")) {
							try {
								final Statement st1 = MySQL.getConnection().createStatement();
								final ResultSet rs1 = st1.executeQuery(
										"SELECT * FROM ArzaniaMutes WHERE BanID=" + Integer.parseInt(args[0]) + ";");
								String reason1 = "";
								String uuid1 = "";
								String name1 = "";
								String bannedBy1 = "";
								while (rs1.next()) {
									reason1 = rs1.getString("Reason");
									uuid1 = rs1.getString("UUID");
									name1 = rs1.getString("Name");
									bannedBy1 = rs1.getString("BannedBy");

								}
								if(name.equals("")){
									sender.sendMessage(
											BanSystem.prefix + "Entweder existiert diese ID nicht oder es gab einen Fehler. (Not found)");
									return false;
								}
								sender.sendMessage(BanSystem.prefix+"§bMute");
								sender.sendMessage("§7Gemuteter Spieler: §e"+name1);
								sender.sendMessage("§7Grund: §e" + reason1);
								sender.sendMessage("§7Gemutet von: §e" + bannedBy1);
								sender.sendMessage("§7Gemutet bis: §a" + BanCmd.getLength(Utils.getMutelength(Integer.parseInt(args[0]))));
							} catch (Exception exx) {
								sender.sendMessage(
										BanSystem.prefix + "Entweder existiert diese ID nicht oder es gab einen Fehler. ("+exx.getMessage()+")");
							}
						} else {
							if(name.equals("")){
								sender.sendMessage(
										BanSystem.prefix + "Entweder existiert diese ID nicht oder es gab einen Fehler. (Not found)");
								return false;
							}
							sender.sendMessage(BanSystem.prefix+"§bBann");
							sender.sendMessage("§7Gebannter Spieler: §e"+name);
							sender.sendMessage("§7Grund: §e" + reason);
							sender.sendMessage("§7Gebannt von: §e" + bannedBy);
							sender.sendMessage("§7Gebannt bis: §a" + BanCmd.getLength(Utils.getBanlength(Integer.parseInt(args[0]))));
						}
					} catch (Exception e) {
						System.err.println(e.getMessage());
						try {
							final Statement st = MySQL.getConnection().createStatement();
							final ResultSet rs = st.executeQuery(
									"SELECT * FROM ArzaniaMutes WHERE BanID=" + Integer.parseInt(args[0]) + ";");
							String reason = "";
							String uuid = "";
							String name = "";
							String bannedBy = "";
							while (rs.next()) {
								reason = rs.getString("Reason");
								uuid = rs.getString("UUID");
								name = rs.getString("Name");
								bannedBy = rs.getString("BannedBy");

							}
							if(name.equals("")){
								sender.sendMessage(
										BanSystem.prefix + "Entweder existiert diese ID nicht oder es gab einen Fehler. (Not found)");
								return false;
							}
							sender.sendMessage(BanSystem.prefix+"§bMute");
							sender.sendMessage("§7Gemuteter Spieler: §e"+name);
							sender.sendMessage("§7Grund: §e" + reason);
							sender.sendMessage("§7Gemutet von: §e" + bannedBy);
							sender.sendMessage("§7Gemutet bis: §a" + BanCmd.getLength(Utils.getMutelength(Integer.parseInt(args[0]))));
						} catch (Exception exx) {
							sender.sendMessage(
									BanSystem.prefix + "Entweder existiert diese ID nicht oder es gab einen Fehler. ("+exx.getMessage()+")");
						}
					}
				}
			}
		}
		return false;
	}

}
