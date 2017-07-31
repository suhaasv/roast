package com.example.android.roast.Activities.Chat;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.roast.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by svnktsh3 on 4/29/17.
 * This class deals with displaying the different chats and chatrooms, as well as the messages
 * associated with them
 */


public class ChatActivity extends AppCompatActivity {
    public static final String EXTRA_CHAT_KEY = "CHAT_KEY";

    private RecyclerView mRecyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ChatRef;
    private EditText mNewPostText;
    private String userName;
    private FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder> mChatViewAdapter;

    /**
     * onCreate method, loads the recyclerView and displays the text needed
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String chatKey = getIntent().getStringExtra(EXTRA_CHAT_KEY);
        ChatRef = database.getReference(chatKey);

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewPostText = (EditText) findViewById(R.id.newPostText);
        Button username = (Button) findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

                final EditText newUser = new EditText(v.getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(newUser);

                // set dialog message
                alertDialogBuilder.setCancelable(true).setPositiveButton
                        ("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String user = newUser.getText().toString();
                                userName = user;

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

        });
    }

    /**
     * onStart method, initializes the adapter and populates our recyclerview
     * with views
     */
    @Override
    protected void onStart() {
        super.onStart();

        mChatViewAdapter = new FirebaseRecyclerAdapter<ChatMessage,
                ChatMessageViewHolder>(ChatMessage.class,
                R.layout.chat_message, ChatMessageViewHolder.class, ChatRef) {
            @Override
            protected void populateViewHolder(ChatMessageViewHolder viewHolder,
                                              ChatMessage model, int position) {
                viewHolder.bind(model);
            }
        };
        mRecyclerView.setAdapter(mChatViewAdapter);
    }

    /**
     * This class handles the sending of message data to firebase, using the data collection methods
     * within the app
     */
    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;
        private final TextView mMessageTextView;

        public ChatMessageViewHolder(View itemView) {
            super(itemView);
            mMessageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }

        public void bind(ChatMessage chatMessage) {
            mMessageTextView.setText(chatMessage.message);
            mNameTextView.setText(chatMessage.author);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mChatViewAdapter != null) {
            mChatViewAdapter.cleanup();
        }
    }

    /**
     * deals with pushing data to firebase, namely in the message form with an author and message
     * @param view
     */
    public void newPostClick(View view) {
        String newPostString = mNewPostText.getText().toString();
        mNewPostText.setText("");

        if (ChatRef != null) {
//
            final ChatMessage chatMessage = new ChatMessage();
            chatMessage.message = newPostString;
            if (userName == null)
                chatMessage.author = "newUser";
            else
                chatMessage.author = userName;

            ChatRef.push().setValue(chatMessage);
        } else {
            Toast.makeText(view.getContext(), "Database not available", Toast.LENGTH_SHORT).show();
        }
    }
}