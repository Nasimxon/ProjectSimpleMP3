package com.example.loc.projectsimple.commons.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.loc.projectsimple.commons.ApiService;
import com.example.loc.projectsimple.models.Number;

import org.androidannotations.annotations.EBean;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LOC on 8/24/2017.
 */

@EBean
public class ApiManager {
    private Retrofit retrofit;
    private ApiService service;
    private Context context;

    public ApiManager(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://echo.jsontest.com")
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(ApiService.class);
        service = retrofit.create(ApiService.class);
        this.context = context;
    }

    public Observable<Number> getNumbers () {
        if (!hasConnection()) return null;
        return service.getBookmarks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return activeNetwork.isConnected();
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return activeNetwork.isConnected();
        }
        return false;
    }
}
