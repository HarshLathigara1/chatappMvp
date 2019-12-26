package lathigara.harsh.chatappmvp.alluser;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;


import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class AllUserActivity  extends AppCompatActivity implements AllUserMvp.View {

    @Inject
    public AllUserMvp.Presenter presenter;

    RecyclerView recyclerView;

    private  DatabaseReference mUserDatabase;



   // public static View view ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);
        recyclerView = findViewById(R.id.allUserRecyclerView);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("USERS");

      //  ButterKnife.bind(this);
        ((App)getApplication()).getComponent().injectTo(this);
        presenter.ShowUser(AllUserActivity.this,recyclerView,mUserDatabase);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
