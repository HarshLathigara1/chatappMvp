package lathigara.harsh.chatappmvp.login;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

   /*@Provides
    FirebaseAuth providesFirebaseAuth(){
        return  FirebaseAuth.getInstance();
    }
*/
    @Provides
    LoginActivityMVP.Presenter providesLoginPresenter(FirebaseAuth auth, Context context){
        return new  LoginPresenter(auth,context);
    }


}
