package com.example.consomationapi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/Offre")
    suspend fun getOffres(): Response<MutableList<offre>>

    @GET("/Offre/{id}")
    suspend fun  getOffreById(@Path("id") searchById:Int?):Response<offre>


    @POST("/Offre")
    suspend fun  AddOffre(@Body Offre: offre):Response<offre>

    @PUT("/Offre/{id}")
    suspend fun  UpdateOffre(@Path(value="id") code:Int ,@Body Offre: offre):Response<offre>

    @DELETE("/Offre/{id}")
    suspend  fun deleteOffre(@Path(value="id") code: Int): Response<String>
}