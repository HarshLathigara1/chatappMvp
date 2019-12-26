package lathigara.harsh.chatappmvp.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class ProfileActivity  extends AppCompatActivity implements ProfileActivityMVP.View {

    private TextView textView;
    private TextView status;
    public static String mCurrentState = "not_friends" ;

    @Inject
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    private FirebaseUser mCurrentUserAuth;

    public static Button sendFriendRequest,declineFriendRequest;

    private DatabaseReference mRootRef;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mFriendRequstDatabase;





    public DatabaseReference databaseReference;


    //public FirebaseDatabase firebaseDatabase;

    @Inject
    ProfileActivityMVP.Presenter presenter;

    private CircleImageView circleImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String user_id = getIntent().getStringExtra("user_id");
        textView = findViewById(R.id.txtProflieName);
        status = findViewById(R.id.txtProfileStatus);
        circleImageView = findViewById(R.id.profileCirecleImageView);
       // mCurrentState = ;
        mCurrentUserAuth = FirebaseAuth.getInstance().getCurrentUser();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mFriendRequstDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_Req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");




        // textView.setText(user_id);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(user_id);
        ((App)getApplication()).getComponent().injectTo(ProfileActivity.this);
        presenter.ShowUsersData(databaseReference,ProfileActivity.this,circleImageView,textView,status, mFriendRequstDatabase,mCurrentUserAuth,user_id,mFriendDatabase);

        sendFriendRequest = findViewById(R.id.btnSendFriendRequest);
        declineFriendRequest = findViewById(R.id.btnDelineFriendRequest);

        sendFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFriendRequest.setEnabled(false);
                // NOT FRIENDS STATE //
                if (mCurrentState.equals("not_friends")){
                   // DatabaseReference newNotificationRef =mRootRef.child("notifications").child(user_id).push();
                  //  String newNotification_id = newNotificationRef.getKey();
                   // HashMap<String,String> notificationData = new HashMap<>();
                   // notificationData.put("from",mCurrentUserAuth.getUid());
                   // notificationData.put("type","request");
                    Map requestMap = new HashMap();
                    requestMap.put("Friend_Req/"+ mCurrentUserAuth.getUid() + "/" + user_id + "/request_type","sent");
                    requestMap.put("Friend_Req/"+ user_id + "/" + mCurrentUserAuth.getUid() + "/request_type","received");
                   // requestMap.put("notifications/" +user_id +"/" + newNotification_id , notificationData);
                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError!= null){
                                Toast.makeText(ProfileActivity.this,"Erroe In Sending Request",Toast.LENGTH_LONG).show();

                            }
                            mCurrentState = "Req_sent";
                            sendFriendRequest.setEnabled(true);
                            sendFriendRequest.setText("Cancel Friend Request");



                        }
                    });


                }

                // CANCEL REQUEST STATE //
                if (mCurrentState.equals("Req_Sent")){
                    mFriendRequstDatabase.child(mCurrentUserAuth.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendRequstDatabase.child(user_id).child(mCurrentUserAuth.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   /* mNotificationDatabase.child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(ProfileActivity.this,"Notification Deleted",Toast.LENGTH_LONG).show();
                                        }
                                    });*/
                                    sendFriendRequest.setEnabled(true);
                                    mCurrentState = "not_friends";
                                    sendFriendRequest.setText("send Friend Request");
                                    declineFriendRequest.setVisibility(View.INVISIBLE);
                                    declineFriendRequest.setEnabled(false);

                                }
                            });
                        }
                    });
                }

                //----Request Recived State-----//
                if (mCurrentState.equals("req_received")){
                    Map friendMap = new HashMap();
                    friendMap.put("Friends/" + mCurrentUserAuth.getUid() + "/" + user_id   + "/state","friends");
                    friendMap.put("Friends/" +   user_id + "/" + mCurrentUserAuth.getUid()  + "/state","friends");

                    friendMap.put("Friend_Req/" + mCurrentUserAuth.getUid() + "/" + user_id ,null );
                    friendMap.put("Friend_Req/" + user_id + "/" + mCurrentUserAuth.getUid() ,null );

                    mRootRef.updateChildren(friendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null){
                                sendFriendRequest.setEnabled(true);
                                mCurrentState = "friends";
                                sendFriendRequest.setText("UnFriend This Person");
                                declineFriendRequest.setVisibility(View.INVISIBLE);
                                declineFriendRequest.setEnabled(false);
                            }else{
                                String error = databaseError.getMessage();
                                Toast.makeText(ProfileActivity.this,error,Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                }

                //UnFriends--//
                if (mCurrentState.equals("friends")){
                    Map unfriend = new HashMap();
                    unfriend.put("Friends/" + mCurrentUserAuth.getUid() + "/" + user_id, null);
                    unfriend.put("Friends/" + user_id + "/" + mCurrentUserAuth.getUid(), null);
                    mRootRef.updateChildren(unfriend, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null){

                                mCurrentState = "not_friends";
                                sendFriendRequest.setText("Send Friend Request");
                                sendFriendRequest.setVisibility(View.INVISIBLE);
                                sendFriendRequest.setEnabled(false);


                            }else{
                                String error = databaseError.getMessage();
                                Toast.makeText(ProfileActivity.this,error,Toast.LENGTH_LONG).show();
                            }


                            sendFriendRequest.setEnabled(true);

                        }

                    });
                    sendFriendRequest.setEnabled(true);

                }





            }
        });






    }


}
