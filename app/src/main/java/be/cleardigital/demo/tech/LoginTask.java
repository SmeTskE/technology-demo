package be.cleardigital.demo.tech;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginTask extends AsyncTask<Void, Long, String> {

    private Long mStartTime;

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient httpClient = new OkHttpClient();

        RequestBody requestBody = new FormEncodingBuilder()
                .add("username", "cl120189rs")
                .add("password", "test")
                .build();

        Request request = new Request.Builder()
                .url("http://api.werf-registratie.be/login")
                .addHeader("Accept", "application/json")
                .post(requestBody)
                .build();

        Response response = null;
        String responseString = "";

        try {
            response = httpClient.newCall(request).execute();
            responseString = response.body().string();

            Log.d(this.getClass().getSimpleName(), responseString);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), ex.toString());
        }

        if (!responseString.equals("")) {
            InputStream inputStream = null;
            try {
                inputStream = new ByteArrayInputStream(responseString.getBytes("UTF-8"));
            } catch (Exception ex) {
                Log.e(this.getClass().getSimpleName(), ex.toString());
            }

            if (inputStream != null) {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));

                Gson gson = new Gson();
                Employee employee = (Employee) gson.fromJson(jsonReader, Employee.class);

                Log.d(this.getClass().getSimpleName(), employee.getFirstName() + " " + employee.getLastName() +
                        " (" + employee.getEnterprise().getName() + ")");
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mStartTime = System.currentTimeMillis();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        long time = System.currentTimeMillis() - mStartTime;

        Log.d(this.getClass().getSimpleName(), "Downloading & parsing took: " + time + "ms");
    }
}
