package vinsoft.com.wavefindyourfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
    public void onBindViewHolder(RecyclerViewHolder_Contact holder, int position) {
        Contact c = list.get(position);
        for (String s : listFriend){
            if(!c.getPhone().equals(s)) {
                holder.imgAdd.setVisibility(View.VISIBLE);
                holder.txtContact.setVisibility(View.GONE);
            }
            else {
                holder.imgAdd.setVisibility(View.GONE);
                holder.txtContact.setVisibility(View.VISIBLE);
                break;
            }
        }
        holder.txtName.setText(String.valueOf(list.get(position).getName()));
        holder.txtPhone.setText(String.valueOf(list.get(position).getPhone()));
//        for (Contact c:list)
//        {
//            for(String s:listFriend)
//            {
//
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder_Contact extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName,txtPhone,txtContact;
        ImageView imageContact,imgAdd;
        public RecyclerViewHolder_Contact(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtName= (TextView) itemView.findViewById(R.id.txtName);
            txtPhone= (TextView) itemView.findViewById(R.id.txtPhone);
            imageContact= (ImageView) itemView.findViewById(R.id.imageContact);
            txtContact= (TextView) itemView.findViewById(R.id.txtContact);
            imgAdd= (ImageView) itemView.findViewById(R.id.imgAdd);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
