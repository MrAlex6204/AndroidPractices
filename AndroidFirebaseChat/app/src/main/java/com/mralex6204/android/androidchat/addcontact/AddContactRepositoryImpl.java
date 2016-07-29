package com.mralex6204.android.androidchat.addcontact;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mralex6204.android.androidchat.addcontact.events.AddContactEvent;
import com.mralex6204.android.androidchat.domain.FirebaseHelper;
import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by oavera on 22/07/16.
 */
public class AddContactRepositoryImpl implements AddContactRepository {
    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void addContact(final String email) {
        if (!email.isEmpty()) {
            final String key = FirebaseHelper.getFormmatedEmail(email);
            DatabaseReference userReference = helper.getUserReferences(email);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Obtener la informacion del user
                    User user = dataSnapshot.getValue(User.class);

                    if (user != null) {
                        //Agregar a mi referencia de usuarios al contacto agregado
                        DatabaseReference myContactReference = helper.getMyContactsReference();
                        myContactReference.child(key).setValue(user.isOnline());

                        //Agregar Mi user email a la lista del otro usuario
                        String myCurrentUserKey = FirebaseHelper.getFormmatedEmail(helper.getAuthUserEmail());
                        DatabaseReference reverseContactReference = helper.getContactsReferences(key);
                        reverseContactReference.child(myCurrentUserKey).setValue(User.ONLINE);

                        postSuccess();
                    } else {
                        postError();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    postError();
                }
            });

        }else{
            postError();
        }


    }

    private void postError() {
        post(true);
    }

    private void postSuccess() {
        post(false);
    }

    private void post(boolean isError) {
        AddContactEvent event = new AddContactEvent();
        event.setError(isError);
        eventBus.post(event);
    }
}
