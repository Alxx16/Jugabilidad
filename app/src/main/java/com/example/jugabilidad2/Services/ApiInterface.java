package com.example.jugabilidad2.Services;

import com.example.jugabilidad2.Entidades.Responses.PareoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/PreguntasPareo/1")
    Call<List<PareoResponse>> obtenerListadoPareo();

}
