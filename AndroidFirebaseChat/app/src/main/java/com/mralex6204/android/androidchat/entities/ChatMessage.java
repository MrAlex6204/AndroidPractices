package com.mralex6204.android.androidchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by oavera on 27/07/16.
 */

//Como la propiedad sentByMe no se va guardar en Fire base se
//agrega esta notacion para que el parser de jackson lo ignore
@JsonIgnoreProperties({"sentByMe"})
public class ChatMessage {
    private String msg;
    private String sender;
    private boolean sentByMe;

    public ChatMessage(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }


    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if(obj instanceof  ChatMessage){
            ChatMessage msg = (ChatMessage)obj;
            equal = this.sender.equals(msg.getSender()) && this.msg.equals(msg.getMsg()) && this.sentByMe == msg.sentByMe ;
        }

        return equal;
    }
}
