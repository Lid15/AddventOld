package nord.is.addvent;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ólafur Georg Gylfason on 21-Feb-18.
 */

public class EventFetcher {

    private static final String TAG = "EventFetcher";
    private static final String GET_ALL_EVENTS = "https://addvent-ws.herokuapp.com/event/all";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<EventItem> fetchItems() {

        List<EventItem> items = new ArrayList<>();

        try {
            String url = Uri.parse(GET_ALL_EVENTS)
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONArray eventJsonArray = new JSONArray(jsonString);
            parseItems(items, eventJsonArray);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    private void parseItems(List<EventItem> items, JSONArray eventJsonArray)
            throws IOException, JSONException {
        for (int i = 0; i < eventJsonArray.length(); i++) {
            JSONObject eventJsonObject = eventJsonArray.getJSONObject(i);

            EventItem item = new EventItem();
            item.setTitle(eventJsonObject.getString("title"));
            item.setLocation(eventJsonObject.getString("location"));
            item.setHost(eventJsonObject.getString("host"));
            item.setDescription(eventJsonObject.getString("description"));

            items.add(item);
        }
    }
}
