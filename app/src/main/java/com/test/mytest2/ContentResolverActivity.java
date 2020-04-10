package com.test.mytest2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class ContentResolverActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri_user = Uri.parse("content://com.test.mytest/user");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id",4);
        contentValues.put("name","wh");

        //获取ContentResolver
        ContentResolver resolver = getContentResolver();
        resolver.insert(uri_user,contentValues);

        Cursor cursor = resolver.query(uri_user,new String[]{"_id","name"},null,null,null);
        while (cursor.moveToNext()){
            Log.d("123","query book:" + cursor.getInt(0) +" "+ cursor.getString(1));
        }
        cursor.close();


           /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_job = Uri.parse("content://com.test.mytest/job");

        // 插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 4);
        values2.put("job", "LOL Player");

        // 获取ContentResolver
        ContentResolver resolver2 =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver2.insert(uri_job,values2);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            System.out.println("query job:" + cursor2.getInt(0) +" "+ cursor2.getString(1));
            // 将表中数据全部输出
        }
        cursor2.close();
    }
}
