package com.example.jugablidad_1.Service;

import com.example.jugablidad_1.Models.Responses.PareoResponse;
import com.example.jugablidad_1.Models.Responses.PreguntasResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("preguntas/{temaid}")
    Call<List<PreguntasResponse>> getPreguntas(@Path("temaid") int tema_id);

    @GET("/preguntas_pareo/{temaid}")
    Call<List<PareoResponse>> obtenerListadoPareo(@Path("temaid") int tema_id);

}
