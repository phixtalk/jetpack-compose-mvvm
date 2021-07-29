package com.mvvmcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.mvvmcompose.data.remote.ServiceBuilder
import com.mvvmcompose.pokemondetail.PokemonDetailScreen
import com.mvvmcompose.pokemondetail.PokemonDetailViewModel
import com.mvvmcompose.pokemonlist.PokemonListViewModel
import com.mvvmcompose.repository.PokemonRepository
import com.mvvmcompose.pokemonlist.PokemonListScreen
import com.mvvmcompose.ui.theme.MVVMComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //val api = ServiceBuilder.providePokeApi()

            //val repository = PokemonRepository(api = api)

            //val viewModelList = PokemonListViewModel(repository = repository)

            //val viewModelDetail = PokemonDetailViewModel(repository = repository)

            MVVMComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    //create first composable screen
                    composable("pokemon_list_screen"){
                        PokemonListScreen(
                            navController = navController
                        )
                    }
                    composable(
                        "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor"){
                                type = NavType.IntType
                            },
                            navArgument("pokemonName"){
                                type = NavType.StringType
                            }
                        )
                    ){
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}