package vinsoft.com.wavefindyourfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.adapter.RecyclerMessageApdapter;
import vinsoft.com.wavefindyourfriend.common.ConnectFirebase;
import vinsoft.com.wavefindyourfriend.model.Message;

/**
 * Created by DONG on 05-Apr-17.
 */

public class MessageActivity extends AppCompatActivity {
 //   private static final int MY_NOTIFICATION_ID = 12345;
    ImageView imgChatSend;
    EditText edtMessage;
    RecyclerView recyclerView;

    ArrayList<Message> listChatHistory;
    LinearLayoutManager linearLayoutManager;
    RecyclerMessageApdapter messageApdapter;

    String userID, groupId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent=getIntent();
        userID=intent.getStringExtra("FriendID");
        initWidget();
        getListChat();
        sendMessage();

    }

    private void sendMessage() {
        final Message message=new Message();
        imgChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setContentMessage(edtMessage.getText().toString());
                message.setPersonSendID(SignInActivity.person.getId());
                edtMessage.getText().clear();
                message.setDateTime(String.valueOf(System.currentTimeMillis()));

                ConnectFirebase.getConnect(MessageActivity.this).child("database").child("Chat").child(SignInActivity.person.getId()).child(userID).child(String.valueOf(System.currentTimeMillis())).setValue(message);
            }
        });

    }

    private void config() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageApdapter = new RecyclerMessageApdapter(this,listChatHistory);
        recyclerView.setAdapter(messageApdapter);



    }

    private void getListChat() {
        Firebase roof=ConnectFirebase.getConnect(this);
        roof.child("database").child("Chat").child(SignInActivity.person.getId()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChatHistory.clear();
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    Message message=data.getValue(Message.class);
                    listChatHistory.add(message);
                }
                config();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void initWidget() {
        imgChatSend = (ImageView) findViewById(R.id.img_chat_send);
        edtMessage = (EditText) findViewById(R.id.edt_message);
        listChatHistory=new ArrayList<Message>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);


    }
}