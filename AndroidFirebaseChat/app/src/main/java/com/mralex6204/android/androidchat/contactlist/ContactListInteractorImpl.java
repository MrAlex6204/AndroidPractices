package com.mralex6204.android.androidchat.contactlist;

/**
 * Created by oavera on 21/07/16.
 */
public class ContactListInteractorImpl implements ContactListInteractor {
    ContactListRepository contactListRepository;


    public ContactListInteractorImpl() {
        contactListRepository = new ContactListRepositoryImpl();

    }

    @Override
    public void subscribe() {
        contactListRepository.subscribeToContactListEvents();

    }

    @Override
    public void unsubscribe() {
        contactListRepository.unsubscribeToContactListEvents();

    }

    @Override
    public void destroyListener() {
        contactListRepository.destroyListener();

    }

    @Override
    public void removeContact(String email) {
        contactListRepository.removeContact(email);
    }
}
