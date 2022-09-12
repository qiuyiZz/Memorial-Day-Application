package com.example.mdays;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mdays.util.DateFormatType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mdays.util.MyFormat.myDateFormat;




/**
 * create_by Android Studio
 *
 * @author zouguo0212@
 * @package_name fun.zzti
 * @description
 * @date 2018/10/26 17:30
 */
public class Main2Activity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private final static String TAG = "Main2Activity";

    DBOpenHelper DBOpenHelper;
    private ListView myListView;
//    private Button createButton;
    private MyBaseAdapter myBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_linear_layout);
        init();
    }

    //初始化控件
    private void init(){
//        createButton = findViewById(R.id.createButton);
//        createButton.setOnClickListener(this);

        myListView = findViewById(R.id.list_view);

        List<Record> recordList = new ArrayList<>();
        DBOpenHelper = new DBOpenHelper(this,"mday.db",null,3);
        SQLiteDatabase db = DBOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.TABLE_NAME_RECORD,null,
                null,null,null,
                null,DBOpenHelper.NOTICE_TIME+","+DBOpenHelper.RECORD_TIME+" DESC");
        if(cursor.moveToFirst()){
            Record record;
            while (!cursor.isAfterLast()){
                record = new Record();
                record.setId(
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECORD_ID))));
                record.setTitleName(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECORD_TITLE))
                );
                record.setTypeName(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECORD_TYPE))
                );
                record.setTextBody(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECORD_BODY))
                );
                record.setCreateTime(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECORD_TIME)));
                record.setNoticeTime(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTICE_TIME)));
                recordList.add(record);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        // 创建一个Adapter的实例
        myBaseAdapter = new MyBaseAdapter(this,recordList,R.layout.list_item);
        myListView.setAdapter(myBaseAdapter);
        // 设置点击监听
        myListView.setOnItemClickListener(this);
        myListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.createButton:
//                Intent intent = new Intent(Main2Activity.this, EditActivity.class);
//                startActivity(intent);
//                Main2Activity.this.finish();
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Main2Activity.this,AmendActivity.class);
        Record record = (Record) myListView.getItemAtPosition(position);
        intent.putExtra(DBOpenHelper.RECORD_TITLE,record.getTitleName().trim());
        intent.putExtra(DBOpenHelper.RECORD_TYPE,record.getTypeName().trim());
        intent.putExtra(DBOpenHelper.RECORD_BODY,record.getTextBody().trim());
        intent.putExtra(DBOpenHelper.RECORD_TIME,record.getCreateTime().trim());
        intent.putExtra(DBOpenHelper.RECORD_ID,record.getId().toString().trim());
        if (record.getNoticeTime()!=null) {
            intent.putExtra(DBOpenHelper.NOTICE_TIME, record.getNoticeTime().trim());
        }
        this.startActivity(intent);
        Main2Activity.this.finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Record record = (Record) myListView.getItemAtPosition(position);
        showDialog(record,position);
        return true;
    }

    void showDialog(final Record record,final int position){

        final AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(Main2Activity.this);
        dialog.setTitle("是否删除？");
        String textBody = record.getTextBody();
        dialog.setMessage(
                textBody.length()>150?textBody.substring(0,150)+"...":textBody);
        dialog.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = DBOpenHelper.getWritableDatabase();
                            db.delete(DBOpenHelper.TABLE_NAME_RECORD,
                                DBOpenHelper.RECORD_ID +"=?",
                                new String[]{String.valueOf(record.getId())});
                        db.close();
                        myBaseAdapter.removeItem(position);
                        myListView.post(new Runnable() {
                            @Override
                            public void run() {
                                myBaseAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
        dialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.show();
    }



    /**
     * ListView展示的适配器类
     */
    class MyBaseAdapter extends BaseAdapter{
        private List<Record> recordList;//数据集合
        private Context context;
        private int layoutId;

        public MyBaseAdapter(Context context,List<Record> recordList,int layoutId){
            this.context = context;
            this.recordList = recordList;
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            if (recordList!=null&&recordList.size()>0)
                return recordList.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            if (recordList!=null&&recordList.size()>0)
                return recordList.get(position);
            else
                return null;
        }

        public void removeItem(int position){
            this.recordList.remove(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.list_item, parent,
                        false);
                viewHolder  = new ViewHolder();
                viewHolder.titleView = convertView.findViewById(R.id.list_item_title);
                viewHolder.typeView = convertView.findViewById(R.id.list_item_type);
                viewHolder.bodyView = convertView.findViewById(R.id.list_item_body);
                viewHolder.timeView = convertView.findViewById(R.id.list_item_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Record record = recordList.get(position);
            String tile = record.getTitleName();
            viewHolder.titleView.setText((position+1)+".距离"+tile);
//            viewHolder.titleView.setText(tile);
            String type = record.getTypeName();
            viewHolder.typeView.setText((type.length()>10?type.substring(0,10)+"...":type));
            String body = record.getTextBody();
            viewHolder.bodyView.setText(body.length()>26?body.substring(0,25)+"...":body);
//            viewHolder.bodyView.setText(body);
//            String createTime = record.getCreateTime();
            String noticeTime = record.getNoticeTime();
            Date date1 = new Date(System.currentTimeMillis());

            Date date2 = myDateFormat(noticeTime,DateFormatType.NORMAL_DATE);
            long day = (date2.getTime()-date1.getTime())/(24*60*60*1000);
//            viewHolder.timeView.setText(getTimeStr(date));
            viewHolder.timeView.setText("还有"+Long.toString(day)+"天");
            return convertView;
        }
    }

    /**
     * ListView里的组件包装类
     */
    class ViewHolder{
        TextView titleView;
        TextView typeView;
        TextView bodyView;
        TextView timeView;
    }

}

