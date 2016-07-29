package com.mralex6204.android.androidchat.contactlist;

import com.mralex6204.android.androidchat.entities.User;

import java.util.List;

/**
 * Created by oavera on 21/07/16.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {
    ContactListRepository contactListRepository;


    public ContactListSessionInteractorImpl() {
        this.contactListRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void signOff() {
        contactListRepository.signOff();
    }

    @Override
    public String getCurrrentUserEmail() {
        return contactListRepository.getCurrentUserEmail();
    }


    @Override
    public void changeConnectionStatus(boolean online) {
        contactListRepository.changeConnectionStatus(online);

    }
}
