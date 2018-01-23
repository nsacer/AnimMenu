package com.example.zpf.animmenu;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import utils.EmojiFilter;

@ContentView(R.layout.activity_emoji)
public class EmojiActivity extends BaseActivity {

    @ViewInject(R.id.et_input_emoji)
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initEmojiInput();
    }

    private void initEmojiInput() {

        etInput.setFilters(new InputFilter[]{new EmojiFilter()});
    }

    @Event(R.id.btn_emoji)
    private void emojiOk(View view) {

        Toast.makeText(this, "emojiOk", Toast.LENGTH_SHORT).show();
    }

    @Event(R.id.btnFaceInput)
    private void openFaceInputAct(View view) {

        openAct(FaceInputActivity.class);
    }
}
