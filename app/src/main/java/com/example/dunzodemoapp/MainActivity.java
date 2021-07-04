package com.example.dunzodemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dunzodemoapp.databinding.ActivityMainBinding;
import com.example.dunzodemoapp.networkcalls.APITags;
import com.example.dunzodemoapp.utils.Utils;
import com.example.dunzodemoapp.viewodel.MainActivityViewModel;
import com.example.dunzodemoapp.viewodel.MyViewModelFactory;
import com.example.dunzodemoapp.views.adapters.ImageRvAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel;
    ImageRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        startInitialization();
    }

    private void startInitialization() {
        activityMainBinding.setQuery("");
        activityMainBinding.edtSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    initViewModels(activityMainBinding.getQuery());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activityMainBinding.edtSearchQuery.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


    }


    private void initToolBar() {
        getSupportActionBar().setTitle("Search Image");
    }

    private void initViewModels(String searchQuery) {
        if (!Utils.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connectivity", Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            if (searchQuery.trim().equals("")) {
                Toast.makeText(this, "Please enter search keywords", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("query", searchQuery);
            editor.apply();

            if (adapter != null) {
                viewModel.clear();
                viewModel = null;
                adapter = null;
                activityMainBinding.rvImageList.setAdapter(adapter);
            }
            viewModel = ViewModelProviders.of(this, new MyViewModelFactory(searchQuery, this)).get(MainActivityViewModel.class);
            activityMainBinding.rvImageList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
            ;
            adapter = new ImageRvAdapter();
            activityMainBinding.rvImageList.setAdapter(adapter);
            viewModel.getListLiveData().observe(this, adapter::submitList);
            viewModel.getProgressLoadStatus().observe(this, status -> {
                if (Objects.requireNonNull(status).equalsIgnoreCase(APITags.LOADING)) {
                    activityMainBinding.setLoading(true);
                } else if (status.equalsIgnoreCase(APITags.LOADED)) {
                    activityMainBinding.setLoading(false);
                }
            });
        }


    }
}