package com.mralex6204.android.androidchat.contactlist;

import android.provider.SyncStateContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mralex6204.android.androidchat.contactlist.events.ContactListEvent;
import com.mralex6204.android.androidchat.domain.FirebaseHelper;
import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oavera on 21/07/16.
 */
public class ContactListRepositoryImpl implements ContactListRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReferences(currentUserEmail,email).removeValue();
        helper.getOneContactReferences(email,getCurrentUserEmail()).removeValue();
    }

    @Override
    public void destroyListener() {
        contactEventListener = null;

    }

    @Override
    public void subscribeToContactListEvents() {

        if(contactEventListener ==null){

            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot,ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };





        }

        helper.getMyContactsReference().addChildEventListener(contactEventListener);

    }

    @Override
    public void unsubscribeToContactListEvents() {
        if(contactEventListener!=null){
            helper.getMyContactsReference().removeEventListener(contactEventListener);
        }
    }

    private void handleContact(DataSnapshot dataSnapshot, int type) {
        User user = new User();
        String email = dataSnapshot.getKey();
        boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();

        email = email.replace("_",".");

        user.setEmail(email);
        user.setOnline(online);

        post(type,user);



    }

    private void post(int type, User user) {

        ContactListEvent event = new ContactListEvent();

        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}
