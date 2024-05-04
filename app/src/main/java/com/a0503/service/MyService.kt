package com.a0503.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    /**
     * startService() 和 bindService() 是用于启动 Service 的两种不同方式
     *
     * startService() 启动 Service 的生命周期：
     * onCreate()：Service 被创建。
     * onStartCommand()：Service 已经启动并且正在运行。
     * onDestroy()：Service 被销毁。
     *
     * bindService() 绑定 Service 的生命周期：
     * onCreate()：Service 被创建。
     * onBind()：Service 被绑定。
     * onUnbind()：Service 解绑。
     * onDestroy()：Service 被销毁。
     *
     * 在 startService() 中，Service 生命周期的开始是在 onCreate() 方法中，而结束是在 onDestroy() 方法中。而在 bindService() 中，Service 生命周期的开始也是在 onCreate() 方法中，但结束取决于绑定状态，只有当所有客户端都解绑时才会调用 onDestroy() 方法。startService() 和 bindService() 也可以同时使用，此时 Service 将会同时执行两种生命周期模式，即同时调用 onCreate() 和 onStartCommand() 方法，并在所有客户端解绑后调用 onDestroy() 方法。
     */

    override fun onCreate() {
        super.onCreate()
        Log.d("ServiceLifecycle", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServiceLifecycle", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("ServiceLifecycle", "onBind")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("ServiceLifecycle", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ServiceLifecycle", "onDestroy")
    }

}