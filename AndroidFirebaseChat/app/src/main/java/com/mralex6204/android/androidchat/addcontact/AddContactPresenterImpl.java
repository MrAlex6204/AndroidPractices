package com.mralex6204.android.androidchat.addcontact;

import com.mralex6204.android.androidchat.addcontact.events.AddContactEvent;
import com.mralex6204.android.androidchat.addcontact.ui.AddContactView;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by oavera on 22/07/16.
 */
public class AddContactPresenterImpl implements AddContactPresenter {
    private EventBus eventBus;
    private AddContactView view;
    private AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.addContactInteractor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {

        view.hideInput();
        view.showProgress();
        addContactInteractor.execute(email);

    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {

        if(view!=null){
            view.hideInput();
            view.showProgress();

            if(event.isError()){
                view.contactNotAdded();
            }else{
                view.contactAdded();
            }

        }

    }
}
