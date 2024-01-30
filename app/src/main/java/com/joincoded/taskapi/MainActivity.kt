package com.joincoded.taskapi


import android.annotation.SuppressLint
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.modifier.modifierLocalMapOf

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.joincoded.taskapi.model.Pet
import com.joincoded.taskapi.ui.theme.TaskAPITheme
import com.joincoded.taskapi.viewModel.PetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun PetListScreen(viewModel: PetViewModel) {
    val pets = viewModel.pets
    LazyColumn {
        items(pets) { pet ->
            petItem(pet)
        }
    }
}

@Composable
fun petItem(pet: Pet, petViewModel: PetViewModel = viewModel()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(260.dp),
        shape = RoundedCornerShape(30.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),

            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name: ${pet.name}", fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )

            AsyncImage(
                model = pet.image,
                contentDescription = null,

                modifier = Modifier
                    .height(110.dp)
                    .width(110.dp)
                    .clip(shape = RoundedCornerShape(40.dp))


            )

        }

        Column(modifier = Modifier.padding(5.dp)) {

            Text(
                text = "ID#:${pet.id}",
                fontSize = 21.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )
            Text(
                text = "Gender: ${pet.gender}",
                fontSize = 21.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )

            Text(
                text = "Adopt Status: ${pet.isAdopted}",
                fontSize = 21.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )

            Text(
                text = "Age: ${pet.age}",
                fontSize = 21.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )


        }
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            Button(modifier = Modifier
                .height(100.dp)
                .width(100.dp), onClick = {
                petViewModel.deletePet(pet.id)
            }) { Text(text = "Delete",) }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val petViewModel: PetViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pet List",
            fontWeight = FontWeight.ExtraBold,
            color = Color.DarkGray, fontSize = 32.sp,
            )}) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addPet") }) {
                Text("+", fontWeight = FontWeight.SemiBold)
            }
        }
    ) { padding ->

        NavHost(navController = navController, startDestination = "petList") {
            composable("petList") {
                // Ensure BookListScreen is correctly implemented and contains visible content
                petListScreen(petViewModel, Modifier.padding(padding))
            }
            composable("addPet") {
                AddPetScreen()

            }
        }
    }
}

@Composable
fun petListScreen(viewModel: PetViewModel, modifier: Modifier = Modifier) {
    val pets = viewModel.pets

    LazyColumn(modifier = modifier) {
        items(pets) { pet ->
            petItem(pet)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen(petViewModel: PetViewModel = viewModel()) {

    //val petViewModel: PetViewModel = viewModel()

    // State variables for input fields
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isAdopted by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    Scaffold {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            InputField(
                value = age,
                onValueChange = { age = it },
                label = "Age"
            )
            InputField(
                value = isAdopted,
                onValueChange = { isAdopted = it },
                label = "Adopt",
                keyboardType = KeyboardType.Number
            )
            InputField(
                value = gender,
                onValueChange = { gender = it },
                label = "Gender"
            )
            InputField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = "Image URL"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = Pet(
                        id = 0,
                        name = name,
                        isAdopted = isAdopted.toBoolean(),
                        age = age.toInt(),
                        image = imageUrl,
                        gender = gender
                    )
                    petViewModel.addPet(newPet)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }

        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}










