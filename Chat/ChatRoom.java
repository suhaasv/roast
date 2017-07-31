package com.example.android.roast.Activities.Chat;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by svnktsh3 on 4/28/17.
 * ChatRoom object class
 */
@IgnoreExtraProperties
public class ChatRoom {
    public String name;
    public String key;

    public ChatRoom(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public ChatRoom() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatRoomDesc.class),
        // because we have a non-default constructor as well.
    }
}
