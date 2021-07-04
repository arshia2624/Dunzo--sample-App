package com.example.dunzodemoapp.viewodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dunzodemoapp.MainActivity;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private String Query;
    private MainActivity mainActivity;


    public MyViewModelFactory(String Query, MainActivity mainActivity) {
        this.Query = Query;
        this.mainActivity=mainActivity;
        new MainActivityViewModel(Query,mainActivity);
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(Query,mainActivity);
    }

}