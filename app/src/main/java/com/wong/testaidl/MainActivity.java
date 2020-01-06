package com.wong.testaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wong.remoteservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvData;
    private Button mBtnBind;
    private Button mBtnUnbind;
    private IMyAidlInterface myBind;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBind = IMyAidlInterface.Stub.asInterface(service);
            try {
                mTvData.setText(myBind.getString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();

    }

    private void initViews(){
        mTvData = findViewById(R.id.tv_data);
        mBtnBind = findViewById(R.id.btn_bind);
        mBtnUnbind = findViewById(R.id.btn_unbind);
    }
    private void initListeners(){
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*绑定服务*/
            case R.id.btn_bind:
                Intent intent = new Intent();
                /*从 Android 5.0开始 隐式Intent绑定服务的方式已不能使用,所以这里需要设置Service所在服务端的包名*/
                intent.setPackage("com.wong.remoteservice");
                intent.setAction("android.intent.action.com.wong.remoteservice.MY_SERVICE");
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            /*解绑服务*/
            case R.id.btn_unbind:
                unbindService(serviceConnection);

        }
    }
}
