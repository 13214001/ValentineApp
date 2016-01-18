package verkstad.org.in.valentineapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;

import java.net.URL;
import java.util.List;

/**
 * Created by coder on 1/16/2016.
 */
public class ShoutListview extends BaseAdapter {
    private Activity activity;
    Bitmap bitmaps;
    Bitmap bitmapr;
    private List<Shout> Shoutitems;
    private LayoutInflater inflater;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public ShoutListview(Activity activity,List<Shout> Shoutitems){
      this.activity=activity;
        this.Shoutitems=Shoutitems;
    }
    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageUrl = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");

        Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());

        return bitmap;
    }
    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
    @Override
    public int getCount() {
        return Shoutitems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        TextView name=(TextView)convertView.findViewById(R.id.name);
        TextView message=(TextView)convertView.findViewById(R.id.message);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        ImageView profile=(ImageView)convertView.findViewById(R.id.list_image);


        Shout s=Shoutitems.get(position);
        String timeAgo=getTimeAgo(s.getTime());
        name.setText(s.getName());
        message.setText(s.getMessage());
        try {
            bitmaps = getFacebookProfilePicture("1683072511916372");
            bitmapr=getResizedBitmap(bitmaps,30,30);
            //profile.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();


        }
        // Converting timestamp into x ago format

        time.setText(timeAgo);
        profile.setImageBitmap(bitmapr);

        return convertView;
    }
}
