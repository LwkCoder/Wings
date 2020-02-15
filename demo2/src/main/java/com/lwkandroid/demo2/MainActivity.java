package com.lwkandroid.demo2;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/13
 */
public class MainActivity extends AppCompatActivity
{
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.img_test);
        findViewById(R.id.btn_test).setOnClickListener(v -> test());

    }

    private void test()
    {
    }
}
