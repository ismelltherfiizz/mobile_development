package com.example.a1lab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;


public class NetworkWatcher extends BroadcastReceiver {
    private View view;

    public NetworkWatcher(View view) {
        super();
        this.view = view;
    }

    private boolean isOnline(Context context) {
        boolean isOnline;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities = connectivityManager.
                    getNetworkCapabilities(connectivityManager.getActiveNetwork());
            isOnline = networkCapabilities != null
                    && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            isOnline = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return isOnline;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (!isOnline(context)) {
                Snackbar.make(view, R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}