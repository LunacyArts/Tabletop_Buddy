package lunacyarts.abgstudios.butany62.accessories.dndapp;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tyler Helwig on 10/13/2015.
 */
public class ReadNetwork extends AsyncTask<URL, Void, String> {

    private static final String TAG = "ReadNetwork";
    String method;
    JSONObject data;
    String[][] headers;
    Boolean success = true;
    Exception netErr;


    public ReadNetwork(int TaskID, CommsInterface commsInterface, String method, JSONObject data, String[]... headers) {
        this.taskID = TaskID;
        this.rn = commsInterface;
        this.method = method;
        this.data = data;
        this.headers = headers;
    }

    int taskID;
    CommsInterface rn;
    int errType = 200;

    @Override
    protected String doInBackground(URL... params) {

        for (URL url : params) {

            try {
                Log.d("RN","Building request" + url.toString());
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .writeTimeout(2, TimeUnit.SECONDS)
                        .readTimeout(2, TimeUnit.SECONDS)
                        .build();

                client.followSslRedirects();
                Request request;
                Log.d("OKHTTP", url.toString());
                if(method.equalsIgnoreCase("get")) {
                    request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    Log.d("RN","Built get request");
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .method(method, RequestBody.create(CommsClient.JSON, data.toString()))
                            .build();
                    for (String[] header : headers) {
                        request = request.newBuilder().addHeader(header[0], header[1]).build();
                    }
                    Log.d("RN","Built" + method + "request");
                }
                Log.d("RN","executing request");
                Response response = client.newCall(request).execute();
                String body = response.body().string();

                Log.d("OKHTTP response body", body);

                Log.d("OKHTTP response code", errType + "");
                return body;
            } catch (IOException e) {
                e.printStackTrace();
                netErr = e;
                success = false;
            }

        }
        errType = 444;
        return "Network timeout";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(success){
            rn.handleSuccess(s, taskID);
        } else {
            rn.handleFail(netErr, taskID);
        }

    }
}
