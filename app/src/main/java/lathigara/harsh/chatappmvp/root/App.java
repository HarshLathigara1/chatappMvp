package lathigara.harsh.chatappmvp.root;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import lathigara.harsh.chatappmvp.Start.StartModule;
import lathigara.harsh.chatappmvp.alluser.AllUserActivityModule;
import lathigara.harsh.chatappmvp.login.LoginModule;
import lathigara.harsh.chatappmvp.main.MainActiivtyModule;

import lathigara.harsh.chatappmvp.setting.SettingMoudle;
import lathigara.harsh.chatappmvp.signUp.SignUpModule;

public class App extends Application {
    // here we will say that what this component is this
    // means we are saying here that we will user dagger dependency injection in our
    // projects
    private ApplicationComponent applicationComponent;
    private FirebaseAuth mAuth;
    private Context context;

    private DatabaseReference mUserDatabase;




    @Override
    public void onCreate() {
        super.onCreate();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
      /*  if (user !=null){
            Intent intent = new Intent(context.getApplicationContext(), NoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }*/

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            mUserDatabase  = FirebaseDatabase.getInstance().getReference().child("USERS").child(mAuth.getCurrentUser().getUid());
            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null){
                        mUserDatabase.child("online").onDisconnect().setValue(ServerValue.TIMESTAMP);
                        // mUserDatabase.child("lastSeen").setValue(ServerValue.TIMESTAMP);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .startModule(new StartModule())
                .loginModule(new LoginModule())
                .allUserActivityModule(new AllUserActivityModule())
                // for the what to bind
                .signUpModule(new SignUpModule())
                .mainActiivtyModule(new MainActiivtyModule())
                .settingMoudle(new SettingMoudle())

                .build();
    }


    public ApplicationComponent getComponent() {
        return applicationComponent;
    }




}
