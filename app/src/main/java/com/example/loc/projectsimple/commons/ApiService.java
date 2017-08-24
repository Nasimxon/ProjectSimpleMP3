package com.example.loc.projectsimple.commons;

import com.example.loc.projectsimple.models.Number;

import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by LOC on 8/23/2017.
 */
public interface ApiService {
    @GET("key/value/one/two")
    Observable<Number> getBookmarks();
}
