package top.xmdhs.banwithmcbbs;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class OnJoin implements Listener {
    private final Data d;
    private final Mcbbs m;
    private final HashMap<String, PlayerInfo> map = new HashMap<>();
    private final Plugin p;

    public OnJoin(Data d, Mcbbs m, Plugin p) {
        this.d = d;
        this.m = m;
        this.p = p;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        PlayerInfo pi = map.get(e.getPlayer().getUniqueId().toString());
        if (pi != null) {
            long nowTime = new Date().getTime();
            if (nowTime - pi.time > 1200000 && pi.isBaned) {
                e.getPlayer().kickPlayer("已被 mcbbs 封禁");
                return;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(p, () -> {
            String s = d.getPlayerBBsName(e.getPlayer());
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
            Bukkit.getScheduler().runTask(p, () -> {
                PlayerInfo p = new PlayerInfo();
                p.isBaned = b;
                p.time = new Date().getTime();
                map.put(e.getPlayer().getUniqueId().toString(), p);
                if (b) {
                    e.getPlayer().kickPlayer("已被 mcbbs 封禁");
                }
            });
        });
    }
}

class PlayerInfo {
    boolean isBaned;
    long time;
}