package com.azanini.thenotelist.repo.ws;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

 class Connection {

    private static RequestQueue queue;

     static RequestQueue getInstance(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

}
