package lathigara.harsh.chatappmvp.signUp;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.main.MainActivity;

public class SignUpActivityPresenter  implements SignUpActivityMvp.Presenter{
    /* private  FirebaseAuth mAuth;*/


    // now we are adding out view to presenter so create instance
    private Context context;
    @Inject
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;




    public SignUpActivityPresenter(FirebaseAuth mAuth,Context context ) {

        this.mAuth = mAuth;
        this.context = context;
    }


   private SignUpActivityMvp.View view;

    @Override
    public void setView(SignUpActivityMvp.View view) {
        this.view = view;

    }

    @Override
    public void RegisterButtonClicked(final String displayName, final String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)||TextUtils.isEmpty(displayName)){

            view.pleaseAddUserNameOrPassword();

        }else {

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (!task.isSuccessful()){
                           view.errorWhileAuthenricating();
                       }else {
                           view.AuthenticationDone();
                           firebaseUser = mAuth.getCurrentUser();
                           String uid = firebaseUser.getUid();
                           databaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
                           HashMap<String,String> userMap = new HashMap<>();
                           userMap.put("displayName",displayName);
                           userMap.put("status","Hi There i m Using ChatApp");
                           userMap.put("image","Default_Image");
                           userMap.put("thumb_image","default");

                           databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                                       context.startActivity(intent);
                                   }

                               }
                           });


                       }

                    }

                });


        }



    }
}
