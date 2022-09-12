package com.example.mdays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apkfuns.logutils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentShequ extends Fragment {

    View mainView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_shequ, container, false);
        }
        ButterKnife.bind(this, mainView);
        LogUtils.d("*************** onCreateView ******************");
        return mainView;
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtils.d("*************** onActivityCreated ******************");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d("*************** onStart ******************");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("*************** onResume ******************");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("*************** onPause ******************");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d("*************** onStop ******************");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("*************** onDestroy ******************");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.d("*************** onDetach ******************");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("*************** onCreate ******************");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.d("*************** onViewCreated ******************");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.d("*************** onAttach ******************");
    }
}