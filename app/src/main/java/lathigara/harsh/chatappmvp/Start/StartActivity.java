package lathigara.harsh.chatappmvp.Start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.NoActivity;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.main.MainActivity;
import lathigara.harsh.chatappmvp.root.App;

public class StartActivity extends AppCompatActivity implements StartActivityMvp.View {
    private Button btnLogin,btnSignUp;
    private ImageButton btnForgetPassword;
    FirebaseAuth auth;

    @Inject
     StartActivityMvp.Presenter presenter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        auth  =FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user",user.getUid());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }


        ((App)getApplication()).getComponent().injectTo(StartActivity.this);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnForgetPassword = findViewById(R.id.imgPhoneLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.GotoSignUp();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.GotoLogin();

            }
        });
    }

    @Override
    public void moveToSignUp() {
        Toast.makeText(this,"Mover To signup",Toast.LENGTH_LONG).show();


    }

    @Override
    public void moveToSignin() {
        Toast.makeText(this,"Mover To signIn",Toast.LENGTH_LONG).show();

    }

    @Override
    public void moveToForgerPassword() {
        Toast.makeText(this,"Mover To ForgetPassword",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);


    }
}
