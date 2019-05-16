package com.example.aula4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonParser json = new JsonParser(this);
        //String nossoJson = jp.getJson();

        //instanciando um objeto clima
        Clima clima = json.parse();

        //recuperando as informacoes

        String siglaEstado = "Estado: "+clima.getSigla()+"\n";
        TextView tv = findViewById(R.id.sigla_estado);
        tv.setText(siglaEstado);

        //----------------------Usando um simple Adapter para popular o List View-------------------

        //criando lista com 2 itens em cada posicao para o listView (vetor bidimensional de strings)
        List<Map<String, String>> listArray = new ArrayList<>();

        //recuperado as infomações do nosso objeto clima
        ArrayList<Clima.Dados> arrayDados = clima.getArrayDados();

        for(Clima.Dados d: arrayDados){//for each de objetos
            //criando uma estrutura basica de 2 strings
            Map<String, String> listItem = new HashMap<>();
            listItem.put("dt", d.getData());
            String temp = "Temperatura: Min "+d.getTemperatura().getMinima()+"ºc, Max "+d.getTemperatura().getMaxima()+"ºc\n";
            listItem.put("t", temp);
            //inserindo no vetor
            listArray.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listArray,
                android.R.layout.simple_list_item_2,
                new String[] {"dt", "t" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        //obtendo id do listview
        ListView listView = findViewById(R.id.dados);
        //inserindo dados atraves do adapter
        listView.setAdapter(simpleAdapter);


    }
}
