package app.test.wyp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Toast.makeText(context,"checking connection",Toast.LENGTH_LONG).show();
        Log.i("active network", ""+activeNetwork);
        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        Toast.makeText(context,"connected",Toast.LENGTH_LONG).show();
                        //calling the method to save the unsynced name to MySQL
                        saveName(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENRE)));
                        Log.i("saved ", ""+ cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                    } while (cursor.moveToNext());
                }
            }
        }else {
            Toast.makeText(context,"no internet",Toast.LENGTH_LONG).show();
        }
    }

    public void saveName(final int id, final String phone, final String name, final String genre) {

        class SendJsonDataTOServer extends AsyncTask<String, Void, String> {

            protected void onPreExecute(){
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL("https://db-node-api.herokuapp.com/api/user");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("phone", phone);
                    jsonObject.put("user_name", name);
                    jsonObject.put("genre", genre);

                    DataOutputStream localDataOutputStream = new DataOutputStream(conn.getOutputStream());
                    localDataOutputStream.writeBytes(jsonObject.toString());
                    localDataOutputStream.flush();
                    localDataOutputStream.close();

                    int responseCode = conn.getResponseCode();

                    Log.i("res",""+responseCode);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.i("sb", ""+sb);
                    in.close();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        return sb.toString();
                    }else {
                        return null;
                    }

                }
                catch(Exception e){
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.i("h", ""+result);
                if (result != null) {
                    try {
                        JSONObject js = new JSONObject(result);
                        //js.getJSONObject(result);
                        if(!js.getBoolean("error")){
                            //updating the status in sqlite
                            db.updateNameStatus(id, AddGenre.NAME_SYNCED_WITH_SERVER);

                            //sending the broadcast to refresh the list
                            context.sendBroadcast(new Intent(AddGenre.DATA_SAVED_BROADCAST));

                            //Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                        }
                        //if(js.has("errorTrue")){
                        //  Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                        //}

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        new SendJsonDataTOServer().execute();
    }
}