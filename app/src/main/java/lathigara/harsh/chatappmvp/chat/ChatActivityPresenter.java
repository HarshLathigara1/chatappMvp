package lathigara.harsh.chatappmvp.chat;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lathigara.harsh.chatappmvp.chat.ChatActivity.TOTAL_ITEMS_LOAD;
import static lathigara.harsh.chatappmvp.chat.ChatActivity.mCurrentPage;

public class ChatActivityPresenter implements ChatctivityMVP.Presenter {
    @Override
    public void setChatConversation(final DatabaseReference mRootRef, final String mchatUser, final String mCurrentuser) {

        mRootRef.child("Chat").child(mCurrentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(mchatUser)) {
                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timeStamp", ServerValue.TIMESTAMP);

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrentuser + "/" + mchatUser, chatAddMap);
                    chatUserMap.put("Chat/" + mchatUser + "/" + mCurrentuser, chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if (databaseError != null) {
                                Log.d("CHAT", databaseError.getMessage().toString());
                            }
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
    public void sendMessageButtonClicked(String currentUserId, String mChatUser, DatabaseReference mRootRef, EditText edtChat) {

        String message = edtChat.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            String currentUserRef = "messages/" + currentUserId + "/" + mChatUser;
            String chatUserRef = "messages/" + mChatUser + "/" + currentUserId;

            DatabaseReference userMessagePush = mRootRef.child("messages").child(currentUserId).child(mChatUser).push();
            String pushId = userMessagePush.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", currentUserId);

            Map messageUserMap = new HashMap();
            messageUserMap.put(currentUserRef + "/" + pushId, messageMap);
            messageUserMap.put(chatUserRef + "/" + pushId, messageMap);
            edtChat.setText("");

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("CHAT", databaseError.getMessage().toString());
                    }

                }
            });


        }


    }

    @Override
    public void loadMessages(DatabaseReference mRootRef, String currentUserId, String mChatUser, final List<Messages> messageNewList, final MessagesAdapter messageAdapter, final RecyclerView messagesList) {
        DatabaseReference messageRef = mRootRef.child("messages").child(currentUserId).child(mChatUser);
        Query messageQuery  = messageRef.limitToLast(mCurrentPage *TOTAL_ITEMS_LOAD);
        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages message = dataSnapshot.getValue(Messages.class);
                /*itemPostion++;
                if (itemPostion ==1){
                    String mMessageKey = dataSnapshot.getKey();
                    lastKey = mMessageKey;
                    mPrevKey = mMessageKey;
                }*/
                messageNewList.add(message);
                messageAdapter.notifyDataSetChanged();
               // messagesList.scrollToPosition(messageNewList.size() -1);
              //  swipeRefreshLayouts.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   /* @Override
    public void loadMoreMessages( DatabaseReference mRootRef, String currentUserId, String mChatUser,final List<Messages> messageNewList,final MessageAdapter messageAdapter,final RecyclerView messagesList,final SwipeRefreshLayout swipeRefreshLayouts ) {

        DatabaseReference messageRef = mRootRef.child("messages").child(currentUserId).child(mChatUser);
        Query messageQuery  = messageRef.limitToLast(mCurrentPage *TOTAL_ITEMS_LOAD);
        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages message = dataSnapshot.getValue(Messages.class);
                itemPostion++;
                if (itemPostion ==1){
                    String mMessageKey = dataSnapshot.getKey();
                    lastKey = mMessageKey;
                    mPrevKey = mMessageKey;
                }
                messageNewList.add(message);
                messageAdapter.notifyDataSetChanged();
                messagesList.scrollToPosition(messageNewList.size() -1);
                swipeRefreshLayouts.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

 */



}
