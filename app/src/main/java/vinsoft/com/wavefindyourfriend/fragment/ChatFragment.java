package vinsoft.com.wavefindyourfriend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.activity.SignInActivity;
import vinsoft.com.wavefindyourfriend.adapter.RecyclerChatAdapter;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 04-Apr-17.
 */

public class ChatFragment extends Fragment  {
    Firebase roof;
    RecyclerChatAdapter recyclerChatAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ArrayList<Person> listPerson;
    ArrayList<String> listChat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);


        addControl(view);
        config();
        readFriendFromFireBase();

        return view;
    }

    private void config() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerChatAdapter = new RecyclerChatAdapter(getContext(),listPerson,listChat);
        recyclerView.setAdapter(recyclerChatAdapter);

    }

    private void inforFriend() {
            roof.child("database").child("Person").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Person c=data.getValue(Person.class);
                        listPerson.add(c);
                    }
                    recyclerChatAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
    }

    private void readFriendFromFireBase() {
        roof.child("database").child("Groupp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPerson.clear();
                listChat.clear();
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    final String key=data.getKey();
                    roof.child("database").child("Groupp").child(key).child("Member").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data:dataSnapshot.getChildren())
                            {
                                String id=data.getKey();
                                if(id.equals(SignInActivity.person.getId()))
                                {
                                    int condition=0;
                                    if (listChat.size() == 0)
                                        listChat.add(key);

                                    for (String s:listChat) {
                                        if (key.equals(s)) {
                                           condition=1;
                                            break;
                                        }
                                    }

                                    if (condition==0)
                                        listChat.add(key);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }

//                for(String s:listChat)
//                {
//                    ConnectFirebase.getConnect(getContext()).child("database").child("Groupp").child(s).child("Conversation").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for(DataSnapshot data:dataSnapshot.getChildren())
//                        {
//                            Message message=data.getValue(Message.class);
//                            String time=Util.getHours(Long.parseLong(message.getDateTime()));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//                }
                inforFriend();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    private void addControl(View view) {
        Firebase.setAndroidContext(getContext());
        roof=new Firebase("https://chatandmap.firebaseio.com");
        listPerson=new ArrayList<Person>();
        listChat=new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    }
}
