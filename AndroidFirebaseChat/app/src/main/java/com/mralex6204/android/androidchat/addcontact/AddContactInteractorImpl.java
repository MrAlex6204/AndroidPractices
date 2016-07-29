package com.mralex6204.android.androidchat.addcontact;

/**
 * Created by oavera on 22/07/16.
 */
public class AddContactInteractorImpl implements AddContactInteractor {
    private  AddContactRepository addContactRepository;

    public AddContactInteractorImpl() {
        this.addContactRepository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        this.addContactRepository.addContact(email);
    }
}
