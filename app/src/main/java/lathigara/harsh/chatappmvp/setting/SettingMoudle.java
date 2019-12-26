package lathigara.harsh.chatappmvp.setting;


import android.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.hdodenhof.circleimageview.CircleImageView;


@Module
public class SettingMoudle {

    @Provides
    public SettingActivityMVP.Presenter providesSettingPresenter(){
        return new SettingActivityPresenter();
    }

    @Provides
    public CircleImageView providesCirculerImageView(CircleImageView circleImageView){
        return  circleImageView;
    }

  /*  @Provides
    public Picasso providesPicasso(Picasso picasso){
        return picasso;
    }*/

    @Provides
    public Glide providesGlide(Glide glide){
        return glide;

    }

   /* @Provides
    public FirebaseDatabase providesFirebaseDatabase(FirebaseDatabase firebaseDatabase){
        return firebaseDatabase;
    }*/

    @Provides
    public StorageReference providesStorageReference(StorageReference storageReference){
        return  storageReference;
    }

    @Provides
    public FirebaseStorage providesFirebaseStorage(FirebaseStorage firebaseStorage){
        return firebaseStorage;
    }

    @Provides
    public DatabaseReference providesDatabasReference(DatabaseReference databaseReference){
        return databaseReference;
    }
    @Provides
    AlertDialog.Builder providesAlertDialogBuilder(AlertDialog.Builder alertBuilder){
        return alertBuilder;
    }
}
