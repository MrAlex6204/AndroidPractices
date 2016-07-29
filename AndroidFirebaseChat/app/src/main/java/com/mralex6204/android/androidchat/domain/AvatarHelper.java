package com.mralex6204.android.androidchat.domain;

import android.util.Log;

import java.util.Random;

/**
 * Created by oavera on 20/07/16.
 */
public class AvatarHelper {
    private  static Random rnd = new Random();

    public static String getAvatarUrl(String email) {
        int picNumber = rnd.nextInt(60) +  email.length();
        boolean isMen = rnd.nextInt(1)+1 == 1 ? true : false;
        String url = "";

        if(isMen){
         url = "https://randomuser.me/api/portraits/med/men/" + picNumber + ".jpg";
        }else{
            url = "https://randomuser.me/api/portraits/med/women/" + picNumber + ".jpg";
        }

        Log.d("avatar Url :",url);
        return url;
    }

}
