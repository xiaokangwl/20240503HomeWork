package com.a0503.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class StartmodeActivity : AppCompatActivity() {

    /**
     * 1. （1）生命周期的顺序：
     *  第一个Activity的启动顺序：onCreate()——>onStart()——>onResume()
     *
     * 当另一个Activity启动时：第一个Activity onPause()——>第二个Activity onCreate()——>onStart()——>onResume()——>第一个Activity onStop()
     *
     * 当返回到第一个Activity时：第二个Activity onPause()——> 第一个Activity onRestart()——>onStart()——>onResume()——>第二个Activity onStop()——>onDestroy()
     * （2）生命周期的特点：
     * 生命周期方法按顺序调用，但并不是每次都完整调用。例如，当用户切换到其他应用时，Activity 可能仅调用到 onPause() 而不会调用 onStop() 或 onDestroy()。
     * 当用户旋转屏幕或按下后退键时，Activity 会经历销毁和重新创建的过程（onDestroy() -> onCreate()）。
     * 生命周期方法提供了在各个阶段执行特定操作的机会，例如在 onCreate() 中初始化数据，在 onResume() 中开始动画等。
     * （3）不同的生命周期应该做什么：
     * onCreate——Activity被创建，做一些初始化工作，例如setContentView去加载界面布局，初始化数据等。
     * onStart——Activity被启动，准备开始用户交互。例如，注册广播接收器或连接到服务。Activity已经可见，但还不能与用户交互。
     * onRestart——Activity被重新启动，一般由不可见状态重新变成可见状态，onRestart方法就被调用。
     * onResume——Activity可见并且可以交互
     * onStart可见其实Activity还在后台，其实还无法真实看到；onResume的时候Activity才显示到前台，我们才真实可见。
     * onPause——暂停，正常情况下会紧接着调用onStop，此时可以做一些存储数据、停止动画等工作
     * onStop——Activity即将停止，释放不再需要的资源
     * onDestory——Activity即将被销毁，释放所有的资源
     * （4）启动模式有哪些:（四种）
     * Standard：每次启动都创建新的实例，生命周期为 onCreate -> onStart -> onResume -> onPause -> onStop -> onDestroy。大多数情况下，适用于普通的应用导航
     *
     * SingleTop：如果栈顶已有该 Activity 的实例，则不会创建新实例，而是调用其 onNewIntent 方法，生命周期为 onNewIntent -> onResume -> onPause -> onStop -> onDestroy。当希望 Activity 保持单例且可以接受多次调用时使用。
     *
     * SingleTask：如果栈中已有该 Activity 的实例，则移除该实例上方所有实例，使其成为栈顶，并调用 onNewIntent 方法，生命周期为 onNewIntent -> onResume -> onPause -> onStop -> onDestroy。当希望确保每个任务只有一个该 Activity 实例存在时使用，例如主界面。
     *
     * SingleInstance：与 SingleTask 类似，但是该 Activity 独占一个任务栈，生命周期为 onNewIntent -> onResume -> onPause -> onStop -> onDestroy。当希望确保 Activity 完全独立于其他 Activity 且仅存在一个实例时使用
     */

    var mode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = intent.getStringExtra("mode")
        Log.d("ActivityLifecycle", "onCreate $mode")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifecycle", "onStart $mode")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "onResume $mode")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifecycle", "onPause $mode")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle", "onStop $mode")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", "onDestroy $mode")
    }
}