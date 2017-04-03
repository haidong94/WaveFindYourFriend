package vinsoft.com.wavefindyourfriend.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
    LinearLayout ln;
    RecyclerContactAdapter recyclerContactAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Contact> listContact;

    ArrayList<String> listFriend;
    Firebase roof;
    TextView txtLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        checkPermission();


        addControl();
        readAllContactFromPhone();
        getCurentLocation();
        addEvent();
    }

    private void addEvent() {
        Firebase.setAndroidContext(this);
        roof=new Firebase("https://chatandmap.firebaseio.com");

        roof.child("database").child("Friend").child(String.valueOf("0986654794")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    listFriend.add(data.getKey());
                }

                ln.setVisibility(View.GONE);

                recyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(ContactFriendActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerContactAdapter = new RecyclerContactAdapter(ContactFriendActivity.this,listContact,listFriend);
                recyclerView.setAdapter(recyclerContactAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void addControl() {
        listContact=new ArrayList<Contact>();
        listFriend=new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        txtLocation= (TextView) findViewById(R.id.txtLocation);
        ln = (LinearLayout) findViewById(R.id.rlt);
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
     //   recyclerContactAdapter.notifyDataSetChanged();

    }


    public void getCurentLocation() {
        //get curren location
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        txtLocation.setText("vi tri"+latitude+","+longitude);
    }

}
