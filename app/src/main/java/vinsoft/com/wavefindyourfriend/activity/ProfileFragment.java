package vinsoft.com.wavefindyourfriend.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.theartofdev.edmodo.cropper.CropImageView;

import vinsoft.com.wavefindyourfriend.R;

/**
 * Created by DONG on 29-Mar-17.
 */

public class ProfileFragment extends DialogFragment {
    ImageButton btn_turn,btn_check;
    Uri uril;

    ImageProfile imageProfile;

    public interface ImageProfile{
        public void SendImage(Bitmap uri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialogframent_profile,container,false);
        final CropImageView cropImageView= (CropImageView) view.findViewById(R.id.cropImageView);

        btn_turn= (ImageButton) view.findViewById(R.id.btn_turn);
        btn_check= (ImageButton) view.findViewById(R.id.btn_check);


        if (getArguments() != null){
            String uri = getArguments().getString("Image",null);
            if (uri != null){
               uril=Uri.parse(uri);
                cropImageView.setImageUriAsync(uril);
            }
        }

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProfile.SendImage(cropImageView.getCroppedImage(100,100));
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        imageProfile= (ImageProfile) context;
    }

}
