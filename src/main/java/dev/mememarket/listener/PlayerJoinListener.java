package dev.mememarket.listener;

import dev.mememarket.service.MarketService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {
    private final MarketService market;

    public PlayerJoinListener(MarketService market) { this.market = market; }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§6Minecraft Meme Market §7— §f" + market.companies().size() + " companies listed.");
        event.getPlayer().sendMessage("§7Use §f/market §7to open the exchange.");
    }
}
