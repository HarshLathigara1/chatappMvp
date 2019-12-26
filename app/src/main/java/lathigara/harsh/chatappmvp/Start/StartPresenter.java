package lathigara.harsh.chatappmvp.Start;

import android.content.Context;
import android.content.Intent;

import lathigara.harsh.chatappmvp.login.LoginActivity;
import lathigara.harsh.chatappmvp.signUp.SignupActivity;

public class StartPresenter implements StartActivityMvp.Presenter {

    private StartActivityMvp.View view;
     private Context context;

    StartPresenter( Context activity){
       context = activity;

     }


    @Override
    public void setView(StartActivityMvp.View view) {
        this.view = view;

    }

    @Override
    public void GotoSignUp() {
        Intent intent;
        intent = new Intent(context.getApplicationContext(), SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);



    }

    @Override
    public void GotoLogin() {
        Intent intent;
        intent = new Intent(context.getApplicationContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
