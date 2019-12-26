package lathigara.harsh.chatappmvp.Start;


import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class StartModule {

    @Provides
    public StartActivityMvp.Presenter providesPresenter(Context activity){
        return new StartPresenter(activity);
    }



}
