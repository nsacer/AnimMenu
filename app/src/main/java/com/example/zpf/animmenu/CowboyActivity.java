package com.example.zpf.animmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import customview.TurtleGraph;

public class CowboyActivity extends AppCompatActivity implements View.OnClickListener {

    private TurtleGraph turtleGraph;
    
    private EditText etInputBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure);

        initView();
    }

    private void initView() {

        Button btnStartAnim = (Button) findViewById(R.id.btn_start_anim_turtle);
        btnStartAnim.setOnClickListener(this);

        Button btnCancelAnim = (Button) findViewById(R.id.btn_cancel_anim_turtle);
        btnCancelAnim.setOnClickListener(this);

        turtleGraph = (TurtleGraph) findViewById(R.id.turtle);
        
        etInputBlank = (EditText) findViewById(R.id.et_input_blank);
        Button btnInputSubmit = (Button) findViewById(R.id.btn_blank_click);
        btnInputSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_start_anim_turtle:

                turtleGraph.doAnimator();
                break;

            case R.id.btn_cancel_anim_turtle:

                turtleGraph.cancelAnimator();
                break;
            
            case R.id.btn_blank_click:
                
                if(TextUtils.isEmpty(etInputBlank.getText().toString().trim()))
                    Toast.makeText(this, "提交的内容不可以为空啊", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "成功！", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

}
