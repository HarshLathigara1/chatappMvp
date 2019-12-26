package lathigara.harsh.chatappmvp.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfieleActivityPresenter implements ProfileActivityMVP.Presenter {



    @Override
    public void ShowUsersData(final DatabaseReference databaseReference, final ProfileActivity activity, final CircleImageView circleImageView, final TextView textView, final TextView txtStatus, final DatabaseReference mFriendRequstDatabase, final FirebaseUser mCurrentUserAuth, final String secondeUserId,
                    final   DatabaseReference mFriendDatabase       ) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("image").getValue().toString();
                Glide.with(activity.getApplicationContext()).load(image).into(circleImageView);
                String name = dataSnapshot.child("displayName").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                textView.setText(name);

                txtStatus.setText(status);
                mFriendRequstDatabase.child(mCurrentUserAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Bundle newBundle = new Bundle();
                        // newBundle.putString("user_id", user_id);
                        // RequestFragment requestFragment = new RequestFragment();
                        // requestFragment.setArguments(newBundle);


                        if (dataSnapshot.hasChild(secondeUserId)) {
                            String req_type = dataSnapshot.child(secondeUserId).child("request_type").getValue().toString();
                            if (req_type.equals("received")) {
                                ProfileActivity.mCurrentState = "req_received";
                                ProfileActivity.sendFriendRequest.setText("Accept Friend Request");
                                ProfileActivity.declineFriendRequest.setVisibility(View.VISIBLE);
                                ProfileActivity.declineFriendRequest.setEnabled(true);

                            } else if (req_type.equals("sent")) {
                                ProfileActivity.mCurrentState = "Req_Sent";
                                ProfileActivity.sendFriendRequest.setText("Cancel Friend Request");
                                ProfileActivity.declineFriendRequest.setVisibility(View.INVISIBLE);
                                ProfileActivity.declineFriendRequest.setEnabled(false);
                            }
                        } else {
                            mFriendDatabase.child(mCurrentUserAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(secondeUserId)) {
                                        ProfileActivity.mCurrentState = "friends";
                                        ProfileActivity.sendFriendRequest.setText("Unfriend This Person");
                                        ProfileActivity.declineFriendRequest.setVisibility(View.INVISIBLE);
                                        ProfileActivity.declineFriendRequest.setEnabled(false);
                                        // String friendName = dataSnapshot.child("name").getValue().toString();
                                        // Intent sendToFriends = new Intent(ProfileActivity.this,FriendsFragment.class);
                                        //sendToFriends.putExtra("friendName",friendName);
                                        //startActivity(sendToFriends);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
