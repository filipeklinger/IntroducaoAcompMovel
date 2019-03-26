package com.example.aula1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView feedback = findViewById(R.id.feedback);
        final CheckBox check1 = findViewById(R.id.check1);
        final CheckBox check2 = findViewById(R.id.check2);
        final ToggleButton toggleButton = findViewById(R.id.toggle);
        Button ok = findViewById(R.id.ok);

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("Check1: "+(check1.isChecked()? "Marcado": "Desmarcado"));
            }
        });

        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("Check2: "+(check2.isChecked()? "Marcado": "Desmarcado"));
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("Botao esta: "+toggleButton.getText());
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("Ok pressionado");
            }
        });
    }
}
