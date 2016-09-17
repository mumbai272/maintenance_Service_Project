package com.android.maintenance.WS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by anand on 09-Sep-16.
 */

import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ServiceHandlerWS {

    static InputStream is = null;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    private static final String TAG = "WSMASSAGE";
    public ServiceHandlerWS() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceGet(String url, int method) {
        Log.e(TAG, "inside makeServiceCall");
        return this.makeServiceCall(url, method,null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            Log.e(TAG, "inside makeServiceCall");
            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpget = new HttpGet(url);
                httpget.setHeader("Content-type", "application/json");
                httpResponse = httpClient.execute(httpget);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }
       // Log.e(TAG,"Response data"+response);
        return response;

    }

    public static String makeServicePost(String url, String data){
        String result = "";
        try {
            Log.e(TAG,"URL: "+url);
            Log.e(TAG,"data: "+data);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/json");
            StringEntity se = new StringEntity(data);
            post.setEntity(se);
            HttpResponse httpResponse = httpclient.execute(post);
            result=EntityUtils.toString(httpResponse.getEntity());
            //inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
        }catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
       /* Log.i("JSONPOSTEND", "End of JSON data post methos...");*/
        Log.e(TAG,"Response====="+result);
        return result;
    }
}