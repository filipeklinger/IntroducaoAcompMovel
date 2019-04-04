package com.example.aula4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonParser jp = new JsonParser(this);
        String nossoJson = jp.getJson();

        TextView tv = findViewById(R.id.tv1);
        tv.setText(nossoJson);
    }
}
