package lathigara.harsh.chatappmvp.chat;

import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface ChatctivityMVP {

    interface View{

    }


    interface Model{

    }

    interface Presenter{

        void setChatConversation( DatabaseReference mRootRef,String mCurrentuser,  String mchatUser );

        void sendMessageButtonClicked(String currentUserId, String mChatUser, DatabaseReference mRootRef, EditText edtChat);

      //  void loadMoreMessages(DatabaseReference mRootRef, String currentUserId, String mChatUser, final List<Messages> messageNewList, final MessageAdapter messageAdapter, final RecyclerView messagesList, final SwipeRefreshLayout swipeRefreshLayouts);
        void loadMessages(DatabaseReference mRootRef, String currentUserId, String mChatUser, List<Messages> messageNewList, MessagesAdapter messageAdapter, RecyclerView messagesList);
    }
}
