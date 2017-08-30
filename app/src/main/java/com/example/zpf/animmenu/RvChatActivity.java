package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ChatAdapter;
import model.ChatModel;

public class RvChatActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 存储聊天信息
     */
    private ArrayList<ChatModel> models = new ArrayList<>();

    /**
     * 表情候选ViewPager
     */
    private ViewPager vpFace;

    /**
     * 聊天内容输入框
     */
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_chat);

        createDatas();

        initView();
    }

    /**
     * 制造假数据
     */
    private void createDatas() {

        for (int i = 0; i < 30; i++) {

            ChatModel model = new ChatModel();
            model.setAvatarUrl("这个是显示的头像图片的url" + i);

            String content = i % 2 == 0 ? "短句聊天" + (i + 1) + "条" :
                    "长的聊天内容这个是" + (i + 1) + "条";
            model.setContent(content);
            model.setMine(i % 2 == 0);

            models.add(model);
        }
    }

    private void initView() {

        initViewPager();

        initInputBar();

        initRecyclerView();
    }

    private void initViewPager() {

        vpFace = (ViewPager) findViewById(R.id.vp_face);
    }

    private void initInputBar() {

        ImageView ivFace = (ImageView) findViewById(R.id.iv_face);
        ivFace.setOnClickListener(this);

        ImageView ivSend = (ImageView) findViewById(R.id.iv_send);
        ivSend.setOnClickListener(this);

        etInput = (EditText) findViewById(R.id.et_input);
    }

    private void initRecyclerView() {

        RecyclerView rvChat = (RecyclerView) findViewById(R.id.rv_chat);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setHasFixedSize(true);

        ChatAdapter adapterChat = new ChatAdapter(this);
        rvChat.setAdapter(adapterChat);
        adapterChat.setModels(models);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_face:

                vpFace.setVisibility(vpFace.getVisibility() == View.VISIBLE ?
                        View.GONE : View.VISIBLE);
                break;

            case R.id.iv_send:

                clickIvSend();
                break;
        }
    }

    /**
     * 发送按钮点击
     */
    private void clickIvSend() {

        if (TextUtils.isEmpty(etInput.getText())) {

            Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            etInput.setText(null);
        }
    }
}
