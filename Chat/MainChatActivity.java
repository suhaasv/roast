package com.example.android.roast.Activities.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.roast.Activities.Chat.ChatActivity;
import com.example.android.roast.Activities.Chat.ChatRoom;
import com.example.android.roast.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by svnktsh3 on 4/23/17.
 * Activity that is initially displayed; holds the overall list of chatrooms, and allows users
 * to create their own
 */

public class MainChatActivity extends AppCompatActivity {
    private TextView mMessageTextView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mMessageRef = database.getReference("chatmessage");
    DatabaseReference mChatRooms = database.getReference("allchats");
    DatabaseReference mChatRoomRef = database.getReference("chatrooms");
    private EditText mNewRoomEditText;
    private RecyclerView mChatRoomRecyclerView;
    private FirebaseRecyclerAdapter<ChatRoom, ChatRoomViewHolder> mAdapter;

    /**
     * Uses a recycler view to display the list of available chat rooms
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        mNewRoomEditText = (EditText) findViewById(R.id.newRoomText);
        mChatRoomRecyclerView = (RecyclerView) findViewById(R.id.chatRoomRecyclerView);
        mChatRoomRecyclerView.setHasFixedSize(true);
        mChatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMessageTextView = (TextView) findViewById(R.id.messageView);
        mMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessageTextView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // nothing
            }
        });
    }

    /**
     * If you click on a new room, add a new chat room to the list
     *
     * @param view
     */
    public void newRoomClick(View view) {
        String newRoomName = mNewRoomEditText.getText().toString();
        if (newRoomName.length() == 0) {
            return;
        }
        String key = mChatRooms.push().getKey();
        ChatRoom chatRoomDesc = new ChatRoom(newRoomName, key);
        mChatRoomRef.push().setValue(chatRoomDesc);
        mChatRooms.push().setValue(newRoomName);
        mNewRoomEditText.setText("");
    }

    /**
     * onStart method, passes the data from the adapter through an intent to the ChatActivity class
     */
    @Override
    protected void onStart() {
        super.onStart();

        mAdapter = new FirebaseRecyclerAdapter<ChatRoom, ChatRoomViewHolder>(ChatRoom.class,
                R.layout.my_simple_list_item_1, ChatRoomViewHolder.class, mChatRoomRef) {
            @Override
            protected void populateViewHolder(ChatRoomViewHolder viewHolder,
                                              final ChatRoom model, int position) {
                viewHolder.mTextView.setText(model.name);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity

                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                        intent.putExtra(ChatActivity.EXTRA_CHAT_KEY, model.key);
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected ChatRoom parseSnapshot(DataSnapshot snapshot) {
                Log.d("mDirectoryRef", "parseSnapshot: " + snapshot.toString());
                return super.parseSnapshot(snapshot);
            }
        };

        mChatRoomRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public static class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ChatRoomViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}



