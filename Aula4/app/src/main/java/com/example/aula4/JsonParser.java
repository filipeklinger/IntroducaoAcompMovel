package com.example.aula4;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonParser {
    Context mContext;
    public JsonParser(Context context){
        mContext = context;
    }

    //---------------Metodos------------------------
    //busca o arquivo dentro de "res/raw" contendo o Json e retorna uma string com seu conteudo
    public String getJson(){
        String json;
        try {
            InputStream is = mContext.getResources().openRawResource(R.raw.climatempo);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Json Utils","Error Loading FROM Asset");
            return null;
        }
        return json;
    }

    public Clima parse(){
        Clima clima = new Clima();//instanciando nosso objeto clima
        String json = getJson();//obtendo nossa string Json

        try {
            //pegando a string Json transformano em Objeto
            JSONObject jsonObject = new JSONObject(json);

            //obtendo nome do objeto json
            String nome = jsonObject.getString("name");
            //inserindo na classe clima
            clima.setNome(nome);

            //setando outros atributos com o mesmo processo so que aninhado
            clima.setSigla(jsonObject.getString("state"));
            clima.setCountry(jsonObject.getString("country"));

            //fazendo parse dos dados desse clima
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i< jsonArray.length();i++){
                Clima.Dados dados = new Clima.Dados();
                JSONObject dd = jsonArray.getJSONObject(i);
                dados.setData(dd.getString("date_br"));
                    //humidade
                    JSONObject humidade = dd.getJSONObject("humidity");
                    dados.setHumidade(humidade.getInt("min"),humidade.getInt("max"));
                    //temperatura
                    JSONObject temp = dd.getJSONObject("temperature");
                    dados.setTemperatura(temp.getInt("min"),temp.getInt("max"));
                //adicionando dados ao clima
                clima.setDados(dados);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clima;
    }

}
