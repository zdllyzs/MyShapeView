package com.zdlly.myshapeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_sides;
    private Button submit;
    private MyShapeView my_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edit_sides = (EditText) findViewById(R.id.edit_sides);
        submit = (Button) findViewById(R.id.submit);
        my_view = (MyShapeView) findViewById(R.id.my_view);
        //my_view.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String sides = edit_sides.getText().toString().trim();
        if (TextUtils.isEmpty(sides)) {
            Toast.makeText(this, "sides不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        my_view.setmSides(Integer.parseInt(sides));
        my_view.invalidate();
    }
}
