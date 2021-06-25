package com.mvvmcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mvvmcompose.data.remote.ServiceBuilder
import com.mvvmcompose.pokemonlist.PokemonListViewModel
import com.mvvmcompose.repository.PokemonRepository
import com.mvvmcompose.ui.theme.HomeScreen
import com.mvvmcompose.ui.theme.MVVMComposeTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val api = ServiceBuilder.providePokeApi()

            val repository = PokemonRepository(api = api)

            val viewModel = PokemonListViewModel(repository = repository)

            MVVMComposeTheme {
                HomeScreen(viewModel)
            }
        }
    }
}