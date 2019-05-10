package com.example.ankitmah.myapplication.myLogin;

import android.content.Context;
import android.content.Intent;

/**
 * Created by cybage on 08-May-19.
 */
class ActivityUtil {
    private Context context;

    ActivityUtil(MyLoginActivity myLoginActivity) {
        context = myLoginActivity;
    }

    void startEmptyActivity() {
        context.startActivity(new Intent(context, EmptyActivity.class));
    }
}
