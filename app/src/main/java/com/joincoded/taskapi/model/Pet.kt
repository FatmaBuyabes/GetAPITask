package com.joincoded.taskapi.model


data class Pet(
    val id: Int,
    var name: String,
    var isAdopted: Boolean,
    var age: Int,
    var image: String,
    val gender: String
)

