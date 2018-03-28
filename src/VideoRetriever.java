import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class VideoRetriever {
    public static ArrayList<Video> getVideos(String keyword) {
        ArrayList<Video> videos = new ArrayList<>();
        try {
            String apiKey = "AIzaSyA2rJ3kuns7kM0B-qLeGl6-BLpM_9whQr0";
            int maxResults = 50;
            String url = "https://www.googleapis.com/youtube/v3/search" +
                    "?part=id%2Csnippet&q=" + keyword + "&key=" + apiKey + "&maxResults=" + maxResults;
            URL netURL = new URL(url);

            URLConnection conn = netURL.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);

            InputStream inStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inStream)
            );

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }


            String jsonData = buffer.toString();
            JSONObject data = new JSONObject(jsonData);
            JSONArray items = data.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject id = item.getJSONObject("id");
                String kind = id.getString("kind");

                if (kind.equals("youtube#video") || kind.equals("youtube#searchResult")) {
                    JSONObject snippet = item.getJSONObject("snippet");
                    JSONObject thumbnailData = snippet.getJSONObject("thumbnails").getJSONObject("default");
                    JSONObject idData = item.getJSONObject("id");

                    String title = snippet.getString("title");
                    String thumbnail = thumbnailData.getString("url");
                    String videoID = idData.getString("videoId");

                    videos.add(new Video(title, thumbnail, videoID));
                    System.out.printf("Found: %s  (%s) (%s) \n", title, videoID, thumbnail);
                }
            }

            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }
}

