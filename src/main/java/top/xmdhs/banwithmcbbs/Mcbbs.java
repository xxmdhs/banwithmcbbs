package top.xmdhs.banwithmcbbs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Mcbbs {

    public Mcbbs() {
    }


    public boolean isBaned(String bbsName) throws IOException {
        String s = httpGet("https://www.mcbbs.net/api/mobile/index.php?version=4&module=profile&username=" + URLEncoder.encode(bbsName,"UTF-8"));
        Gson gson = new Gson();
        uidapi u = gson.fromJson(s, uidapi.class);
        int id = u.Variables.space.groupid;
        return id == 4 || id == 5;
    }

    private String httpGet(String u) throws IOException {
        URL url = new URL(u);
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");

        int code = connection.getResponseCode();
        if (code != 200) {
            throw new IOException("not 200");
        }
        StringBuilder line = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                line.append(inputLine);
            }
        }
        return line.toString();
    }
}


class uidapi {
    public variables Variables;

    static class variables {
        public Space space;

        static class Space {
            public int uid;
            public String username;
            public int credits;
            public int extcredits1;
            public int extcredits2;
            public int extcredits3;
            public int extcredits4;
            public int extcredits5;
            public int extcredits6;
            public int extcredits7;
            public int extcredits8;
            public int oltime;
            public int groupid;
            public int posts;
            public String lastvisit;
            public int digestposts;
            public int threads;
            public Group group;

            static class Group {
                public String grouptitle;
            }

            public int friends;
            public int views;
            public int emailstatus;
            public int adminid;
            public String extgroupids;
        }
    }
}

