package verkstad.org.in.valentineapp;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;

import java.util.Arrays;

/**
 * Created by coder on 1/15/2016.
 */
public class OneFragment extends android.support.v4.app.Fragment {

    Functions functions;
    TextView textView,textView2,textView3;
    EditText editText2;
    String current_user,red_rose,yellow_rose,receiver,message;
    String anonymous="no";
    RadioGroup radioGroup;
    Button send_flowers,received_flowers,send;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    LinearLayout linearLayout,linearLayout2;
    ListView listView;
    String arr1[] = {"anu","abhishek","chetan","akash"};
    String arr2[] = {"hiiii","hellooo","hahaha","awwww"};
    CheckBox anonymous_checkbox;
    int img[] = {R.drawable.splash,R.drawable.splash,R.drawable.splash,R.drawable.splash};

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_one, container, false);

        functions = new Functions();
        radioGroup = (RadioGroup)view.findViewById(R.id.radio);
        autoCompleteTextView =(AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        editText2= (EditText)view.findViewById(R.id.message_box);
        linearLayout = (LinearLayout)view.findViewById(R.id.linear);
        linearLayout2 = (LinearLayout)view.findViewById(R.id.linear2);
        listView= (ListView)view.findViewById(R.id.listView);
        textView= (TextView)view.findViewById(R.id.current_user);
        textView2= (TextView)view.findViewById(R.id.display_red);
        textView3= (TextView)view.findViewById(R.id.display_yellow);
        send_flowers= (Button) view.findViewById(R.id.send_flower);
        received_flowers= (Button) view.findViewById(R.id.received_flowers);
        send = (Button) view.findViewById(R.id.send);
        anonymous_checkbox= (CheckBox) view.findViewById(R.id.anonymous);

        Profile profile = Profile.getCurrentProfile();
        current_user = profile.getName();
        // Toast.makeText(getApplicationContext(),current_user,Toast.LENGTH_LONG).show();

         textView.setText(current_user);
        functions.count_roses(OneFragment.this.getActivity(), current_user, new Functions.VolleyCallback() {
            @Override
            public void onSuccess(int[] result) {
                textView2.setText("" + result[0]);
                textView3.setText("" + result[1]);
                //Toast.makeText(OneFragment.this.getActivity(), "fgh", Toast.LENGTH_LONG).show();
            }
        });

        functions.get_users(OneFragment.this.getActivity(), new Functions.VolleyCallback1() {
            @Override
            public void onSuccess(String[] result) {
                Toast.makeText(OneFragment.this.getActivity(), Arrays.toString(result),Toast.LENGTH_SHORT).show();
                arrayAdapter = new ArrayAdapter<String>(OneFragment.this.getActivity(), R.layout.support_simple_spinner_dropdown_item, result);
                autoCompleteTextView.setAdapter(arrayAdapter);
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                receiver = arrayAdapter.getItem(position).toString();
            }
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiver = null;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        send_flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);

            }
        });

        received_flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
             //   rflr = new received_flowers_list_rows(Home.this,img,arr1,arr2);
                 ArrayAdapter arrayAdapter = new ArrayAdapter(OneFragment.this.getActivity(),R.layout.support_simple_spinner_dropdown_item,arr1);
                listView.setAdapter(arrayAdapter);


            }
        });

        anonymous_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=((CheckBox)v).isChecked();
                if(check){
                    anonymous="yes";
                }
                else{anonymous="no";}
            }
        });




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=editText2.getText().toString();

                if(receiver==null){
                    Toast.makeText(OneFragment.this.getActivity(),"enter valid name",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(OneFragment.this.getActivity(), "Please Select a rose", Toast.LENGTH_SHORT).show();
                    } else {
                        functions.send(OneFragment.this.getActivity(), current_user, receiver, "1", "0", anonymous, message);
                    }

                    radioGroup.clearCheck();
                }
                autoCompleteTextView.setText("");
                editText2.setText("");

            }
        });


        // Inflate the layout for this fragment
        return view;
    }








}
