package com.zdlly.myshapeview;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_sides;
    private Button submit;
    private MyShapeView my_view;
    private AppCompatRadioButton color;
    private AppCompatRadioButton photo;
    private RadioGroup choose;
    private Button color_choose;
    private Button picture_take;
    private Button picture_choose;

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
        my_view.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
        submit.setOnClickListener(this);
        color = (AppCompatRadioButton) findViewById(R.id.color);
        photo = (AppCompatRadioButton) findViewById(R.id.photo);
        choose = (RadioGroup) findViewById(R.id.choose);
        color_choose = (Button) findViewById(R.id.color_choose);
        color_choose.setOnClickListener(this);
        picture_take = (Button) findViewById(R.id.picture_take);
        picture_take.setOnClickListener(this);
        picture_choose = (Button) findViewById(R.id.picture_choose);
        picture_choose.setOnClickListener(this);
        picture_take.setVisibility(View.GONE);
        picture_choose.setVisibility(View.GONE);

        choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.color:
                        picture_take.setVisibility(View.GONE);
                        picture_choose.setVisibility(View.GONE);
                        color_choose.setVisibility(View.VISIBLE);
                        break;
                    case R.id.photo:
                        color_choose.setVisibility(View.GONE);
                        picture_take.setVisibility(View.VISIBLE);
                        picture_choose.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                submit();
                break;
            case R.id.color_choose:
                break;
            case R.id.picture_take:
                break;
            case R.id.picture_choose:
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
        my_view.setIsReload(1);
        my_view.invalidate();
    }
}
