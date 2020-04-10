package com.test.mytest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.test.mytest.IBinderPool;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IBinderPool iBinderPool;
    private TextView tv;
    private String name;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("123", "------test2 onServiceConnected--------");
            iBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                tv.setText(iBinderPool.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Messenger clientMessenger,serverMessage;
    private ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             Log.d("123", "------messengerConnection onServiceConnected--------");
            serverMessage = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.e("123", "-------client-------" + msg.what);
        }
    };

    private void sendMessage() {
        Message msg = Message.obtain(null, 1);
        msg.replyTo = clientMessenger;
        try {
            serverMessage.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientMessenger = new Messenger(handler);


        tv = findViewById(R.id.tv);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);


        Intent messengerIntent = new Intent();
        messengerIntent.setAction("com.test.wh.MessengerService");
        messengerIntent.setPackage("com.test.mytest");
        bindService(messengerIntent, messengerConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                Intent intent = new Intent();
                intent.setAction("com.test.wh.AIDLService");
                intent.setPackage("com.test.mytest");
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn1:
                sendMessage();
                break;
            case R.id.btn2:
                try {
                    iBinderPool.setName("哈哈哈哈哈哈");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn3:
                try {
                    tv.setText(iBinderPool.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4:
                startActivity(new Intent(this,ContentResolverActivity.class));
                break;
        }
    }
}
