package lathigara.harsh.chatappmvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import javax.inject.Inject;



import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.Start.StartActivity;
import lathigara.harsh.chatappmvp.alluser.AllUserActivity;
import lathigara.harsh.chatappmvp.root.App;
import lathigara.harsh.chatappmvp.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.view {


    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    String userId;


   // @BindView(R.id.rec)

    @Inject
    MainActivityMVP.Presenter presenter;

    @Override
    protected void onStart() {
        auth = FirebaseAuth.getInstance();
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null){
            sendToStart();
           // Log.d(TAG, "onStart: user Added To NoActivity " + user.getEmail());


        }else
        {
            Toast.makeText(this,"userAdded" +user.getEmail(),Toast.LENGTH_LONG ).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App)getApplication()).getComponent().injectTo(this);
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
       userId = firebaseUser.getUid();
        getSupportActionBar().setTitle("DoIt");
       // ButterKnife.bind(this);


    }

    BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.chats:
                    selectedFragment = new ChatFragment();
                    break;

                case R.id.request:
                    selectedFragment = new RequestFragment();
                    break;

                case R.id.friends:
                    selectedFragment = new FreindsFragment();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

            presenter.setView(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }else if (item.getItemId() == R.id.account_settings){
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            intent.putExtra("user",userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (item.getItemId() == R.id.allUsers){
            Intent intent = new Intent(getApplicationContext(), AllUserActivity.class);
            intent.putExtra("user",userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }


        return true;
    }

    private void sendToStart() {

        Intent sIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(sIntent);
        finish();

    }
}
