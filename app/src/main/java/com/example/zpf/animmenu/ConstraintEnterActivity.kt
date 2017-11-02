package com.example.zpf.animmenu

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_constraint_enter.*

class ConstraintEnterActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnOne -> {

                openActivity(ConstraintLayoutActivity::class.java, v)
            }
            R.id.btnTwo -> {

                openActivity(ConstraintLayoutAnimActivity::class.java, v)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_enter)

        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
    }

}
