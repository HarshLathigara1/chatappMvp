package lathigara.harsh.chatappmvp.alluser;


import android.content.Context;


import dagger.Module;
import dagger.Provides;

@Module
public class AllUserActivityModule {

    @Provides
    public AllUserMvp.Presenter providesAllUserActivityPresenter() {

        return  new AllUserPresenter();
    }

    @Provides
    public AllUserMvp.Model providesAllUserModel(){
        return new AllUserModel();
    }


}
