package eu.arzania.ban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kick")) {
			if (sender.hasPermission("ban.kick")) {
				if (args.length == 0) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: §e/kick <SpielerName> <Grund>");
				} else if (args.length == 1) {
					sender.sendMessage(BanSystem.prefix + "Nutzung: §e/kick " + args[0] + " <Grund>");
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						target.kickPlayer("§2§lA§a§lrzania\n§0\n§7Du wurdest vom Netzwerk gekickt.\n§7Grund: §e"
								+ args[1] + "\n$7Gekickt von: §e" + sender.getName());
					} else
						sender.sendMessage(BanSystem.prefix + "Dieser Spieler ist nicht Online!");
				}

			}
		}
		return false;
	}

}
