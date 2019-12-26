package lathigara.harsh.chatappmvp.profile;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileActivityModule {

    @Provides
    public ProfileActivityMVP.Presenter providesProfileActivityPresenter(){
        return new  ProfieleActivityPresenter();
    }

  /*  @Provides
    public FirebaseAuth providesFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }*/

  /*@Provides
    public FirebaseDatabase providesFireDatabase(){
      return FirebaseDatabase.getInstance();
  }*/
}
