package com.example.irving.cuponesapp.utils;

import android.content.Context;
import android.media.audiofx.LoudnessEnhancer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Irving on 27/03/2017.
 */
public class Network
{
    public static final String TAG = Network.class.getSimpleName();
    public static  boolean isConnected(Context mContext)
    {
        try
        {
            ConnectivityManager connectivityManager=
                    (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            if(connectivityManager!=null)
            {
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                return  networkInfo!=null && networkInfo.isConnected();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG,ex.getMessage());
        }
        return  false;
    }

}
