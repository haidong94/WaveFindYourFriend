package vinsoft.com.wavefindyourfriend.activity;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.adapter.RecyclerContactAdapter;
import vinsoft.com.wavefindyourfriend.model.Contact;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 30-Mar-17.
 */

public class ContactFriendActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST =123 ;
    RecyclerView recyclerView;
    RecyclerContactAdapter recyclerContactAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Contact> listContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        checkPermission();

        addControl();
        readAllContactFromPhone();
        addEvent();

    }

    private void addEvent() {
        // Setting the LayoutManager.

    }

    private void addControl() {
        listContact=new ArrayList<Contact>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerContactAdapter = new RecyclerContactAdapter(ContactFriendActivity.this,listContact);
        recyclerView.setAdapter(recyclerContactAdapter);

    }

    private void checkPermission() {
        String[] listPermission = new String[] {android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.READ_PHONE_STATE};
        boolean isOn = false;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
           // textStatus1.setText("On");
        } else {
           // textStatus1.setText("Off");
            isOn = true;
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
           // textStatus2.setText("On");
        } else {
           // textStatus2.setText("Off");
            isOn = true;
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
           // textStatus3.setText("On");
        } else {
            //textStatus3.setText("Off");
            isOn = true;
        }
        if (isOn){
            ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                switch (i) {
                    case 0:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                           // textStatus1.setText("On");
                        } else {
                            //textStatus1.setText("Off");
                        }
                        break;
                    case 1:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                           // textStatus2.setText("On");
                        } else {
                           // textStatus2.setText("Off");
                        }
                        break;
                    case 2:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                           // textStatus3.setText("On");
                        } else {
                            //textStatus3.setText("Off");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void readAllContactFromPhone(){
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor=getContentResolver().query(uri, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        //listContact.clear();

        while (cursor.moveToNext()){
            String nameConTact=ContactsContract.Contacts.DISPLAY_NAME;
            String phoneContact=ContactsContract.CommonDataKinds.Phone.NUMBER;
            int locationName=cursor.getColumnIndex(nameConTact);
            int locationPhone=cursor.getColumnIndex(phoneContact);
            String name=cursor.getString(locationName);
            String phone=cursor.getString(locationPhone);

            Contact contact=new Contact(name,phone);
            listContact.add(contact);
        }

        recyclerContactAdapter.notifyDataSetChanged();

    }
}
