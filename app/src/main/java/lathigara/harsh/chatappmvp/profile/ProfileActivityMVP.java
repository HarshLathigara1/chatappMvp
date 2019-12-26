package lathigara.harsh.chatappmvp.profile;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

interface ProfileActivityMVP {

    interface View{

     }

     interface Modle{

     }


     interface Presenter{
      void ShowUsersData(DatabaseReference databaseReference, ProfileActivity activity, CircleImageView circleImageView,
                         TextView textView, TextView status,
                         DatabaseReference mFriendUserdatabse, FirebaseUser currrentUserAuth,
                         String secondeUserId,DatabaseReference mFriendDatabase);


     }
}
