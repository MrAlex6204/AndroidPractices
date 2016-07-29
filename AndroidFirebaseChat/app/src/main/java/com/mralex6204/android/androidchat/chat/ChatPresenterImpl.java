package com.mralex6204.android.androidchat.chat;

import com.mralex6204.android.androidchat.chat.events.ChatEvent;
import com.mralex6204.android.androidchat.chat.ui.ChatView;
import com.mralex6204.android.androidchat.entities.ChatMessage;
import com.mralex6204.android.androidchat.entities.User;
import com.mralex6204.android.androidchat.lib.EventBus;
import com.mralex6204.android.androidchat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by oavera on 27/07/16.
 */
public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView chatView;
    private ChatSessionInteractor chatSessionInteractor;
    private ChatInteractor chatInteractor;


    public ChatPresenterImpl(ChatView view) {
        this.chatView = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();


    }

    @Override
    public void onPause() {
        chatInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        chatView = null;

    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Subscribe
    @Override
    public void onEventMainThread(ChatEvent event) {

        if(chatView!=null){
            chatView.onMessageReceived(event.getMessage());
        }

    }

}
