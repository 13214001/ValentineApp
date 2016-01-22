package verkstad.org.in.valentineapp;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.LogRecord;

/**
 * Created by coder on 1/15/2016.
 */
public class ThreeFragment extends android.support.v4.app.Fragment  {
    //String login_url="http://192.168.16.1/Valentine/index.php";
    String[] name={"anu","abhi","chetan"};
    ListView listView;
    ShoutListview adapter;
    Long lastMsgTime;
    Handler handler;
    private boolean isRunning;
    private List<Shout> Shoutitems;
    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_three, container, false);
        Button shout=(Button)view.findViewById(R.id.shout);
        //ListView listView=(ListView)view.findViewById(R.id.list);
        listView= (ListView)view. findViewById(R.id.list);
        handler=new Handler();
        Shoutitems = new ArrayList<Shout>();
       /** ArrayAdapter adapter=new ArrayAdapter(ThreeFragment.this.getActivity(), android.support.design.R.layout.support_simple_spinner_dropdown_item,name);
        listView.setAdapter(adapter); **/
       /** for(int i=0;i<3;i++) {
            Shout shouts = new Shout();
            shouts.setId("123456789");
            shouts.setMessage("Hi There");
            shouts.setName("Abhi");
            shouts.setTime((long) 1453139839);
            Shoutitems.add(shouts);
        } **/


        ShoutList();
        adapter=new ShoutListview(ThreeFragment.this.getActivity(),Shoutitems);
        listView.setAdapter(adapter);
        shout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue request = Volley.newRequestQueue(ThreeFragment.this.getActivity());

                Profile profile = Profile.getCurrentProfile();
                final String name = profile.getName();
                final String id=profile.getId();
                final EditText editText = (EditText) view.findViewById(R.id.EditText);
                final String message = editText.getText().toString();
                StringRequest stringrequest = new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Shout shouts = new Shout();
                        shouts.setId(id);
                        shouts.setMessage(message);
                        shouts.setName(name);
                       // shouts.setTime();
                        Shoutitems.add(shouts);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("message", message);
                        params.put("id",id);
                        return params;
                    }
                };
                request.add(stringrequest);
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
        ShoutList();
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    public void ShoutList(){

        RequestQueue request=Volley.newRequestQueue(ThreeFragment.this.getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    if(jsonArray!=null && jsonArray.length()>0) {
                        for (int i = jsonArray.length()-1; i>=0; i--) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Shout shouts = new Shout();
                            shouts.setId(jsonObject.getString("id"));
                            shouts.setMessage(jsonObject.getString("message"));
                            shouts.setName(jsonObject.getString("name"));
                            shouts.setTime(jsonObject.getLong("time"));
                            Shoutitems.add(shouts);
                            if (lastMsgTime == null
                                    || lastMsgTime.compareTo(shouts.getTime())<0)
                                lastMsgTime = shouts.getTime();
                            adapter.notifyDataSetChanged();

                        }
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRunning)
                            ShoutList();
                        }
                    },1000);

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
                Map<String, String> params = new HashMap<String, String>();
               if (Shoutitems.size()==0)
                params.put("TAG","ShoutList");
                else{
                    params.put("TAG","ShoutListSorted");
                    params.put("lastMsgTime",lastMsgTime.toString());
                   //params.put("lastMsgTime","1450289156000");
                }
                return params;
            }
        };
        request.add(stringRequest);
    }
}
