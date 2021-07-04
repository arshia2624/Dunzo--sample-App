package com.example.dunzodemoapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.ablanco.zoomy.Zoomy;
import com.ablanco.zoomy.ZoomyConfig;
import com.bumptech.glide.Glide;
import com.example.dunzodemoapp.MainActivity;
import com.example.dunzodemoapp.R;
import com.example.dunzodemoapp.models.PhotosList;

public class Utils {

    @BindingAdapter(value = {"data"}, requireAll = false)
    public static void loadImage(ImageView img, PhotosList.Photo photo) {
        String imageURL = "https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + "_m.jpg";


        Zoomy.Builder builder = new Zoomy.Builder(((MainActivity) img.getContext())).target(img);
        ZoomyConfig config = new ZoomyConfig();
        config.setZoomAnimationEnabled(false);
        config.setImmersiveModeEnabled(false);
        Zoomy.setDefaultConfig(config);
        builder.register();


        try {
            Glide.with(img.getContext())
                    .load(imageURL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
