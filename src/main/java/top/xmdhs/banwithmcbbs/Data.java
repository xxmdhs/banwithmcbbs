package top.xmdhs.banwithmcbbs;

import moe.feo.bbstoper.Poster;
import moe.feo.bbstoper.sql.SQLManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Data {

    public Data() {
    }

    public String getPlayerBBsName(UUID u){
        Poster poster = SQLManager.sql.getPoster(u.toString());
        if (poster == null){
            return null;
        }
        return poster.getBbsname();
    }
}
