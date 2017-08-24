package com.example.loc.projectsimple.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.loc.projectsimple.R;
import com.example.loc.projectsimple.commons.managers.ApiManager;
import com.example.loc.projectsimple.models.Number;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import rx.Observer;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Bean
    ApiManager apiManager;

    @ViewById
    FlowingDrawer drawerlayout;

    @AfterViews
    void initBookmarkList() {
        drawerlayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        drawerlayout.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });
//        apiManager = new ApiManager(this);
        searchAsync();
    }

    @Background
    void searchAsync() {
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
//        tvResult.setText("" + numbers.getOne() + "\n" + numbers.getKey());
    }
}