package lathigara.harsh.chatappmvp.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class ChatActivity extends AppCompatActivity implements ChatctivityMVP.View {
    private String mchatUser, mCurrentuser;
    private Toolbar toolbar;
    private TextView txtName, txtSeen;
    private DatabaseReference mRootRef;
    private EditText edtChatMessageField;
    private ImageButton btnSendMessageButton;
    private RecyclerView chatUserList;
    private final List<Messages>messagesList  = new ArrayList<>();
    private MessagesAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;

    // load more messages
    public static  final int TOTAL_ITEMS_LOAD = 10;
    public static  int mCurrentPage = 1;
    public static int itemPostion = 0;
    public static String lastKey = "";
    public static String mPrevKey = "";

    //


    @Inject
    ChatctivityMVP.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        ((App)getApplication()).getComponent().injectTo(this);
        mCurrentuser = mAuth.getCurrentUser().getUid();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mchatUser = getIntent().getStringExtra("user_id");
        String mchatUserName = getIntent().getStringExtra("user_name");
        edtChatMessageField = findViewById(R.id.edtChat);
        btnSendMessageButton = findViewById(R.id.btnSendMessage);
        ActionBar actionBar = getSupportActionBar();
        chatUserList = findViewById(R.id.messages_List);
        linearLayoutManager = new LinearLayoutManager(this);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbarView = inflater.inflate(R.layout.chat_custom_bar, null);
        actionBar.setCustomView(actionbarView);
        txtName = findViewById(R.id.txtName);
        txtName.setText(mchatUserName);
        txtSeen = findViewById(R.id.txtSeen);
        mRootRef.child("USERS").child(mchatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String online = dataSnapshot.child("online").getValue().toString();
                if (online.equals("true")) {
                    txtSeen.setText("online");
                } else {

                    GetTimeAgo getTimeAgo = new GetTimeAgo();

                    long lastSeen = Long.parseLong(online);
                    String lastSeenTime = getTimeAgo.getTimeAgo(lastSeen, getApplicationContext());
                    txtSeen.setText(lastSeenTime);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        messageAdapter = new MessagesAdapter(messagesList);

        chatUserList.setHasFixedSize(true);
        chatUserList.setLayoutManager(linearLayoutManager);
        chatUserList.setAdapter(messageAdapter);
        // it will create chat convo for you
        presenter.setChatConversation(mRootRef,mchatUser,mCurrentuser);
        // for loading messages//
        presenter.loadMessages(mRootRef,mCurrentuser,mchatUser,messagesList,messageAdapter,chatUserList);
        btnSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendMessageButtonClicked(mCurrentuser,mchatUser,mRootRef,edtChatMessageField);
            }
        });



    }
}
