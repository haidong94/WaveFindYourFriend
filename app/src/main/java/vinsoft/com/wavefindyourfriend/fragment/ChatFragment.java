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

public class ChatFragment extends Fragment {
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
        roof.child("database").child("Chat").child(SignInActivity.person.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChat.clear();
                listPerson.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    listChat.add(data.getKey());
                }
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
        listChat=new ArrayList<String>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    }
}
