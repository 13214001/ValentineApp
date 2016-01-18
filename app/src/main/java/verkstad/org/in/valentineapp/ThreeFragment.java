package verkstad.org.in.valentineapp;


import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by coder on 1/15/2016.
 */
public class ThreeFragment extends android.support.v4.app.Fragment {
    //String login_url="http://192.168.16.1/Valentine/index.php";
    String[] name={"anu","abhi","chetan"};

    private List<Shout> Shoutitems = new ArrayList<Shout>();
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
        ListView listView= (ListView)view. findViewById(R.id.list);
       /** ArrayAdapter adapter=new ArrayAdapter(ThreeFragment.this.getActivity(), android.support.design.R.layout.support_simple_spinner_dropdown_item,name);
        listView.setAdapter(adapter); **/
        for(int i=0;i<3;i++) {
            Shout shouts = new Shout();
            shouts.setId("123456789");
            shouts.setMessage("Hi There");
            shouts.setName("Anu");
            shouts.setTime((long) 1453005006);
            Shoutitems.add(shouts);
        }
        ShoutListview adapter=new ShoutListview(ThreeFragment.this.getActivity(),Shoutitems);
        listView.setAdapter(adapter);
        shout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue request = Volley.newRequestQueue(ThreeFragment.this.getActivity());

                Profile profile = Profile.getCurrentProfile();
                final String name = profile.getName();
                final EditText editText = (EditText) view.findViewById(R.id.EditText);
                final String message = editText.getText().toString();
                StringRequest stringrequest = new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

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

                        return params;
                    }
                };
                request.add(stringrequest);
            }
        });
        return view;
    }

    public void ShoutList(){



    }
}
