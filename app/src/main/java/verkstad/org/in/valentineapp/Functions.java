package verkstad.org.in.valentineapp;

/**
 * Created by coder on 1/17/2016.
 */

import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anu on 1/11/2016.
 */
public class Functions {
    Context context;
    public interface VolleyCallback{
        void onSuccess(int size);
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
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();


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

    int size_json;
    String[] sender_json,receiver_json,red_rose_json,yellow_rose_json,message_json,anonymous_json;
    public int count_roses(Context context1, final VolleyCallback callback){



        context=context1;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    //Toast.makeText(context, "checking",Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = new JSONArray(s);

                    sender_json=new String[jsonArray.length()];  anonymous_json=new String[jsonArray.length()];
                    receiver_json=new String[jsonArray.length()];  red_rose_json=new String[jsonArray.length()];
                    yellow_rose_json=new String[jsonArray.length()]; message_json=new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        sender_json[i]=n.getString("sender");
                        receiver_json[i]=n.getString("receiver");
                        red_rose_json[i]=n.getString("red_roses");
                        yellow_rose_json[i]=n.getString("yellow_roses");
                        message_json[i]=n.getString("message");
                        anonymous_json[i]=n.getString("anonymous");
                    }
                    // Toast.makeText(context,"arrays yrr",Toast.LENGTH_LONG).show();

                    size_json=jsonArray.length();
                    callback.onSuccess(jsonArray.length());

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
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
        return size_json;
    }


    String[] users,email,id,gender;
    int users_json_size;
    public int get_users(Context context1, final VolleyCallback callback){
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
                    users_json_size=jsonArray.length();
                    // Toast.makeText(context,Arrays.toString(gender),Toast.LENGTH_LONG).show();
                    callback.onSuccess(jsonArray.length());
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
        return users_json_size;

    }

}
