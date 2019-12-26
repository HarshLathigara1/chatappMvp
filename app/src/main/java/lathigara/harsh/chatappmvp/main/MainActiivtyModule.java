package lathigara.harsh.chatappmvp.main;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;


import dagger.Module;
import dagger.Provides;

@Module
public class MainActiivtyModule {

    @Provides
    public MainActivityMVP.Presenter providesMainActivityPresenter(){
        return new MainActivityPresenter();
    }

    /*@Provides
    public ButterKnife providesButterKnife(ButterKnife butterKnife){
        return butterKnife;
    }*/



}
