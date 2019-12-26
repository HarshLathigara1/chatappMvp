package lathigara.harsh.chatappmvp.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.main.MainActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginPresenter implements LoginActivityMVP.Presenter {

   private LoginActivityMVP.View view;
    private FirebaseAuth auth;
    private Context context;

    public LoginPresenter(FirebaseAuth auth,Context context){
        this.auth = auth;
        this.context = context;


    }




    @Override
    public void loginButtonClicked(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    view.loginDone();
                    Log.d(TAG, "onComplete: login done");
                    Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    view.loginError();
                }

            }
        });
    }

    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;

    }
}
