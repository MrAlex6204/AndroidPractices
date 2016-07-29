package com.mralex6204.android.androidchat.lib;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by oavera on 20/07/16.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager requestManager;


    public GlideImageLoader(Context context) {
        this.requestManager = Glide.with(context);

    }

    @Override
    public void load(ImageView imgAvatar, String url) {
        requestManager.load(url).into(imgAvatar);
    }
}
