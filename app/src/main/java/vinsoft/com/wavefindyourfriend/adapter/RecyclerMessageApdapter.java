package vinsoft.com.wavefindyourfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.activity.SignInActivity;
import vinsoft.com.wavefindyourfriend.common.ConnectFirebase;
import vinsoft.com.wavefindyourfriend.common.Util;
import vinsoft.com.wavefindyourfriend.model.Message;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 05-Apr-17.
 */

public class RecyclerMessageApdapter extends RecyclerView.Adapter<RecyclerMessageApdapter.ViewHolder> {

    private List<Message> listMessage;
    private Context mContext;
    Person c;


    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public RecyclerMessageApdapter( Context mContext,List<Message> listMessage) {
        this.listMessage = listMessage;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_message_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Message message = listMessage.get(position);
       // setLayoutChat(holder);
        RelativeLayout.LayoutParams paramsAvatar = (RelativeLayout.LayoutParams) holder.imgAvatar.getLayoutParams();
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) holder.llBubbleMessage.getLayoutParams();
        RelativeLayout.LayoutParams paramsInfo = (RelativeLayout.LayoutParams) holder.tvInfo.getLayoutParams();
        //set location item chat
        if (!message.getPersonSendID().equals(SignInActivity.person.getId())) {
            holder.llBubbleMessage.setBackgroundResource(R.drawable.out_message_bg);
            paramsAvatar.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            llParams.addRule(RelativeLayout.RIGHT_OF,R.id.img_message_avatar);
            paramsInfo.addRule(RelativeLayout.BELOW, R.id.ll_bubble_message);
            paramsInfo.addRule(RelativeLayout.RIGHT_OF,R.id.img_message_avatar);
        }
        else {
            holder.llBubbleMessage.setBackgroundResource(R.drawable.in_message_bg);
            paramsAvatar.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            llParams.addRule(RelativeLayout.LEFT_OF,R.id.img_message_avatar);
            paramsInfo.addRule(RelativeLayout.BELOW, R.id.ll_bubble_message);
            paramsInfo.addRule(RelativeLayout.LEFT_OF,R.id.img_message_avatar);
        }
         ConnectFirebase.getConnect(mContext).child("database").child("Person").child(message.getPersonSendID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c=dataSnapshot.getValue(Person.class);
                if(c.getImage()==null)
                {
                    holder.imgAvatar.setImageResource(R.drawable.ic_profile_);
                }
                else {
                    Glide.with(mContext).load(c.getImage()).thumbnail(0.5f)
                            .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.imgAvatar);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        holder.imgAvatar.setLayoutParams(paramsAvatar);
        holder.llBubbleMessage.setLayoutParams(llParams);
        holder.tvInfo.setLayoutParams(paramsInfo);
        holder.tvMessage.setText(message.getContentMessage());

        String time=message.getDateTime();
        if(time!=null)
            holder.tvInfo.setText(Util.getHours(Long.parseLong(time)));
        else
            holder.tvInfo.setText("");
    }

    @Override
    public int getItemCount() {
        if(listMessage==null||listMessage.size()<=0)
            return 0;
        return listMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo, tvMessage;
        LinearLayout llBubbleMessage;
        CircleImageView imgAvatar;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_info);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            llBubbleMessage = (LinearLayout) itemView.findViewById(R.id.ll_bubble_message);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.img_message_avatar);
        }
    }

}