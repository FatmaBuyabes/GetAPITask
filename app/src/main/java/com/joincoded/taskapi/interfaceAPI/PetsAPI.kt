package com.joincoded.taskapi.interfaceAPI

import com.joincoded.taskapi.model.Pet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PetsAPI {

    @GET("pets")
    suspend fun getAllPets(): List<Pet>

    @POST("pets") // it will work and not freeze
    suspend fun addPet(@Body pet: Pet): Response<Pet>

    @DELETE("pets/{petID}")
    suspend fun deletePet(@Path("petID") petID: Int): Response<Unit>   // anything will be shown

// create variable of book //need more about response


}