package com.example.admin.calender;

/**
 * Created by Admin on 8/21/2015.
 */
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.client.utils.URLEncodedUtils;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.util.EntityUtils;

        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.util.List;

/**
 * Created by Admin on 6/12/2015.
 */
public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    //empty constructor
    public ServiceHandler(){

    }

    /**
     * Make service call to fetch json
     * @param url - url from to fecth json data
     * @param method - method used to fetch json data
     * @return - json response received
     */
    public String makeServiceCall(String url, int method){
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Make service call to fetch json with optional parameters for request
     * @param url - url from to fetch json data
     * @param method - method used to fetch json data
     * @param params - parameter to send with reqquest(optional)
     * @return - json response received
     */
    private String makeServiceCall(String url, int method, List<NameValuePair> params) {

        try {
            //http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            //Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                //adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                //appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return response;
    }
}
