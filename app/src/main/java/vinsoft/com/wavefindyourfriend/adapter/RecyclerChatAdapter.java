package vinsoft.com.wavefindyourfriend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.activity.MessageActivity;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 05-Apr-17.
 */

public class RecyclerChatAdapter extends RecyclerView.Adapter<RecyclerChatAdapter.RecyclerViewHolder_Message> {
    private List<Person> listFriend;
    private Context context;

    public RecyclerChatAdapter(Context context, List<Person> listFriend){
        this.context=context;
        this.listFriend=listFriend;

    }
    @Override
    public RecyclerViewHolder_Message onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item_chat_layout,parent,false);
        return new RecyclerViewHolder_Message(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_Message holder, int position) {
//        if(listFriend.get(position).getName()==null)
//        {
//            holder.txtName.setText("chua ket ban");
//        }
//        else
        holder.txtName.setText(String.valueOf(listFriend.get(position).getName()));
      //  holder.txtMessage.setText(String.valueOf(listFriend.get(position).toString()));
        String url=listFriend.get(position).getImage();
        if(url!=null)
        {
            Glide.with(context).load(listFriend.get(position).getImage()).thumbnail(0.5f)
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatar);
        }
        else {
            holder.avatar.setImageResource(R.drawable.ic_profile_);
        }

    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

    public class RecyclerViewHolder_Message extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName,txtMessage,txtVision;
        CircleImageView avatar;

        public RecyclerViewHolder_Message(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtName= (TextView) itemView.findViewById(R.id.txtName);
            txtMessage= (TextView) itemView.findViewById(R.id.txtMessage);
            txtVision= (TextView) itemView.findViewById(R.id.txtVision);
            avatar= (CircleImageView) itemView.findViewById(R.id.avatar);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, MessageActivity.class);
            intent.putExtra("FriendID",listFriend.get(getPosition()).getId());
            context.startActivity(intent);
        }
    }
}
