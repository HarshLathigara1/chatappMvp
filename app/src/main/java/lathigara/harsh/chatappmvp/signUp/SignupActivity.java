package lathigara.harsh.chatappmvp.signUp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class SignupActivity extends AppCompatActivity implements SignUpActivityMvp.View {

    private EditText edtUserName, edtPassword,displayName;
    private Button btnGetAuthentication;




    @Inject
    SignUpActivityMvp.Presenter presenter;


    // now we have method inside presenter and we want in so we are creating instance for pressenter
    // that way we are depended on presenter interface


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        // add injection point for Dagger
        // dagger will provide required things

        ((App) getApplication()).getComponent().injectTo(this);

        displayName  = findViewById(R.id.displayNme);
        edtUserName = findViewById(R.id.userName_editText_SignUp);
        edtPassword = findViewById(R.id.password_editText_signUp);
        btnGetAuthentication = findViewById(R.id.btnGetAuthenticated);
        btnGetAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.RegisterButtonClicked(displayName.getText().toString(),edtUserName.getText().toString(), edtPassword.getText().toString());


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);

    }

    @Override
    public void AuthenticationDone() {
        Toast.makeText(this, "Authentication Done", Toast.LENGTH_LONG).show();

    }

    @Override
    public void errorWhileAuthenricating() {
        Toast.makeText(this, "Authentication error", Toast.LENGTH_LONG).show();

    }

    @Override
    public void pleaseAddUserNameOrPassword() {
        Toast.makeText(this, "plesae Add UserName Or Password ", Toast.LENGTH_LONG).show();

    }
}
