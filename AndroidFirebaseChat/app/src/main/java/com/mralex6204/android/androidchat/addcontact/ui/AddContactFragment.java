package com.mralex6204.android.androidchat.addcontact.ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mralex6204.android.androidchat.R;
import com.mralex6204.android.androidchat.addcontact.AddContactPresenter;
import com.mralex6204.android.androidchat.addcontact.AddContactPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {
    @BindView(R.id.editTxtEmail)
    EditText editTxtEmail;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    AddContactPresenter addContactPresenter;

    public AddContactFragment() {
        // Required empty public constructor
        addContactPresenter = new AddContactPresenterImpl(this);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity())
                .setTitle(R.string.addcontact_message_title)
                .setPositiveButton(R.string.addcontact_message_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.addcontact_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        // Inflate the layout for this fragment
        View view = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, view);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    /******
     * ADD*CONTACT*VIEW*METHODS
     ******/
    @Override
    public void showInput() {
        editTxtEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTxtEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(this.getActivity(),R.string.addcontact_message_contactadded,Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        showInput();
        hideProgress();
        editTxtEmail.setError(this.getString(R.string.addcontact_error_message));
        //dismiss();

    }

    /*************END*OF*ADD*CONTACT*VIEW*****/

    /*****Dialog onShowListener***************/
    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)this.getDialog();
        if(dialog!=null){
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negaviteButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContactPresenter.addContact(editTxtEmail.getText().toString());
                }
            });

            negaviteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        addContactPresenter.onShow();

    }
    /*****************************************/

    @Override
    public void onDestroy() {
        addContactPresenter.onDestroy();
        super.onDestroy();
    }
}
