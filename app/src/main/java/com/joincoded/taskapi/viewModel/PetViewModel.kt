package com.joincoded.taskapi.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joincoded.taskapi.interfaceAPI.PetsAPI
import com.joincoded.taskapi.model.Pet
import com.joincoded.taskapi.repo.PetsRepo
import com.joincoded.taskapi.singelton.RetrofitHelper
import kotlinx.coroutines.launch

class PetViewModel : ViewModel() {

    private val petApiService = RetrofitHelper.getInstance().create(PetsAPI::class.java)
    private val repository = PetsRepo(petApiService)

    var pets by mutableStateOf(listOf<Pet>())

    init {
        fetchPets()
    }


    fun fetchPets() {
        viewModelScope.launch {
            try {

                pets = repository.getAllPets()

            } catch (e: Exception) {
                // Log the exception or handle the error
            }
        }
    }


    fun addPet(pet: Pet) {
        viewModelScope.launch {
            try {
                val response = petApiService.addPet(pet)

                if (response.isSuccessful && response.body() !== null) {

                } else {

                }
            } catch (e: Exception) {

            }


        }
    }

    fun deletePet(petId: Int) {
        viewModelScope.launch {
            try {
                var response = petApiService.deletePet(petId)

                if (response.isSuccessful) {
                    println("Pet has been Deleted")
                } else {
                    println("Error")
                }

            } catch (e: Exception) {


            }
        }


    }
}