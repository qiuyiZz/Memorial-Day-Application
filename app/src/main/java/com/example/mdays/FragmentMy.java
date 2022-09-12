package com.example.mdays;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apkfuns.logutils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMy extends Fragment {

    View mainView = null;
    private EditText editPhone;
    private EditText editPwd;
    private Button btnLogin;
    private TextView txtForgetPwd;
    private TextView txtStartRegist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_my, container, false);
        }
        editPhone = (EditText) mainView.findViewById(R.id.editPhone);
        editPwd = (EditText) mainView.findViewById(R.id.editPwd);
        btnLogin = (Button) mainView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            //为找到的button设置监听
            @Override
//重写onClick函数
            public void onClick(View v) {
                onClick(v);
            }
        });


        ButterKnife.bind(this, mainView);

        LogUtils.d("*************** onCreateView ******************");

        return mainView;
    }

    @OnClick(R.id.btnLogin)
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtForgetPwd=mainView.findViewById(R.id.txtForgetPwd);
        txtStartRegist=mainView.findViewById(R.id.txtStartRegist);
        txtForgetPwd.setOnClickListener(new View.OnClickListener() {
            //为找到的button设置监听
            @Override
//重写onClick函数
            public void onClick(View v) {
                OnMyResPwdClick(v);
            }
        });
        txtStartRegist.setOnClickListener(new View.OnClickListener() {
            //为找到的button设置监听
            @Override
//重写onClick函数
            public void onClick(View v) {
                OnMyRegistClick(v);
            }
        });

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

    public void OnMyLoginClick(View v){
        if(pubFun.isEmpty(editPhone.getText().toString()) || pubFun.isEmpty(editPwd.getText().toString())){
            Toast.makeText(getActivity(), "手机号或密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        //call DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(getActivity(),"mdays.db",null,3);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=? and pwd=?",new String[]{editPhone.getText().toString(),editPwd.getText().toString()},null,null,null);
        if(c!=null && c.getCount() >= 1){
            String[] cols = c.getColumnNames();
            while(c.moveToNext()){
                for(String ColumnName:cols){
                    Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                }
            }
            c.close();
            db.close();

            //将登陆用户信息存储到SharedPreferences中
            SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("setting",Context.MODE_PRIVATE); //实例化SharedPreferences对象
            SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象
            editor.putString("userID", editPhone.getText().toString()); //用putString的方法保存数据
            editor.commit(); //提交当前数据

            getActivity().finish();
        }
        else{
            Toast.makeText(getActivity(), "手机号或密码输入错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Jump to the registration interface
     * @param v
     */
    public void OnMyRegistClick(View v)  {
        Intent intent=new Intent(getActivity(),RegistActivity.class);
        //intent.putExtra("info", "No66778899");
        getActivity().startActivity(intent);
    }

    /**
     * Jump to reset password interface
     * @param v
     */
    public void OnMyResPwdClick(View v){
        Intent intent=new Intent(getActivity(),ResPwdActivity.class);
        getActivity().startActivity(intent);
    }
}