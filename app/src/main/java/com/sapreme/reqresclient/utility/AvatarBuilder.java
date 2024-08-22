package com.sapreme.reqresclient.utility;

import android.net.Uri;

public class AvatarBuilder {

    private String name;
    private String background = "random";
    private int size = 128;
    private boolean rounded = true;
    private boolean bold = true;
    private boolean uppercase = true;

    private AvatarBuilder(){

    }

    public static AvatarBuilder withName(String name){
        AvatarBuilder builder = new AvatarBuilder();
        builder.name = name;
        return builder;
    }

    public String buildUrl(){
        return "https://ui-avatars.com/api/?name=" + Uri.encode(name) +
                "&background=" + background +
                "&size=" + size +
                "&rounded=" + rounded +
                "&bold=" + bold +
                "&uppercase=" + uppercase;
    }
}
