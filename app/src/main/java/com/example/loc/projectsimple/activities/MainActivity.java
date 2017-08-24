package com.example.loc.projectsimple.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.loc.projectsimple.R;
import com.example.loc.projectsimple.commons.managers.ApiManager;
import com.example.loc.projectsimple.models.Number;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import rx.Observer;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    ApiManager apiManager;

    @ViewById
    TextView tvResult;

    @AfterViews
    void initBookmarkList() {
        apiManager = new ApiManager(this);
        searchAsync();
    }

    @Background
    void searchAsync() {
        Log.d("ssss", "keldi");
        if (apiManager.getNumbers() != null)
            apiManager.getNumbers().subscribe(new Observer<Number>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("ssss", e.toString());
                }

                @Override
                public void onNext(Number number) {
                    updateBookmarks(number);
                }
            });
    }

    @UiThread
    void updateBookmarks(Number numbers) {
        tvResult.setText("" + numbers.getOne() + "\n" + numbers.getKey());
    }
}