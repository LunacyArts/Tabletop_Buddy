package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;

/**
 * Created by Tyler Helwig on 5/13/2017.
 */

class CommsClient{
    private String HEADER_USERNAME = "BuddyUser";
    private String HEADER_PASSWORD = "BuddyPass";
    static MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private URL url;
    private CommsInterface commsInterface;
    CommsClient(String s, CommsInterface commsInterface) throws MalformedURLException {
        this.url = new URL(s);
        this.commsInterface = commsInterface;
    }

    void auth(String username, String password) throws IOException {
        String method = "POST";
        JSONObject data = new JSONObject();
        String[] usernameHeader = {HEADER_USERNAME, username};
        String[] passHeader = {HEADER_PASSWORD, password};
        String[][] headers = {usernameHeader, passHeader};

        ReadNetwork rn = new ReadNetwork(0, commsInterface, method, data, headers);
        Log.d("CommsClient","Starting new network task");
        rn.execute(url);

    }

}
