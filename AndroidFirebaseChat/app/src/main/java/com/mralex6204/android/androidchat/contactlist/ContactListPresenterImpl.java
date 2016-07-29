package com.mralex6204.android.androidchat.contactlist;

import com.mralex6204.android.androidchat.contactlist.events.ContactListEvent;
import com.mralex6204.android.androidchat.contactlist.ui.ContactListView;
import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by oavera on 21/07/16.
 */
public class ContactListPresenterImpl implements ContactListPresenter {
    private EventBus eventBus;
    private ContactListView contactListView;
    private ContactListInteractor contactListInteractor;
    private ContactListSessionInteractor contactListSessionInteractor;


    public ContactListPresenterImpl(ContactListView contactListView) {
        this.contactListView = contactListView;

        eventBus = GreenRobotEventBus.getInstance();
        this.contactListSessionInteractor = new ContactListSessionInteractorImpl();
        this.contactListInteractor = new ContactListInteractorImpl();

    }

    @Override
    public void onPause() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unsubscribe();

    }

    @Override
    public void onResume() {
        contactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribe();

    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        contactListInteractor.destroyListener();
        contactListView = null;
    }

    @Override
    public void signOff() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unsubscribe();
        contactListInteractor.destroyListener();
        contactListSessionInteractor.signOff();

    }

    @Override
    public String getCurrentUserEmail() {

        return contactListSessionInteractor.getCurrrentUserEmail();
    }


    @Override
    public void removedContact(String email) {
        contactListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {

        User user = event.getUser();

        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                    onContactAdded(user);
                break;

            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;

            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;

        }

    }

    private  void onContactAdded(User user){

        if(this.contactListView !=null){
            contactListView.onContactAdded(user);
        }

    }

    private  void onContactChanged(User user){

        if(this.contactListView !=null){
            contactListView.onContactChanged(user);
        }

    }
    private  void onContactRemoved(User user){

        if(this.contactListView !=null){
            contactListView.onContactRemoved(user);
        }

    }
}
