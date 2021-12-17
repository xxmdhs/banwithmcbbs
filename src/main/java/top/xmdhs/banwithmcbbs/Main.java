package top.xmdhs.banwithmcbbs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Mcbbs m = new Mcbbs();
        Data d = new Data();
        OnJoin j = new OnJoin(d, m, this);
        Bukkit.getPluginManager().registerEvents(j, this);
    }
}
