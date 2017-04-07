package vinsoft.com.wavefindyourfriend.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.activity.SignInActivity;
import vinsoft.com.wavefindyourfriend.adapter.RecyclerContactAdapter;
import vinsoft.com.wavefindyourfriend.model.Contact;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 04-Apr-17.
 */

public class ContactFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST =123 ;
    RecyclerView recyclerView;
    LinearLayout ln;
    RecyclerContactAdapter recyclerContactAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Contact> listContact;
    ArrayList<String> listFriend;
    Firebase roof;
  //  TextView txtLocation;
    double latitude,longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);
        checkPermission();

        addControl(view);
        readAllContactFromPhone();
        getLocationChanged();
        setLocationFirebase();

        addFriendFirebase();
        writeFriendFirebase();


        return view;
    }


    private void addFriendFirebase() {
        roof.child("database").child("Person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Person c =  postSnapshot.getValue(Person.class);
                    for(Contact contact:listContact)
                    {
                        if(contact.getPhone().equals(c.getId()))
                        {
                            Map<String, Object> childUpdates = new HashMap<String, Object>() ;
                            childUpdates.put(c.getId(),"true");
                            roof.child("database").child("Friend").child(SignInActivity.person.getId()).updateChildren(childUpdates);
                            break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void writeFriendFirebase() {
        roof.child("database").child("Friend").child(SignInActivity.person.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    listFriend.add(data.getKey());
                }
                ln.setVisibility(View.GONE);

                recyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerContactAdapter = new RecyclerContactAdapter(getContext(),listContact,listFriend);
                recyclerView.setAdapter(recyclerContactAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


    private void addControl(View view) {
        listContact=new ArrayList<Contact>();
        listFriend=new ArrayList<String>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
//        txtLocation= (TextView) view.findViewById(R.id.txtLocation);
        ln = (LinearLayout) view.findViewById(R.id.rlt);

        Firebase.setAndroidContext(view.getContext());
        roof=new Firebase("https://chatandmap.firebaseio.com");
    }

    private void checkPermission() {
        String[] listPermission = new String[] {android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.READ_PHONE_STATE};
        boolean isOn = false;

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            // textStatus1.setText("On");
        } else {
            // textStatus1.setText("Off");
            isOn = true;
        }
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            // textStatus2.setText("On");
        } else {
            // textStatus2.setText("Off");
            isOn = true;
        }
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // textStatus3.setText("On");
        } else {
            //textStatus3.setText("Off");
            isOn = true;
        }
        if (isOn){
            ActivityCompat.requestPermissions((Activity) getContext(), listPermission, MY_PERMISSIONS_REQUEST);
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

//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
//        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

        Cursor cursor=getContext().getContentResolver().query(uri, null, null,null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        //listContact.clear();
        while (cursor.moveToNext()){
            String nameConTact=ContactsContract.Contacts.DISPLAY_NAME;
            String phoneContact=ContactsContract.CommonDataKinds.Phone.NUMBER;
            int locationName=cursor.getColumnIndex(nameConTact);
            int locationPhone=cursor.getColumnIndex(phoneContact);
            String name=cursor.getString(locationName);
            String phone=cursor.getString(locationPhone);

            Contact contact=new Contact(name,phone);
            int condition=1;

            if(listContact.size()==0)
                listContact.add(contact);
            else {
                for (Contact contact1 : listContact)
                    if (contact.getPhone().equals(contact1.getPhone()))
                    {
                        condition=2;
                        break;
                    }
                if(condition==1)
                    listContact.add(contact);
            }
        }
        //   recyclerContactAdapter.notifyDataSetChanged();

    }


    public void getLocationChanged(){
        Log.d("Find MyLocation", "in find_location");
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
               // txtLocation.setText("vi tri: "+latitude+"\n"+longitude);

            }
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                           // txtLocation.setText("vi tri: "+latitude+"\n"+longitude);
                        }
                        public void onProviderDisabled(String provider) {}

                        public void onProviderEnabled(String provider) {}

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {}
                    });
        }
    }

    public void setLocationFirebase(){


        Map<String, Object> childUpdates = new HashMap<String, Object>() ;
        childUpdates.put("latitude",latitude);
        childUpdates.put("longitude",longitude);

        roof.child("database").child("Location").child(SignInActivity.person.getId()).updateChildren(childUpdates);

    }
}
