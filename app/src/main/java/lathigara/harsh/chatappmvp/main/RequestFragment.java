package lathigara.harsh.chatappmvp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.main.models.Request;
import lathigara.harsh.chatappmvp.profile.ProfileActivity;

public class RequestFragment extends Fragment {

    private RecyclerView reqList;
    private DatabaseReference friend_Req;
    private DatabaseReference userDatabesRef;
    private DatabaseReference mConvDatabase;
    private FirebaseAuth mAuth;
    private String currenUser_id;
    private View mMainView;
    private String currentState;
    private TextView txtfrndreqst;

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.request_fragment,container,false);

        reqList = view.findViewById(R.id.requestRecyclerView);

        txtfrndreqst = view.findViewById(R.id.txtfrndrqst);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
          //  txtfrndreqst.setVisibility(View.VISIBLE);

            reqList.setEnabled(false);
        }else if (mAuth.getCurrentUser() != null){
            currenUser_id = mAuth.getCurrentUser().getUid();


            friend_Req = FirebaseDatabase.getInstance().getReference().child("Friend_Req").child(currenUser_id);
        }



        userDatabesRef = FirebaseDatabase.getInstance().getReference().child("USERS");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        reqList.setHasFixedSize(true);
        reqList.setLayoutManager(linearLayoutManager);

       return view;
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            reqList.setEnabled(false);
        }else {
            Query reqQuery = friend_Req.orderByChild("request_type").equalTo("received");
            FirebaseRecyclerAdapter<Request, RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Request, RequestViewHolder>(
                    Request.class,
                    R.layout.request_layout,
                    RequestViewHolder.class,
                    reqQuery

            ) {
                @Override
                protected void populateViewHolder(final RequestViewHolder viewHolder, Request model, int position) {
                    final String list_user_id = getRef(position).getKey();
                    //Query lastReqst= friend_Req.child(list_user_id).limitToLast(1);
                    userDatabesRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("displayName").getValue().toString();
                            String image = dataSnapshot.child("image").getValue().toString();
                            viewHolder.setName(name);
                            viewHolder.setImage(image, getContext());
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent newIntent = new Intent(getContext(), ProfileActivity.class);
                                    newIntent.putExtra("user_id", list_user_id);
                                    startActivity(newIntent);

                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            };
            reqList.setAdapter(firebaseRecyclerAdapter);
        }

    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        View mView;



        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }

        private void setName(String name){
            TextView userName = (TextView)mView.findViewById(R.id.txtReqName);
            userName.setText(name);

        }

        private void setImage(String image, Context ctx){
            CircleImageView reqImage = (CircleImageView)mView.findViewById(R.id.requestProfile);
           // Picasso.with(ctx).load(image).placeholder(R.drawable.logo).into(reqImage);

            Glide.with(ctx).load(image).into(reqImage);

        }



    }
}
