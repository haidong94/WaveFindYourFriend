package vinsoft.com.wavefindyourfriend.common;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by DONG on 06-Apr-17.
 */

public class ConnectFirebase {

    public static Firebase getConnect(Context context){
        Firebase.setAndroidContext(context);
        Firebase roof=new Firebase("https://chatandmap.firebaseio.com");
        return roof;
    }
}
