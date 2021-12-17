package top.xmdhs.banwithmcbbs;

import moe.feo.bbstoper.sql.SQLer;
import moe.feo.bbstoper.sql.SQLManager;
import org.bukkit.entity.Player;

public class Data {
    private final SQLer sqLer;

    public Data() {
        this.sqLer = SQLManager.sql;
    }

    public String getPlayerBBsName(Player p){
        return sqLer.getPoster(p.getUniqueId().toString()).getBbsname();
    }
}
