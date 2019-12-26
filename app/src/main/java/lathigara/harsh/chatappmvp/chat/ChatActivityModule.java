package lathigara.harsh.chatappmvp.chat;


import dagger.Module;
import dagger.Provides;

@Module
public class ChatActivityModule {

    @Provides
    public ChatctivityMVP.Presenter providesChatctivityPresenter(){
        return new ChatActivityPresenter();
    }
}
