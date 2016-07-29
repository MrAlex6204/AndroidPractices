package com.mralex6204.android.androidchat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mralex6204.android.androidchat.R;
import com.mralex6204.android.androidchat.chat.*;
import com.mralex6204.android.androidchat.chat.ui.adapters.ChatAdapter;
import com.mralex6204.android.androidchat.contactlist.*;
import com.mralex6204.android.androidchat.contactlist.ui.*;
import com.mralex6204.android.androidchat.domain.AvatarHelper;
import com.mralex6204.android.androidchat.entities.*;
import com.mralex6204.android.androidchat.lib.*;


import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @BindView(R.id.editTxtMessage)
    EditText editTxtMessage;
    @BindView(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    public  final static  String EMAIL_KEY = "email";
    public  final static  String ONLINE_KEY = "online";

    private ChatPresenter chatPresenter;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        chatPresenter = new ChatPresenterImpl(this);


        chatPresenter.onCreate();

        setupToolbar(getIntent());

    }

    private void setupAdapter() {
        /*
        ChatMessage chat1 = new ChatMessage(),chat2 = new ChatMessage();

        chat1.setMsg("Hola");
        chat2.setMsg("Como estas ?");
        chat1.setSentByMe(true);
        chat2.setSentByMe(false);

        chatAdapter = new ChatAdapter(this, Arrays.asList(new ChatMessage[]{chat1,chat2}) );
        */

        chatAdapter = new ChatAdapter(this, new ArrayList<ChatMessage>());

    }


    private void setupRecyclerView() {

        messageRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(chatAdapter);

    }


    private void setupToolbar(Intent i) {

        String recipient = i.getStringExtra(EMAIL_KEY);
        boolean online = i.getBooleanExtra(ONLINE_KEY,false);



        //Indicamos el usuario con el que se inicio el chat.
        chatPresenter.setChatRecipient(recipient);

        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());

        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));

        setSupportActionBar(toolbar);
        //===>Habilitar el boton de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onResume() {
        chatPresenter.onResume();

        super.onResume();
    }

    @Override
    protected void onPause() {
        chatPresenter.onPause();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        chatPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        chatAdapter.add(msg);
        messageRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        chatPresenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }

}
