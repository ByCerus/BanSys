package eu.arzania.ban;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginEvent implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		long m = System.currentTimeMillis() / 1000;
		System.out.println("cMillis: " + System.currentTimeMillis());
		System.out.println("cMillisInSeconds: " + System.currentTimeMillis() / 1000);
		System.out.println("cMillisInHours: " + m / 3600);
		try {
			System.out.println("banLenght: " + Utils.getBanlength(
					Integer.parseInt(Utils.getState(UUIDFetcher.getUUID(e.getPlayer().getName()), "BanID"))));

			if (Utils.isBanned(UUIDFetcher.getUUID(e.getPlayer().getName()))) {
				String bannedBy = Utils.getState(UUIDFetcher.getUUID(e.getPlayer().getName()), "BannedBy");
				long length = Utils.getBanlength(
						Integer.parseInt(Utils.getState(UUIDFetcher.getUUID(e.getPlayer().getName()), "BanID")));
				String lengthString = BanCmd.getLength(length);
				String reason = Utils.getState(UUIDFetcher.getUUID(e.getPlayer().getName()), "Reason");
				int banID = Utils.getBanID(UUIDFetcher.getUUID(e.getPlayer().getName()));

				e.disallow(Result.KICK_OTHER, "§2§lA§a§lrzania\n§0\n§7Du bist bis zum §c" + lengthString
						+ " §7vom Netzerk ausgeschlossen.\n§0\n§7Gebannt von: §e" + bannedBy + "\n§7Grund: §e" + reason
						+ "\n§7Ban ID: §b" + banID
						+ "\n§0\n§7Du kannst im §3Forum §7einen Entbannungsantrag stellen.\n§ahttp://arzania.eu/go/ea/");
			} else {
				System.out.println("NICHT GEBANNT!!!");
				MySQL.update("DELETE FROM ArzaniaBans WHERE UUID='"
						+ UUIDFetcher.getUUID(e.getPlayer().getName()).toString().replace("-", "") + "'");
			}
		} catch (Exception e2) {
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		try {
			if (Utils.isMuted(UUIDFetcher.getUUID(e.getPlayer().getName()))) {
				e.setCancelled(true);

				String bannedBy = Utils.getMuteState(UUIDFetcher.getUUID(e.getPlayer().getName()), "BannedBy");
				long length = Utils.getMutelength(
						Integer.parseInt(Utils.getMuteState(UUIDFetcher.getUUID(e.getPlayer().getName()), "BanID")));
				String lengthString = BanCmd.getLength(length);
				String reason = Utils.getMuteState(UUIDFetcher.getUUID(e.getPlayer().getName()), "Reason");
				int banID = Utils.getMuteID(UUIDFetcher.getUUID(e.getPlayer().getName()));

				if (length > -1) {
					e.getPlayer().sendMessage(BanSystem.prefix + "Du wurdest von §e" + bannedBy + " §7bis zum §c"
							+ lengthString + " §7gemutet. Grund: §e" + reason + " §7MuteID: §b" + banID);
				} else
					e.getPlayer().sendMessage(BanSystem.prefix + "Du wurdest von §e" + bannedBy
							+ " §cPermanent §7gemutet. Grund: §e" + reason + " §7MuteID: §b" + banID);
			} else {
				MySQL.update("DELETE FROM ArzaniaMutes WHERE UUID='"
						+ UUIDFetcher.getUUID(e.getPlayer().getName()).toString().replace("-", "") + "'");
			}
		} catch (Exception e2) {
		}
	}

}
