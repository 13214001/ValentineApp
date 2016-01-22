package verkstad.org.in.valentineapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anu on 1/19/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<String> sender_names,sent_messages;
    ArrayList<Integer> profile_pics;
    public RecyclerViewAdapter(Context context, ArrayList<String> sender_names, ArrayList<String> sent_messages, ArrayList<Integer> profile_pics){
        inflater=LayoutInflater.from(context);
        this.sender_names=sender_names;
        this.sent_messages=sent_messages;
        this.profile_pics=profile_pics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.senders_list_row,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String sender_name = sender_names.get(i);
        String sent_message = sent_messages.get(i);
        int profile_pic_id=profile_pics.get(i);
        viewHolder.sender_name.setText(sender_name);
        viewHolder.sent_message.setText(sent_message);
        viewHolder.profile_pic.setImageResource(profile_pic_id);

    }

    @Override
    public int getItemCount() {
        return sender_names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView sender_name;
        TextView sent_message;
        ImageView  profile_pic;
        public ViewHolder(View itemView) {
            super(itemView);
            sender_name= (TextView) itemView.findViewById(R.id.sender_name);
            sent_message= (TextView) itemView.findViewById(R.id.sent_message);
            profile_pic= (ImageView) itemView.findViewById(R.id.senders_list_image);
        }
    }
}
