package com.example.quizbandeiras;

import java.util.List;

public class Pergunta {
    private int imagemBandeira;
    private String enunciado;
    private List<String> alternativas;
    private int indiceCorreto;

    public Pergunta(int imagemBandeira, String enunciado, List<String> alternativas, int indiceCorreto) {
        this.imagemBandeira = imagemBandeira;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.indiceCorreto = indiceCorreto;
    }

    public int getImagemBandeira() { return imagemBandeira; }
    public String getEnunciado() { return enunciado; }
    public List<String> getAlternativas() { return alternativas; }
    public int getIndiceCorreto() { return indiceCorreto; }
}