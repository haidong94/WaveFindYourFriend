package vinsoft.com.wavefindyourfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.model.Contact;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 30-Mar-17.
 */

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.RecyclerViewHolder_Contact> {

    private List<Contact> list;
    private Context context;
    private List<String> listFriend;
    Firebase roof;


    public RecyclerContactAdapter(Context context, List<Contact> list, List<String> listFriend){
        this.context=context;
        this.list=list;
        this.listFriend=listFriend;

    }
    @Override
    public RecyclerViewHolder_Contact onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item_contact_layout,parent,false);
        return new RecyclerViewHolder_Contact(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_Contact holder, int position) {
        Contact c = list.get(position);
        for (String s : listFriend){
            if(!c.getPhone().equals(s)) {
                holder.imgAdd.setVisibility(View.VISIBLE);
                holder.txtContact.setVisibility(View.GONE);
                holder.avatar.setImageResource(R.drawable.ic_profile_);
            }
            else {
                holder.imgAdd.setVisibility(View.GONE);
                holder.txtContact.setVisibility(View.VISIBLE);
                roof.child("database").child("Person").child(c.getPhone()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Person c=dataSnapshot.getValue(Person.class);
                        String url=c.getImage();
                        if(url==null)
                            holder.avatar.setImageResource(R.drawable.ic_profile_);
                        else
                        {
                            Glide.with(context).load(url).thumbnail(0.5f)
                                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.avatar);
                        }
                       //
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;
            }
        }


        holder.txtName.setText(String.valueOf(list.get(position).getName()));
        holder.txtPhone.setText(String.valueOf(list.get(position).getPhone()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder_Contact extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName,txtPhone,txtContact;
        ImageView imgAdd;
        CircleImageView avatar;

        public RecyclerViewHolder_Contact(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Firebase.setAndroidContext(context);
            roof=new Firebase("https://chatandmap.firebaseio.com");
            txtName= (TextView) itemView.findViewById(R.id.txtName);
            txtPhone= (TextView) itemView.findViewById(R.id.txtPhone);
            txtContact= (TextView) itemView.findViewById(R.id.txtContact);
            imgAdd= (ImageView) itemView.findViewById(R.id.imgAdd);
            avatar= (CircleImageView) itemView.findViewById(R.id.avatar);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
