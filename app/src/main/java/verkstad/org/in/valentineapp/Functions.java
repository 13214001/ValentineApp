package verkstad.org.in.valentineapp;

/**
 * Created by coder on 1/17/2016.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anu on 1/11/2016.
 */
public class Functions {
    Context context;
    public interface VolleyCallback{
        void onSuccess(int[] result);
    }

    public interface VolleyCallback1{
        void onSuccess(String[] result);
    }



    // Sending rose
    static String sender,receiver,red_rose,yellow_rose,anonymous,message;
    public void send(Context context1,String sender1,String receiver1, String red_rose1,String yellow_rose1,String anonymous1,String message1){
        sender=sender1;
        receiver=receiver1;
        red_rose=red_rose1;
        yellow_rose=yellow_rose1;
        anonymous=anonymous1;
        message=message1;
        context=context1;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String message = jsonObject.getString("MESSAGE");
                    //String ann  = jsonObject.getString("anonymous");
                    Toast.makeText(context,message,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(context,volleyError.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sender",sender);
                params.put("receiver",receiver);
                params.put("red_rose",red_rose);
                params.put("yellow_rose",yellow_rose);
                params.put("anonymous",anonymous);
                params.put("message",message);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



    int r, y;
    int arr[]=new int[2];
    public int[] count_roses(Context context1,final String name, final VolleyCallback callback){



        context=context1;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
              // Toast.makeText(context, "checking",Toast.LENGTH_LONG).show();
                try {
                    //Toast.makeText(context, "checking",Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = new JSONArray(s);

                    String id[]=new String[jsonArray.length()];String sender[]=new String[jsonArray.length()];
                    String receiver[]=new String[jsonArray.length()]; String red_rose[]=new String[jsonArray.length()];
                    String yellow_rose[]=new String[jsonArray.length()]; String message[]=new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        id[i]=n.getString("id");
                        sender[i]=n.getString("sender");
                        receiver[i]=n.getString("receiver");
                        red_rose[i]=n.getString("red_roses");
                        yellow_rose[i]=n.getString("yellow_roses");
                        message[i]=n.getString("message");
                    }
                    // Toast.makeText(context,"arrays yrr",Toast.LENGTH_LONG).show();

                    r=0;y=0;
                    for(int i=0;i<id.length;i++){
                        if(receiver[i].equals(name)){
                            if(red_rose[i].equals("1")){r++;}
                            if(yellow_rose[i].equals("1")){y++;}

                        }
                    }
                    arr[0]=r;arr[1]=y;
                    callback.onSuccess(arr);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TAG","CountRoses");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        return arr;

    }


    String[] users,email,id,gender;
    public String[] get_users(Context context1, final VolleyCallback1 callback){
        context=context1;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    users = new String[jsonArray.length()]; email = new String[jsonArray.length()];
                    id = new String[jsonArray.length()]; gender = new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        users[i]=jsonObject.getString("name");
                        email[i]=jsonObject.getString("email_id");
                        id[i]=jsonObject.getString("id");
                        gender[i]=jsonObject.getString("gender");

                    }

                    // Toast.makeText(context,Arrays.toString(gender),Toast.LENGTH_LONG).show();
                    callback.onSuccess(users);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TAG","GetUsers");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return users;

    }

}
