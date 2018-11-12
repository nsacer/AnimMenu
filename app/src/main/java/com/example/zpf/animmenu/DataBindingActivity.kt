package com.example.zpf.animmenu

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.zpf.animmenu.databinding.ActivityDataBindingBinding
import model.BindingModel
import model.MyHandler

/**
 * vm
 * */
class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        binding.bindingModel = BindingModel("Wang", "Pacy", 3)
        binding.myHandler = MyHandler()

    }
}
