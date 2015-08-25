package com.example.admin.calender;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.ListAdapter;
        import android.widget.SimpleAdapter;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;
    public CalenderDataSource datasource = new CalenderDataSource(this);

    //URL to get contacts json
    private static String url = "https://nodejs-calender-prashantdawar.c9.io/shedule/btech/mon/cse1.json";

    //JSON Node names

    private static final String TAG_SUBJECT_NAME = "subject_name";
    private static final String TAG_ROOM_NO = "room_no";
    private static final String TAG_SLOT = "slot";

    //tmp hashmap for single contact
    HashMap<String, String> contact = new HashMap<String, String>();


    //Hashmap for ListView
    ArrayList<HashMap<String, String>> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleList = new ArrayList<HashMap<String, String>>();


        //Calling async task to get json
        new GetSchedule().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetSchedule extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Creating service handler class interface
            ServiceHandler sh = new ServiceHandler();

            //Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if(jsonStr != null){
                try {

                    JSONArray schedule = new JSONArray(jsonStr);


                    //looping through all contacts
                    for (int i = 0; i < schedule.length(); i++) {
                        JSONObject c = schedule.getJSONObject(i);
                        Log.d("JSONOBJECt " + i,c.toString());

                        String subject_name = c.getString(TAG_SUBJECT_NAME);
                        String room_no = c.getString(TAG_ROOM_NO);
                        String slot = c.getString(TAG_SLOT);

                        //adding each child node to HashMap key => value
                        contact.put(TAG_SUBJECT_NAME,subject_name);
                        contact.put(TAG_ROOM_NO, room_no);
                        contact.put(TAG_SLOT, slot);


                        //adding contact to contact list
                        scheduleList.add(contact);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            } else {
                Log.e("Servicehandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();

                /**
                 * Updating Parsed json data into ListView
                 */
                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this, scheduleList,
                        R.layout.list_item, new String[] {
                        TAG_SUBJECT_NAME, TAG_ROOM_NO,TAG_SLOT
                }, new int[] {
                        R.id.name,R.id.email,R.id.mobile
                });

                for (HashMap<String, String> map : scheduleList) {
                    String[] key = null;
                    String[] value = null;
                    int i =0;
                    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                        key[i] = mapEntry.getKey();
                        value[i] = mapEntry.getValue();
                        i++;
                    }
                    try {
                        datasource.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    datasource.createSchedule(value[0], value[1], value[2]);
                }

                setListAdapter(adapter);
            }
        }
    }
}
