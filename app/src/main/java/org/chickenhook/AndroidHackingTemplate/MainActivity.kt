package org.chickenhook.AndroidHackingTemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.chickenhook.myandroidhackinglibrary.NativeInterface

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NativeInterface.installHooks()
    }
}
