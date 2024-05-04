package com.a0503.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.a0503.aidl.IMyAidlInterface


class MyAidlService : Service() {

    private val mBinder: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        override fun add(a: Int, b: Int): Int {
            return a + b
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

}