package com.example.zpf.animmenu

import adapter.EmotionGridViewAdapter
import adapter.EmotionPagerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_face_input.*
import utils.DisplayUtil
import utils.EmotionKeyboard
import utils.EmotionUtils
import utils.GlobalOnItemClickManagerUtils


class FaceInputActivity : AppCompatActivity() {

    private val emotion_map_type = EmotionUtils.EMOTION_CLASSIC_TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_input)

        initView()
    }

    private fun initView() {

        EmotionKeyboard.with(this)
                .setEmotionView(vpFaceKeyboard)
                .bindToContent(srlFaceInput)
                .bindToEditText(etInputFace)
                .bindToEmotionButton(ivFaceInput)
                .build()

        initFaceKeyBoard()
    }

    //初始化表情面板
    private fun initFaceKeyBoard() {

        val screenWidth = resources.displayMetrics.widthPixels
        val spacing = DisplayUtil.dip2px(this, 2f)
        val itemWidth = (screenWidth - spacing * 13) / 12
        val gvHeight = itemWidth * 4 + spacing * 6
        val emotionViews = ArrayList<GridView>()
        var emotionNames = ArrayList<String>()
        // 遍历所有的表情的key
        val faceSize = EmotionUtils.getEmojiMap(emotion_map_type).size

        for (emojiName in EmotionUtils.getEmojiMap(emotion_map_type).keys) {
            emotionNames.add(emojiName)
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size == 47) {
                val gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
                emotionViews.add(gv)
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = ArrayList()
            }
        }

        vpFaceKeyboard.adapter = EmotionPagerAdapter(emotionViews)
    }

    /**
     * 创建显示表情的GridView
     */
    private fun createEmotionGridView(emotionNames: List<String>, gvWidth: Int, padding: Int, itemWidth: Int, gvHeight: Int): GridView {
        // 创建GridView
        val gv = GridView(this)
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent)
        //设置7列
        gv.numColumns = 12
        gv.setPadding(padding, padding, padding, padding)
        gv.horizontalSpacing = padding
        gv.verticalSpacing = padding * 3
        //设置GridView的宽高
        val params = ViewGroup.LayoutParams(gvWidth, gvHeight)
        gv.layoutParams = params
        // 给GridView设置表情图片
        val adapter = EmotionGridViewAdapter(this, emotionNames, itemWidth, emotion_map_type)
        gv.adapter = adapter
        //设置全局点击事件
        val globalUtils = GlobalOnItemClickManagerUtils.getInstance(this)
        globalUtils.attachToEditText(etInputFace)
        gv.onItemClickListener = globalUtils.getOnItemClickListener(emotion_map_type)
        return gv
    }

}
