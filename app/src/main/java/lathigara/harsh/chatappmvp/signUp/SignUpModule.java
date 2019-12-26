package lathigara.harsh.chatappmvp.signUp;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;





import dagger.Module;
import dagger.Provides;

@Module
public class SignUpModule {

    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public SignUpActivityMvp.Presenter providessignUpActivityPresenter(FirebaseAuth auth, Context context){
        return new SignUpActivityPresenter(auth,context);
    }


    @Provides
    public FirebaseDatabase firebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }

    @Provides
    public FirebaseUser providesFirebaseUser(FirebaseUser firebaseUser){
        return firebaseUser;
    }

    @Provides
    public DatabaseReference providesDatabaseReference(DatabaseReference databaseReference){
        return databaseReference;
    }








}
