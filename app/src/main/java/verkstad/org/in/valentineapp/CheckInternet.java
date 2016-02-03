package verkstad.org.in.valentineapp;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by coder on 2/3/2016.
 */
public class CheckInternet extends AsyncTask<Void,Void,Boolean> {
    Context show_dialog;
    ProgressDialog progressDialog;
    Functions functions;
    public CheckInternet(Context context){
        show_dialog=context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(show_dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
       // functions=new Functions();
        //return functions.isOnline();
        return true;
    }



    @Override
    protected void onPostExecute(Boolean result) {

      if (result==true){
          progressDialog.cancel();
          Toast.makeText(show_dialog,"done",Toast.LENGTH_LONG).show();
      }
        else {
          progressDialog.cancel();
          return;
      }
    }
}
