package com.wong.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    class MyBind extends IMyAidlInterface.Stub{

        @Override
        public String getString() throws RemoteException {
            return "服务端：Hello world";
        }
    }
}
