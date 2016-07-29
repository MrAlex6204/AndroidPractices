package com.mralex6204.android.androidchat.domain;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import com.mralex6204.android.androidchat.entities.User;

/**
 * Created by oavera on 30/06/16.
 */
public class FirebaseHelper {

    /*
    * Se cambio el el tipo de objeto de retorno Firebase por DatabaseReference en cada una de las funciones
    * para poder hacerla compatible con la ultima version de firebase.
    * */

    private DatabaseReference dataReference;
    private final static String SEPARATOR = "__";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
//    private final static String FIREBASE_URL = "https://androidchat-303cb.firebaseio.com";

    private  static  class  SingletonHolder{
        private static final FirebaseHelper INSTANCE  = new FirebaseHelper();
    }

    public static  FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper(){

        this.dataReference = FirebaseDatabase.getInstance().getReference();

    }

    public DatabaseReference getDataReference(){

        return this.dataReference;
    }

    public  String getAuthUserEmail(){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;


        if(user!=null){
            email = user.getEmail();
        }

        return email;
    }

    public static String getFormmatedEmail(String email){
        return  email.replace(".","_");
    }

    public DatabaseReference getUserReferences(String email){
        DatabaseReference userReference = null;

        if(email!=null){
            String emailKey = FirebaseHelper.getFormmatedEmail(email);
            userReference = this.dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }

        return userReference;
    }

    public DatabaseReference getMyUserReference(){

        return  this.getUserReferences(this.getAuthUserEmail());
    }

    public DatabaseReference getContactsReferences(String email){
        return this.getUserReferences(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference(){
        return getContactsReferences(getAuthUserEmail());
    }

    public  DatabaseReference getOneContactReferences(String mainEmail,String childEmail){
               return this.getUserReferences(mainEmail).child(CONTACTS_PATH).child(getFormmatedEmail(childEmail));
    }

    public DatabaseReference getChatReference(String receiver){
        String keySender = getFormmatedEmail(this.getAuthUserEmail());
        String keyReceiver = getFormmatedEmail(receiver);


        String keyChat = keySender+SEPARATOR+keyReceiver;

        if(keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver+SEPARATOR+keySender;
        }

        return this.dataReference.getRoot().child(CHATS_PATH).child(keyChat);

    }

    public void changeUserConnectionStatus(boolean online){
        if(getMyUserReference() !=null ){
            Map<String,Object> update = new HashMap<String,Object>();
            update.put("online",online);
            this.getMyUserReference().updateChildren(update);
            notifyContactsOfConnectionChange(online);
        }
    }

    public void notifyContactsOfConnectionChange(boolean online) {

        notifyContactsOfConnectionChange(online,false);
    }

    public  void signOff(){
        notifyContactsOfConnectionChange(User.OFFLINE,true);

    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReferences(email, myEmail);
                    reference.setValue(online);
                }
                if (signoff){
                    FirebaseAuth.getInstance().signOut();
                }
            }


            public void onCancelled(FirebaseError firebaseError) {

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });

    }

}
