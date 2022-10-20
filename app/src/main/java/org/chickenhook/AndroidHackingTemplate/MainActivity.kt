package org.chickenhook.AndroidHackingTemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import org.chickenhook.AndroidHackingTemplate.penetration.ClipboardManagerCrash
import org.chickenhook.AndroidHackingTemplate.penetration.PackageManagerCrash
import org.chickenhook.AndroidHackingTemplate.penetration.ServiceManagerCrash

//import org.chickenhook.myandroidhackinglibrary.NativeInterface

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //NativeInterface.installHooks()
        findViewById<Button>(R.id.package_manager_attack)?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                PackageManagerCrash().launch(this@MainActivity)
            }
        })
        findViewById<Button>(R.id.clipboard_manager_crash)?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ClipboardManagerCrash().launch(this@MainActivity)
            }
        })
        findViewById<Button>(R.id.service_manager_crash)?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ServiceManagerCrash().launch(this@MainActivity)
            }
        })
    }
}
