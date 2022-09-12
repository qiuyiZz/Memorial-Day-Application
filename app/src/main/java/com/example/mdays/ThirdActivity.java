package com.example.mdays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onClick(View view) {
        finish();
    }
}