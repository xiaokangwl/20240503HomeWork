package com.a0503

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.a0503.activity.StartmodeActivity
import com.a0503.aidl.IMyAidlInterface
import com.a0503.receiver.MyReceiver
import com.a0503.service.MyAidlService
import com.a0503.service.MyService
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    lateinit var myServiceIntent: Intent
    private var bound: Boolean = false
    private val myServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("ServiceLifecycle", "Service bound")
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("ServiceLifecycle", "Service disconnected")
            bound = false
        }
    }

    private var aidlService: IMyAidlInterface? = null
    private val myAidlServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlService = IMyAidlInterface.Stub.asInterface(service)
            Log.d("ServiceLifecycle", "Service bound")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
            Log.d("ServiceLifecycle", "Service disconnected")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: MaterialButton = findViewById(R.id.button1)
        val button2: MaterialButton = findViewById(R.id.button2)
        val button3: MaterialButton = findViewById(R.id.button3)
        val button4: MaterialButton = findViewById(R.id.button4)
        val bindService: MaterialButton = findViewById(R.id.bindService)
        val unbindService: MaterialButton = findViewById(R.id.unbindService)
        val registerReceiver: MaterialButton = findViewById(R.id.registerReceiver)
        val bindAidlService: MaterialButton = findViewById(R.id.bindAidlService)
        val unbindAidlService: MaterialButton = findViewById(R.id.unbindAidlService)
        val serviceContext: MaterialButton = findViewById(R.id.serviceContext)


        button1.setOnClickListener {
            startModeActivity()
        }
        button2.setOnClickListener {
            startModeActivity(Intent.FLAG_ACTIVITY_SINGLE_TOP, "singleTop")
        }
        button3.setOnClickListener {
            startModeActivity(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
                "singleTask"
            )
        }
        button4.setOnClickListener {
            startModeActivity(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
                "singleInstance"
            )
        }

        bindService.setOnClickListener {
            myServiceIntent = Intent(this, MyService::class.java)
            bindService(myServiceIntent, myServiceConnection, Context.BIND_AUTO_CREATE)
        }
        unbindService.setOnClickListener {
            if (bound) {
                unbindService(myServiceConnection)
                bound = false
                Log.d("ServiceLifecycle", "Service unbound")
            }
        }

        registerReceiver.setOnClickListener {
            val myReceiver = MyReceiver()
            val filter = IntentFilter().apply {
                addAction("com.a0503.receiver.MyReceiverTest")
            }
            registerReceiver(myReceiver, filter)
            // 发送广播
            val intent = Intent("com.a0503.receiver.MyReceiverTest")
            sendBroadcast(intent)
        }
        
        
        bindAidlService.setOnClickListener { 
            val intent = Intent(this, MyAidlService::class.java)
            bindService(intent, myAidlServiceConnection, Context.BIND_AUTO_CREATE)
        }
        unbindAidlService.setOnClickListener {
            unbindService(myAidlServiceConnection)
        }
        serviceContext.setOnClickListener { 
            val result = aidlService?.add(5, 5)
            Toast.makeText(this, "result=$result", Toast.LENGTH_SHORT).show()
        }
    }

    fun startModeActivity(flags: Int = 0, mode: String = "standard") {
        val intent: Intent = Intent(this, StartmodeActivity::class.java)
        if (flags != 0) intent.flags = flags
        intent.putExtra("mode", mode)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        bound = false
        unbindService(myServiceConnection)
    }
}