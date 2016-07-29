package com.mralex6204.android.androidchat.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mralex6204.android.androidchat.R;
import com.mralex6204.android.androidchat.addcontact.ui.AddContactFragment;
import com.mralex6204.android.androidchat.chat.ui.ChatActivity;
import com.mralex6204.android.androidchat.contactlist.ContactListPresenter;
import com.mralex6204.android.androidchat.contactlist.ContactListPresenterImpl;
import com.mralex6204.android.androidchat.contactlist.ui.adapters.ContactListAdapter;
import com.mralex6204.android.androidchat.contactlist.ui.adapters.OnItemClickListener;
import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.GlideImageLoader;
import com.mralex6204.android.androidchat.lib.ImageLoader;
import com.mralex6204.android.androidchat.login.ui.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;

    private ContactListAdapter contactListAdapter;
    private ContactListPresenter contactListPresenter;
    private boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        contactListPresenter = new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();

        setupToolbar();

        setupAdapter();
        setupRecyclerView();
    }

    @Override
    protected void onDestroy() {
        contactListPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        isBusy = false;
        contactListPresenter.onResume();
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        if(!isBusy){
            contactListPresenter.onPause();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Crear menu
        this.getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Al hacer click sobre cualquier opcion del menu
        if (item.getItemId() == R.id.action_logout) {
            contactListPresenter.signOff();
            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }



    private void setupToolbar() {
        toolbar.setTitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactListAdapter);
    }

    private void setupAdapter() {
        ImageLoader loader = new GlideImageLoader(this.getApplicationContext());
        contactListAdapter = new ContactListAdapter(new ArrayList<User>(), loader, this);
    }


    @OnClick(R.id.fab)
    public void addContact() {
        new AddContactFragment().show(this.getSupportFragmentManager(), this.getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        contactListAdapter.add(user);
    }


    @Override
    public void onContactChanged(User user) {
        contactListAdapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        contactListAdapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {

        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra(ChatActivity.EMAIL_KEY, user.getEmail());
        i.putExtra(ChatActivity.ONLINE_KEY, user.isOnline());

        isBusy = true;

        startActivity(i);

    }

    @Override
    public void onItemLongClick(User user) {

        contactListPresenter.removedContact(user.getEmail());

    }
}
