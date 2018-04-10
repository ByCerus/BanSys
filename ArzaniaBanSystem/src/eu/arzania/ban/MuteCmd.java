package eu.arzania.ban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mute")) {
			if (sender.hasPermission("ban.mute")) {
				if (args.length == 0) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: �e/mute <SpielerName> <L�nge> <Grund>");
				} else if (args.length == 1) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: �e/mute " + args[0] + " <L�nge> <Grund>");
				} else if (args.length == 2) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: �e/mute " + args[0] + " " + args[1] + " <Grund>");
				} else if (args.length >= 3) {
					String timeString = args[1].toLowerCase();
					long time = 0;
					long millis = System.currentTimeMillis();
					if (args[1].endsWith("sec")) {
						if (!sender.hasPermission("ban.laenge.jrsup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}

						if (Integer.parseInt(args[1].replace("sec", "")) > 604800) {
							if (!sender.hasPermission("ban.laenge.jrsup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						if (Integer.parseInt(args[1].replace("sec", "")) > 2419200) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						timeString = args[1].replace("sec", "");
						time = Integer.parseInt(timeString) * 1000 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("min")) {
						if (!sender.hasPermission("ban.laenge.jrsup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene Ban-L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}

						if (Integer.parseInt(args[1].replace("min", "")) > 10080) {
							if (!sender.hasPermission("ban.laenge.jrsup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						if (Integer.parseInt(args[1].replace("min", "")) > 40320) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						timeString = args[1].replace("min", "");
						time = Integer.parseInt(timeString) * 1000 * 60 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("hour")) {
						if (!sender.hasPermission("ban.laenge.jrsup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}

						if (Integer.parseInt(args[1].replace("hour", "")) > 168) {
							if (!sender.hasPermission("ban.laenge.jrsup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						if (Integer.parseInt(args[1].replace("hour", "")) > 672) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						timeString = args[1].replace("hour", "");
						time = Integer.parseInt(timeString) * 1000 * 60 * 60 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("day")) {
						if (!sender.hasPermission("ban.laenge.jrsup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}
						if (Integer.parseInt(args[1].replace("day", "")) > 7) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						if (Integer.parseInt(args[1].replace("day", "")) > 30) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						timeString = args[1].replace("day", "");
						time = Integer.parseInt(timeString) * 1000 * 60 * 60 * 24 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("week")) {
						if (!sender.hasPermission("ban.laenge.jrsup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}
						if (Integer.parseInt(args[1].replace("week", "")) > 1) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						if (Integer.parseInt(args[1].replace("week", "")) > 4) {
							if (!sender.hasPermission("ban.laenge.sup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}

						timeString = args[1].replace("week", "");
						time = Integer.parseInt(timeString) * 1000 * 60 * 60 * 24 * 7 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("month")) {
						if (!sender.hasPermission("ban.laenge.sup")) {
							sender.sendMessage(BanSystem.prefix
									+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;
						}
						if (Integer.parseInt(args[1].replace("month", "")) > 1) {
							if (!sender.hasPermission("ban.laenge.srsup")) {
								sender.sendMessage(BanSystem.prefix
										+ "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
								return false;
							}
						}
						timeString = args[1].replace("month", "");
						time = Integer.parseInt(timeString) * 1000 * 60 * 60 * 24 * 7 * 4 + millis;

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �7bis zum �c" + BanCmd.getLength(time) + " �7gemutet. Grund: �e" + reason
									+ " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, time, sender.getName(), banned.getName());
					} else if (args[1].toLowerCase().endsWith("perma")) {
						if (!sender.hasPermission("ban.laenge.srsup")) {
							sender.sendMessage(BanSystem.prefix + "Die eingegebene L�nge ist ung�ltig, du hast nicht die erforderlichen Rechte.");
							return false;

						}

						String reason = "";

						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i];
						}
						reason = reason.toUpperCase();

						Player banned = Bukkit.getPlayer(args[0]);
						
						if(banned.hasPermission("ban.ignore")){
							sender.sendMessage(BanSystem.prefix+"Dieser Spieler kann nicht gemutet werden.");
							return false;
						}
						
						if (banned != null) {
							banned.sendMessage(BanSystem.prefix + "Du wurdest von �e" + sender.getName()
									+ " �cPermanent �7gemutet. Grund: �e" + reason + " �7MuteID: �cGenerating...");
						}
						Utils.addMute(UUIDFetcher.getUUID(args[0]), reason, -1, sender.getName(), banned.getName());
					} else
						sender.sendMessage(
								BanSystem.prefix + "Verf�gbare Zeitangaben: Min, Hour, Day, Week, Month, PERMA");
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("unmute")) {
			if (sender.hasPermission("bansys.mute")) {
				if (args.length == 0) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: �e/unmute <SpielerName>");
				} else if (args.length == 1) {
					try {
						if (Utils.isMuted(UUIDFetcher.getUUID(args[0]))) {
							Utils.unmute(args[0]);
							sender.sendMessage(BanSystem.prefix + "Spieler " + args[0] + " erfolgreich entmutet.");
						}
					} catch (Exception e) {
						sender.sendMessage(BanSystem.prefix
								+ "Entweder ist dieser Spieler nicht gemutet oder es gab einen Fehler.");
					}
				}
			}
		}
		return false;
	}

}
