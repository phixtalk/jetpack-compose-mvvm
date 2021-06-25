package com.mvvmcompose.ui.theme

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.request.ImageRequest
//import com.google.accompanist.coil.CoilImage
import com.mvvmcompose.R
import com.mvvmcompose.data.models.PokedexListEntry
import com.mvvmcompose.pokemonlist.PokemonListViewModel
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.NonDisposableHandle.parent


@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: PokemonListViewModel) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(viewModel)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )
        if(isHintDisplayed){
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonList(viewModel: PokemonListViewModel) {

    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, bottom = 100.dp),
//        modifier = Modifier.fillMaxHeight()
    ) {
        var itemCount = if (pokemonList.size % 2 == 0){//since we are using a grid, to get the current item position
            pokemonList.size / 2
        }else{
            pokemonList.size / 2 + 1 //to account for the extra entry
        }

        items(pokemonList.size) {
            if(it >= itemCount - 1 && !endReached){//check if we have scrolled to the bottom of page
                viewModel.loadPokemonPaginated()
            }

            PokemonItem(
                entry = pokemonList[it],
                modifier = Modifier.padding(12.dp),
                viewModel = viewModel
            )
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
//        if(loadError.isNotEmpty()) {
//            RetrySection(error = loadError) {
//                viewModel.loadPokemonPaginated()
//            }
//        }
    }
}

@Composable
fun PokemonItem(
    entry: PokedexListEntry,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel,
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
//                navController.navigate(
//                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
//                )
            }

    ){
        Column {
            CoilImage(
                imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .target{
                        viewModel.calcDominantColor(it){ color ->
                            dominantColor = color
                        }
                    }
                    .crossfade(true)
                    .build(),

                //enable this for devices with low memory, so images will not be caached to devices, only when they are visible
//                imageLoader = ImageLoader.Builder(LocalContext.current)
//                    .availableMemoryPercentage(0.25)
//                    .crossfade(true)
//                    .build(),

                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                // shows a progress indicator when loading an image.
                loading = {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val indicator = createRef()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(indicator) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                },
                // shows an error text message when request failed.
                failure = {
                    Text(text = "image request failed.")
                })


            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
