package vinsoft.com.wavefindyourfriend.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.adapter.ZonePhoneAdapter;
import vinsoft.com.wavefindyourfriend.model.Person;
import vinsoft.com.wavefindyourfriend.model.ZonePhone;
import vinsoft.com.wavefindyourfriend.myinterface.ISetZonePhone;

import static android.widget.Toast.makeText;

public class SignInActivity extends AppCompatActivity {
    EditText edt_number_phone,edt_pass;
    CheckBox cb_ShowPassword;
    TextView tv_register;
    Button btn_continue;
    Firebase roof;
    MyLogin mListener;
    public static Person person=new Person();
    TextInputLayout layout_phone,layout_pass;
    List<ZonePhone> zonePhoneList;
    ZonePhoneAdapter adapter;
    EditText edtZonePhone;
    RecyclerView rlvZonePhone;
     String phone;
     String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();

        mListener=new MyLogin();

        Firebase.setAndroidContext(this);
        roof=new Firebase("https://chatandmap.firebaseio.com");

        addControl();
        setParams();
        setEventParam();
        addEvent();
    }

    private void addControl() {

        edtZonePhone= (EditText) findViewById(R.id.edt_zone_phone);
        rlvZonePhone= (RecyclerView) findViewById(R.id.rlvZonePhone);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rlvZonePhone.setLayoutManager(layoutManager);

        layout_phone= (TextInputLayout) findViewById(R.id.phone);
        layout_pass= (TextInputLayout) findViewById(R.id.pass);
        edt_number_phone= (EditText) findViewById(R.id.edt_number_phone);
        edt_pass= (EditText) findViewById(R.id.edt_pass);
        cb_ShowPassword= (CheckBox) findViewById(R.id.cb_ShowPassword);
        btn_continue= (Button) findViewById(R.id.btn_continue);
        tv_register= (TextView) findViewById(R.id.tv_register);

        cb_ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    edt_pass.setTransformationMethod(null);
                else
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void addEvent() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRegister();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                rlvZonePhone.setVisibility(View.GONE);
            }
        });
    }

    public void setParams(){
        zonePhoneList=new ArrayList<>();

        zonePhoneList.add(new ZonePhone("Afghanistan","93"));
        zonePhoneList.add(new ZonePhone("Argentina","54"));
        zonePhoneList.add(new ZonePhone("Australia","61"));
        zonePhoneList.add(new ZonePhone("Belarus","375"));
        zonePhoneList.add(new ZonePhone("Belgium","32"));
        zonePhoneList.add(new ZonePhone("Canada","1"));
        zonePhoneList.add(new ZonePhone("China","86"));
        zonePhoneList.add(new ZonePhone("Croatia","385"));
        zonePhoneList.add(new ZonePhone("Cuba","53"));
        zonePhoneList.add(new ZonePhone("Czech Republic","420"));
        zonePhoneList.add(new ZonePhone("Denmark","45"));
        zonePhoneList.add(new ZonePhone("France","33"));
        zonePhoneList.add(new ZonePhone("Germany","49"));
        zonePhoneList.add(new ZonePhone("Indonesia","62"));
        zonePhoneList.add(new ZonePhone("Italy","39"));
        zonePhoneList.add(new ZonePhone("Japan","81"));
        zonePhoneList.add(new ZonePhone("Laos","856"));
        zonePhoneList.add(new ZonePhone("Myanmar","95"));
        zonePhoneList.add(new ZonePhone("Slovenia","386"));
        zonePhoneList.add(new ZonePhone("South Korea","82"));
        zonePhoneList.add(new ZonePhone("Vietnam","84"));

        adapter = new ZonePhoneAdapter(zonePhoneList, new ISetZonePhone() {
            @Override
            public void onSetZonePhone(ZonePhone zonePhone) {
                edtZonePhone.setText(zonePhone.getCountryName()+" +"+zonePhone.getCountryPhone());
                rlvZonePhone.setVisibility(View.GONE);
                btn_continue.setVisibility(View.VISIBLE);
            }
        });

        rlvZonePhone.setAdapter(adapter);
    }

    public void  setEventParam(){
        edtZonePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlvZonePhone.setVisibility(View.VISIBLE);
                btn_continue.setVisibility(View.GONE);
            }
        });
    }

    private void login() {
        phone = edt_number_phone.getText().toString();
        pass = edt_pass.getText().toString();
        if(TextUtils.isEmpty(phone))
            layout_phone.setError(getResources().getString(R.string.EmptyPhone));
        else
            layout_phone.setErrorEnabled(false);
        if(TextUtils.isEmpty(pass))
            layout_pass.setError(getResources().getString(R.string.EmptyPass));
        else
            layout_pass.setErrorEnabled(false);

        if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass))
            roof.child("database").child("Person").addValueEventListener(mListener);
    }

    private void dialogRegister() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_account, null);
        final EditText ed_phone = (EditText) alertLayout.findViewById(R.id.ed_phone);
        final EditText ed_password = (EditText) alertLayout.findViewById(R.id.ed_password);
        final EditText ed_name = (EditText) alertLayout.findViewById(R.id.ed_name);


        final RadioGroup radioGroup = (RadioGroup) alertLayout.findViewById(R.id.radioGroup);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Register");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeText(getBaseContext(), getResources().getString(R.string.CancelClick), Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Tạo tài khoản", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                roof.child("database").child("Person").addValueEventListener(new ValueEventListener() {
                    int condition=0;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String phone =ed_phone.getText().toString();
                        String pass = ed_password.getText().toString();
                        String name= ed_name.getText().toString();

                        //getSex
                        int id=radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(id);
                        int radioId = radioGroup.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                        String sex = btn.getText().toString();
                        condition++;//condition=1
                        if(phone!=null) {
                            person.setId(phone);
                            person.setName(name);
                            person.setPass(pass);
                            person.setSex(sex);
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                Person c =  postSnapshot.getValue(Person.class);

                                if(c.getId()==person.getId()&&condition==1){
                                    condition++;//condition=2
                                    Toast.makeText(SignInActivity.this, getResources().getString(R.string.PhoneUsed), Toast.LENGTH_SHORT).show();
                                    break;
                                }

                            }
                        }
                        else {
                            condition++;//condition=2
                            Toast.makeText(SignInActivity.this, "SDT khong hop le", Toast.LENGTH_SHORT).show();
                        }

                        if(condition==1) {
                           // String s=roof.child("database").child("Person").push().getKey();
                            roof.child("database").child("Person").child(phone).setValue(person);

                            Toast.makeText(SignInActivity.this, getResources().getString(R.string.LoginSuccesfull), Toast.LENGTH_SHORT).show();
                            condition++;//condition=2
                            Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);

                            finish();

                        }


                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    class MyLogin implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int condition=0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Person c =  postSnapshot.getValue(Person.class);
                if(c.getId().equals(phone)&&c.getPass().equals(pass)){
                    person=c;
                    condition=1;
                    break;
                }
            }
            if(condition==1){
                Toast.makeText(SignInActivity.this,getResources().getString(R.string.LoginSuccesfull),Toast.LENGTH_SHORT).show();
                if(person.getImage()!=null)
                {
                    Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(SignInActivity.this,ChooseAvatarActivity.class);
                    startActivity(intent);
                    finish();
                }

            }


            else
                Toast.makeText(SignInActivity.this,getResources().getString(R.string.phoneIncorrect ),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        roof.child("database").child("Person").removeEventListener(mListener);
    }
}
