package com.example.zpf.animmenu.emotion

import android.os.Bundle
import com.example.zpf.animmenu.BaseActivity
import com.example.zpf.animmenu.R
import emotion.EmotionBaseFragment
import emotion.EmotionMainFragment
import kotlinx.android.synthetic.main.activity_emotion_test.*

class EmotionTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_test)
        initView()
    }

    private fun initView() {

        createEmotionFragment()
    }

    private fun createEmotionFragment() {

        val bundle = Bundle()
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true)
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false)

        val fragmentEmotion: EmotionMainFragment = EmotionBaseFragment.newInstance(EmotionMainFragment::class.java, bundle)
        fragmentEmotion.bindToContentView(srlEmotionAct)

        supportFragmentManager.beginTransaction().replace(R.id.flEmotionKeyboard, fragmentEmotion).addToBackStack(null).commit()
    }
}
