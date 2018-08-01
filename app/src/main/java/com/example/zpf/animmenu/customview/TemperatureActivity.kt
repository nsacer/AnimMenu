package com.example.zpf.animmenu.customview

import android.os.Bundle
import com.example.zpf.animmenu.BaseActivity
import com.example.zpf.animmenu.R
import kotlinx.android.synthetic.main.activity_temperature.*

/**
 *
 * 温度计（刻度表）activity
 * */
class TemperatureActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
    }

    override fun onResume() {
        super.onResume()

        temperatureView.setValueWithAnim(90)
    }
}
