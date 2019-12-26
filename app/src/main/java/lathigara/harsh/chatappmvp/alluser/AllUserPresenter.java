package lathigara.harsh.chatappmvp.alluser;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;


import de.hdodenhof.circleimageview.CircleImageView;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.profile.ProfileActivity;

public class AllUserPresenter implements AllUserMvp.Presenter {

   // Context context;
   // public static View mView;
    /*public AllUserPresenter(Context context){
        this.context = context;

    }*/



    @Inject
    public AllUserMvp.Model model;









    @Override
    public void ShowUser(final AllUserActivity activity,RecyclerView recyclerView,DatabaseReference databaseReference) {



        FirebaseRecyclerAdapter<AllUser,UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AllUser, UserViewHolder>(
                AllUser.class,
                R.layout.user_single,
                UserViewHolder.class,
                databaseReference


        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, AllUser model, int position) {
                viewHolder.setName(model.getDisplayName(),activity.getApplicationContext());
                viewHolder.setStatus(model.getStatus(),activity.getApplicationContext());
                viewHolder.setImage(model.getImage(),activity.getApplicationContext());
                final String user_id = getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profileIntent = new Intent(activity.getApplicationContext(), ProfileActivity.class);
                        profileIntent.putExtra("user_id",user_id);
                        profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.getApplicationContext().startActivity(profileIntent);

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);



    }



    public static class UserViewHolder extends RecyclerView.ViewHolder{


       // @Inject
       // ButterKnife butterKnife;
       // @BindView(R.id.txtName)
       // TextView mUserName;

       // @BindView(R.id.txtStatus)
       // TextView mUserStatus;
      // Context context1;

        View mView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setName(String name,Context context){
            //TextView userName =new((AllUserActivity)context) TextView(context);
          //  View view = View.inflate(context,R.id.txtName,null);
            TextView userName = mView.findViewById(R.id.txtName);

          //  View view = View.inflate(context, R.layout.user_single, null);

           // userName = view.findViewById(R.id.txtName);

            userName.setText(name);








        }

        public void setStatus(String status,Context ctx){
            // mUserStatus = new TextView(ctx);


            TextView mUserStatus = mView.findViewById(R.id.setting_status);

            mUserStatus.findViewById(R.id.setting_status);
            mUserStatus.setText(status);
        }

        public void setImage(String image,Context context){
            CircleImageView userImage = (CircleImageView)mView.findViewById(R.id.user_single_imageView);
           // Picasso.with(ctx).load(image).placeholder(R.drawable.logo).into(userImage);
            Glide.with(context).load(image).placeholder(R.drawable.ic_launcher_background).into(userImage);

        }
    }
}
