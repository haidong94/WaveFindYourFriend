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
    ArrayList<Person> listFriend;
    ArrayList<String> listPhone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);

        addControl(view);
        readFriendFromFireBase();

        config();

        return view;
    }

    private void config() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerChatAdapter = new RecyclerChatAdapter(getContext(),listFriend);
        recyclerView.setAdapter(recyclerChatAdapter);

    }

    private void inforFriend() {
        for (String s:listPhone)
        {
            roof.child("database").child("Person").child(s).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Person c=dataSnapshot.getValue(Person.class);
                    recyclerChatAdapter.addPerson(c);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    private void readFriendFromFireBase() {
        roof.child("database").child("Chat").child(SignInActivity.person.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    listPhone.add(data.getKey());
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
        listFriend=new ArrayList<Person>();
        listPhone=new ArrayList<String>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    }
}
