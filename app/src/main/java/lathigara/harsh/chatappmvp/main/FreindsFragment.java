package lathigara.harsh.chatappmvp.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.chat.ChatActivity;
import lathigara.harsh.chatappmvp.main.models.Friends;
import lathigara.harsh.chatappmvp.profile.ProfileActivity;

public class FreindsFragment extends Fragment {

    private View view;
    private RecyclerView friendlistRecycler;
    private FirebaseAuth mAuth;

    private DatabaseReference mFriendDatabase;
    private DatabaseReference mUserDatabaseReference;

    private String mCurrentUserId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.friends_fragment,container,false);

        friendlistRecycler = (RecyclerView)view.findViewById(R.id.userRecyclerView);
        mAuth = FirebaseAuth.getInstance();

        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrentUserId);
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("USERS");


        friendlistRecycler.setHasFixedSize(true);
        friendlistRecycler.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }





    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Friends,FriendsViewHolder> friendsViewHolderAdapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(
                Friends.class,
                R.layout.user_single,
                FriendsViewHolder.class,
                mFriendDatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, Friends model, int position) {

                // viewHolder.setName(model.getName());
                // viewHolder.setStatus(model.getStatus());
                // viewHolder.setImage(model.getImage(),getContext());
                //viewHolder.setOnline(model.getOnline());
                final String list_user_id = getRef(position).getKey();
                mUserDatabaseReference.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String name = dataSnapshot.child("displayName").getValue().toString();
                        String status = dataSnapshot.child("status").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();

                        viewHolder.setName(name);
                        viewHolder.setStatus(status);
                        viewHolder.setImage(image,getContext());
                        if (dataSnapshot.hasChild("online")){
                            String online =  dataSnapshot.child("online").getValue().toString();
                           // viewHolder.setUserOnline(online);

                        }

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final CharSequence options[] = new CharSequence[]{"open Profile","Send Message"};

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select Options");

                              //  builder.setIcon(R.drawable.ic_send);
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0){
                                            // builder.setIcon(R.drawable.ic_friends);

                                            Intent newIntent = new Intent(getContext(), ProfileActivity.class);
                                            newIntent.putExtra("user_id",list_user_id);
                                            startActivity(newIntent);

                                        }
                                        if (which == 1){
                                            // builder.setIcon(R.drawable.ic_send);
                                            Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                            chatIntent.putExtra("user_id",list_user_id);
                                            chatIntent.putExtra("user_name",name);

                                            startActivity(chatIntent);
                                        }

                                    }
                                });
                                builder.show();
                            }
                        });






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




               /*// viewHolder.setName(model.getName());
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());*/


            }
        };
        friendlistRecycler.setAdapter(friendsViewHolderAdapter);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        View mView;



        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }
        public void setName(String name){
            TextView userNameView = (TextView)mView.findViewById(R.id.txtName);
            userNameView.setText(name);
        }

        public void setStatus(String status){
            TextView userStatus = (TextView)mView.findViewById(R.id.setting_status);
            userStatus.setText(status);
        }

        public void setImage(String image, Context ctx){
            CircleImageView userImage = (CircleImageView)mView.findViewById(R.id.user_single_imageView);
           // Picasso.with(ctx).load(image).placeholder(R.drawable.logo).into(userImage);
            Glide.with(ctx).load(image).into(userImage);
        }


      /*  public void setUserOnline(String online_status){
            ImageView userOnlineView = (ImageView)mView.findViewById(R.id.imgOnlineDot);
            if (online_status.equals("true")){
                userOnlineView.setVisibility(View.VISIBLE);
            }else{
                userOnlineView.setVisibility(View.INVISIBLE);
            }

        }*/




    }


}


