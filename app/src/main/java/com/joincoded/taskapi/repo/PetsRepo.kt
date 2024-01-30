package com.joincoded.taskapi.repo

import com.joincoded.taskapi.interfaceAPI.PetsAPI

class PetsRepo(private val api: PetsAPI) {
    suspend fun getAllPets() = api.getAllPets()

}
