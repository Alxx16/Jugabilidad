package com.example.jugabilidad2.Entidades.Responses;

public class PareoResponse {

    private int pregunta_id;
    private int tematica_id;
    private int orden_pareo;
    private String texto;
    private String audio;

    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public int getTematica_id() {
        return tematica_id;
    }

    public void setTematica_id(int tematica_id) {
        this.tematica_id = tematica_id;
    }

    public int getOrden_pareo() {
        return orden_pareo;
    }

    public void setOrden_pareo(int orden_pareo) {
        this.orden_pareo = orden_pareo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAudio() { return audio; }

    public void setAudio(String audio) { this.audio = audio; }
}
