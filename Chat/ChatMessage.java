package com.example.android.roast.Activities.Chat;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by svnktsh3 on 4/28/17.
 */

@IgnoreExtraProperties
public class ChatMessage {
    public String message;
    public String author;

    // re-wrote to avoid non-default constructor
  public ChatMessage() { // if you include a non-default constructor,
    }                      // this is necessary for de-serialization
//
   public ChatMessage(String message, String author) {
       this.message = message;
       this.author = author;

    }

}
