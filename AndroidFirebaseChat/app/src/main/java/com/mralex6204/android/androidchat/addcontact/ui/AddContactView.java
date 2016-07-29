package com.mralex6204.android.androidchat.addcontact.ui;

/**
 * Created by oavera on 22/07/16.
 */
public interface AddContactView {
    /*Aqui se exponen los metodos de la vista*/

    void showInput();
    void hideInput();

    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}

