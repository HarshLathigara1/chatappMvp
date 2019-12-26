package lathigara.harsh.chatappmvp.root;

import javax.inject.Singleton;

import dagger.Component;

import lathigara.harsh.chatappmvp.Start.StartActivity;
import lathigara.harsh.chatappmvp.Start.StartModule;
import lathigara.harsh.chatappmvp.alluser.AllUserActivity;
import lathigara.harsh.chatappmvp.alluser.AllUserActivityModule;

import lathigara.harsh.chatappmvp.chat.ChatActivity;
import lathigara.harsh.chatappmvp.chat.ChatActivityModule;
import lathigara.harsh.chatappmvp.login.LoginActivity;
import lathigara.harsh.chatappmvp.login.LoginModule;
import lathigara.harsh.chatappmvp.main.MainActiivtyModule;
import lathigara.harsh.chatappmvp.main.MainActivity;
import lathigara.harsh.chatappmvp.profile.ProfileActivity;
import lathigara.harsh.chatappmvp.profile.ProfileActivityModule;
import lathigara.harsh.chatappmvp.setting.SettingActivity;
import lathigara.harsh.chatappmvp.setting.SettingMoudle;
import lathigara.harsh.chatappmvp.signUp.SignUpModule;
import lathigara.harsh.chatappmvp.signUp.SignupActivity;

@Singleton
@Component(modules = {ApplicationModule.class, SignUpModule.class, StartModule.class, LoginModule.class, MainActiivtyModule.class, SettingMoudle.class, AllUserActivityModule.class, ProfileActivityModule.class, ChatActivityModule.class}) // singling to dagger this will be component
public interface ApplicationComponent {
    // here we are difing that where we want to injcet out things in activity
    // here we will inject our presenter

    void injectTo(SignupActivity target);

    void injectTo(StartActivity startActivity);

    void injectTo(LoginActivity login);

    void injectTo(MainActivity mainActivity);

    void injectTo(SettingActivity settingActivity);

    void injectTo(AllUserActivity allUserActivity);

    void injectTo(ProfileActivity profileActivity);

    void injectTo(ChatActivity chatActivity);







}
