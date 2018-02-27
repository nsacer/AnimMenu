package com.example.zpf.animmenu

import android.app.Application
import android.content.Context
import android.content.res.Resources

/**
 * Created by Administrator on 2018/2/27.
 */
class MyApplication : Application() {

    companion object {

        fun getContext(): Context {

            return MyApplication().applicationContext
        }

        fun getResources(): Resources {

            return MyApplication().resources
        }
    }
}