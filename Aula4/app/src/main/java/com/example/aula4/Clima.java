package com.example.aula4;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Clima {
    private String nome,sigla,country;
    private ArrayList<Dados> arrayDados;

    //construtor
    public Clima(){
        arrayDados = new ArrayList<>();//Inicializa o vetor
    }

    //setters
    public void setSigla(String sigla){
        this.sigla = sigla;
    }
    public void setCountry(String country){
        this.country = country;
    }

    public void setDados(Dados dados){
        arrayDados.add(dados);
    }

    //getters
    public String getNome() { return nome; }

    public String getSigla() { return sigla; }

    public String getCountry() { return country; }

    public ArrayList<Dados> getArrayDados() { return arrayDados; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //objeto dados que representa cada dia sobre o Clima
    public static class Dados{
        private String data;
        private Humidade humidade;
        private Temperatura temperatura;
        //construtor da classe
        public Dados(){
        }

        //setters
        public void setData(String data) { this.data = data;}
        public void setHumidade(int min,int max) { this.humidade = new Humidade(min,max); }
        public void setTemperatura(int min,int max) { this.temperatura = new Temperatura(min,max); }

        //getters
        public String getData(){ return this.data; }
        public Humidade getHumidade(){ return this.humidade;}
        public Temperatura getTemperatura(){ return this.temperatura;}


        //objeto humidade
        public class Humidade{
            private int minima,maxima;
            //construtor da classe aninhada
            public Humidade(int min,int max){
                this.minima = min;
                this.maxima = max;
            }
            //getters
            public int getMinima(){ return this.minima; }
            public int getMaxima(){ return this.maxima; }

        }
        //objeto Temp
        public class Temperatura{
            private int minima,maxima;

            public Temperatura(int min,int max){
                this.minima = min;
                this.maxima = max;
            }

            //getters
            public int getMinima(){ return this.minima; }
            public int getMaxima(){ return this.maxima; }

        }
    }

}
