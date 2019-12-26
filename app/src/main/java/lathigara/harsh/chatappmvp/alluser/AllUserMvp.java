package lathigara.harsh.chatappmvp.alluser;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

public interface AllUserMvp {

    interface View{

    }

    interface Model{
        AllUser getUser();

    }

    interface Presenter{
        void ShowUser(AllUserActivity activity, RecyclerView recyclerView, DatabaseReference databaseReference);



    }
}
