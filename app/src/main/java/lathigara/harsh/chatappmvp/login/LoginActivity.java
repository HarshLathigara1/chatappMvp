package lathigara.harsh.chatappmvp.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    private EditText edtUserNameLogin,edtPasswordlogin;
    private Button btnLogin;







    @Inject
     LoginActivityMVP.Presenter presenter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((App)getApplication()).getComponent().injectTo(this);

       btnLogin = findViewById(R.id.btnLoginButton);
        edtUserNameLogin = findViewById(R.id.edtUserNameLogin);
        edtPasswordlogin = findViewById(R.id.edtPasswordLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginButtonClicked( edtUserNameLogin.getText().toString(),edtPasswordlogin.getText().toString());

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void loginDone() {
        Toast.makeText(this,"Login Done",Toast.LENGTH_LONG).show();

    }

    @Override
    public void loginError() {
        Toast.makeText(this,"Login Error",Toast.LENGTH_LONG).show();

    }

    @Override
    public void pleaseAddUserNameAndPassword() {
        Toast.makeText(this,"Please Add UserName And Password",Toast.LENGTH_LONG).show();

    }
}
