package com.example.admin.calender;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;

/**
 * Created by Admin on 8/21/2015.
 */
public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    //URL to get contacts json
    private static String url = "https://nodejs-calender-prashantdawar.c9.io/shedule/btech/mon/cse1.json";

    //JSON Node names

    private static  final String TAG_EVENT = "event";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ST = "st";
    private static final String TAG_ET = "et";
    private static final String TAG_SUBJECT_NAME = "subject_name";
    private static final String TAG_ROOM_NO = "room_no";
    private static final String TAG_SLOT = "slot";


    //contacts JSONArray
    JSONArray contacts = null;

    //Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<HashMap<String, String>>();


        //Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void>{

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
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    //Getting JSON Array node
                    //contacts = jsonObj.getJSONArray(TAG_EVENT);

                    JSONArray schedule = new JSONArray(jsonStr);


                    //looping through all contacts
                    for (int i = 0; i < schedule.length(); i++) {
                        JSONObject c = schedule.getJSONObject(i);
                        Log.d("JSONOBJECt " + i,c.toString());

                        String subject_name = c.getString(TAG_SUBJECT_NAME);
                        String room_no = c.getString(TAG_ROOM_NO);
                        String slot = c.getString(TAG_SLOT);



                        //tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        //adding each child node to HashMap key => value
                        contact.put(TAG_SUBJECT_NAME,subject_name);
                        contact.put(TAG_ROOM_NO, room_no);
                        contact.put(TAG_SLOT, slot);


                        //adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            } else {
                Log.e("Serviehandler", "Couldn't get any data from the url");
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
                        MainActivity.this, contactList,
                        R.layout.list_item, new String[] {
                        TAG_SUBJECT_NAME, TAG_ROOM_NO,TAG_SLOT
                }, new int[] {
                        R.id.name,R.id.email,R.id.mobile
                });

                setListAdapter(adapter);
            }
        }
    }
}
