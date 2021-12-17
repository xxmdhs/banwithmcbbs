package top.xmdhs.banwithmcbbs;

import moe.feo.bbstoper.Poster;
import moe.feo.bbstoper.sql.SQLManager;
import org.bukkit.entity.Player;

public class Data {

    public Data() {
    }

    public String getPlayerBBsName(Player p){
        Poster poster = SQLManager.sql.getPoster(p.getUniqueId().toString());
        if (poster == null){
            return null;
        }
        return poster.getBbsname();
    }
}
