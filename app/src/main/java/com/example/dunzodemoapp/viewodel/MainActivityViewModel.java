package com.example.dunzodemoapp.viewodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.dunzodemoapp.MainActivity;
import com.example.dunzodemoapp.models.PhotosList;
import com.example.dunzodemoapp.repository.ImageDataSource;
import com.example.dunzodemoapp.repository.ImageDataSourceFactory;

public class MainActivityViewModel extends ViewModel {
    private ImageDataSourceFactory imageDataSourceFactory;
    private LiveData<PagedList<PhotosList.Photo>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();

    public MainActivityViewModel(String query, MainActivity mainActivity) {
        imageDataSourceFactory = new ImageDataSourceFactory(query,mainActivity);
        listLiveData = null;
        initializePaging();
    }

    private void initializePaging() {
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(50).build();

        listLiveData = new LivePagedListBuilder<>(imageDataSourceFactory, pagedListConfig).build();
        progressLoadStatus = Transformations.switchMap(imageDataSourceFactory.getMutableLiveData(), ImageDataSource::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<PhotosList.Photo>> getListLiveData() {
        return listLiveData;
    }


    public void clear() {
        imageDataSourceFactory.clear();
    }

}
