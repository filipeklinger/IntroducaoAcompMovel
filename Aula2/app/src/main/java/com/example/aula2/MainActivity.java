package com.example.aula2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //variaveis fora dos metodos sao globais
    TextView gorgeta;
    TextView valorTotal;
    int tipValue = 20;
    float valorInicial = 5.7f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtendo os Ids
        final EditText editValor = findViewById(R.id.valor);
        SeekBar seekBar = findViewById(R.id.seek_bar);
        final TextView seekText = findViewById(R.id.seekpercent);
        gorgeta = findViewById(R.id.gorgeta);
        valorTotal = findViewById(R.id.valor_total);

        //aqui obtemos a moeda corrente do dispositivo
        final NumberFormat dinheiroNum = NumberFormat.getCurrencyInstance();


        //Setando Valores iniciais
        String val = String.format(Locale.getDefault(),"%s",dinheiroNum.format(valorInicial));
        editValor.setText(val);
        seekBar.setProgress(tipValue);
        seekText.setText(String.format(Locale.getDefault(),"%s%%",tipValue));
        calculaGorgeta();

        //formatando valorInicial a cada alteracao do usuario
        editValor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    valorInicial = onlyDigits(editValor.getText().toString());
                    String val = String.format(Locale.getDefault(),"%s",dinheiroNum.format(valorInicial));
                    editValor.setText(val);
                    calculaGorgeta();
                }
                return false;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipValue = seekBar.getProgress();
                String percent = String.format(Locale.getDefault(),"%s%%",tipValue);
                seekText.setText(percent);
                calculaGorgeta();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public float onlyDigits(String any){
        String simbolo = NumberFormat.getCurrencyInstance().getCurrency().getSymbol();
        //temos que remover o simbolo do texto e depois trocar a virgula pelo poto
        String str = any.replace(simbolo,"").replace(",",".");
        return Float.parseFloat(str);
    }

    public float calculaGorgeta(){
        NumberFormat dinheiroNum = NumberFormat.getCurrencyInstance();
        float g =(valorInicial *tipValue)/100;
        String gorg = String.format(Locale.getDefault(),"Gorgeta: %s",dinheiroNum.format(g));
        gorgeta.setText(gorg);
        String tot = String.format(Locale.getDefault(),"Total: %s",dinheiroNum.format(valorInicial+g));
        valorTotal.setText(tot);
        return (valorInicial *tipValue)/100;
    }


}
