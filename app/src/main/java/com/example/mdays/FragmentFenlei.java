package com.example.mdays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentFenlei extends Fragment {

    View mainView = null;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_fenlei, container, false);
        }
        listView = (ListView)mainView.findViewById(R.id.listview);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new ListViewAdapter(getActivity(), list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub


                Intent intent = new Intent(getActivity(),Main3Activity.class);
                intent.putExtra("type", (String)list.get(position).get("title"));
                startActivity(intent);


            }
        });


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

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        String[]fenlei=new String[]{"work","study","anniversary","birth","family"};
        int[]icons=new int[]{R.drawable.work,R.drawable.study,R.drawable.anniversary,R.drawable.birth,R.drawable.family};
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", icons[i]);
            map.put("title", fenlei[i]);
//            map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }


}