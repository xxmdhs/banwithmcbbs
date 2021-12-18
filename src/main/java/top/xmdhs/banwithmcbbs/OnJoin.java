package top.xmdhs.banwithmcbbs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class OnJoin implements Listener {
    private final Data d;
    private final Mcbbs m;
    private final ConcurrentHashMap<String, PlayerInfo> map = new ConcurrentHashMap<>();
    private final Plugin p;

    public OnJoin(Data d, Mcbbs m, Plugin p) {
        this.d = d;
        this.m = m;
        this.p = p;
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e){
        String s = d.getPlayerBBsName(e.getUniqueId());
        if (s == null) {
            return;
        }
        PlayerInfo pi = map.get(s);
        if (pi != null) {
            long nowTime = new Date().getTime();
            if (nowTime - pi.time < 1200000 && pi.isBaned) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,"已被 mcbbs 封禁");
            }
        }
    }


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(p, () -> {
            String s = d.getPlayerBBsName(e.getPlayer().getUniqueId());
            if (s == null) {
                return;
            }
            boolean b;
            try {
                b = m.isBaned(s);
            } catch (IOException ioException) {
                p.getLogger().warning("网络错误");
                ioException.printStackTrace();
                return;
            }
            PlayerInfo p = new PlayerInfo();
            p.isBaned = b;
            p.time = new Date().getTime();
            map.put(s, p);
            if (b) {
                kickPlayer(e.getPlayer());
            }
        });
    }

    private void kickPlayer(Player player) {
        Bukkit.getScheduler().runTask(p, () -> {
            player.kickPlayer("已被 mcbbs 封禁");
        });
    }
}

class PlayerInfo {
    boolean isBaned;
    long time;
}