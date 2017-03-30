package vinsoft.com.wavefindyourfriend.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.storage.StorageManager;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.model.Person;

/**
 * Created by DONG on 29-Mar-17.
 */

public class ImageActivity extends AppCompatActivity implements ProfileFragment.ImageProfile {
    CircleImageView circleImageView;
    TextView tv_name;
    Button btn_choose,btn_skip;
    FirebaseStorage mStoreage;
    StorageReference storageRef;
    private static int PICK_IMAGE_GALLERY = 1;
    private static int PICK_IMAGE_CAMERA = 0;
    Uri selectedImage;
    DatabaseReference mData;
    Firebase roof;
    ProgressDialog progressDialog;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mStoreage=FirebaseStorage.getInstance();
        storageRef = mStoreage.getReference();
        progressDialog=new ProgressDialog(this);

        Firebase.setAndroidContext(this);
        roof=new Firebase("https://chatandmap.firebaseio.com");
        Intent intent=getIntent();
        key= intent.getStringExtra("key");
        addControl();
        addEvent();

    }

    private void addControl() {

        circleImageView= (CircleImageView) findViewById(R.id.profile_image);
        tv_name= (TextView) findViewById(R.id.tv_name);
        
        btn_choose= (Button) findViewById(R.id.btn_choose);
        btn_skip= (Button) findViewById(R.id.btn_skip);
    }
    private void addEvent() {
       // tv_name.setText(SignInActivity.person.getName()+"");

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ImageActivity.this,ContactFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void uploadImage() {
        progressDialog.setMessage("Uploading avatar...");
        progressDialog.show();
        final Calendar calendar = Calendar.getInstance();
        StorageReference stoRefe = storageRef.child("image"+calendar.getTimeInMillis()+".png");
        circleImageView.setDrawingCacheEnabled(true);
        circleImageView.buildDrawingCache();
        Bitmap bitmap = circleImageView.getDrawingCache();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outStream);
        byte[] array = outStream.toByteArray();

        UploadTask uploadTask = stoRefe.putBytes(array);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ImageActivity.this,"Upload Failed"+e.toString(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                Map<String, Object> childUpdates = new HashMap<String, Object>() ;
                childUpdates.put("avatar",downloadUrl.toString());
                if(key!=null)
                    roof.child("database").child("Person").child(key).updateChildren(childUpdates);

                progressDialog.dismiss();
            }
        });
    }


    public void selectImage(){
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    selectedImage = data.getData();
                    break;
                case 1:
                    selectedImage = data.getData();
                    break;
            }
        }

        Bundle bundle=new Bundle();
        bundle.putString("Image", String.valueOf(selectedImage));
        FragmentManager fm=getSupportFragmentManager();
        ProfileFragment profileFragment=new ProfileFragment();
        profileFragment.setArguments(bundle);
        profileFragment.show(fm,"profile");



    }

//    private Uri getCaptureImageOutputUri() {
//        Uri outputFileUri = null;
//        File getImage = this.getExternalCacheDir();
//        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new  File(getImage.getPath(), "pickImageResult.jpeg"));
//        }
//        return outputFileUri;
//    }

    @Override
    public void SendImage(Bitmap uri) {
        circleImageView.setImageBitmap(uri);
    }


//    public boolean hasPermission() {
//        int res = 0;
//        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
//        for (String perms : permission) {
//            res = checkCallingOrSelfPermission(perms);
//            if (!(res == PackageManager.PERMISSION_GRANTED)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void repuestPerms() {
//        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(permission, 123);
//        }
//    }

}
