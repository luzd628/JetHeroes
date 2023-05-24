package com.dzul.jetheroes

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dzul.jetheroes.model.HeroesData
import com.dzul.jetheroes.ui.theme.JetHeroesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetHeroesApp(
    modifier: Modifier = Modifier,
) {

    //variabel untuk mensortir data berdasarkan inisial(huruf awal)
    val groupedHeroes = HeroesData.heroes
        .sortedBy { it.name }
        .groupBy { it.name[0] }

    Box(modifier = modifier) {

        val scope = rememberCoroutineScope() // untuk menjalankan suspend function
        val listState = rememberLazyListState() // state lazy list untuk mengontrol & baca posisi item
        val showButton : Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn (
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)

         ){
            groupedHeroes.forEach{(initial,heroes)->

                stickyHeader {
                    CharacterHeader(initial)
                }

                items(heroes, key = {it.id}) {hero ->
                    HeroListItem(
                        name = hero.name,
                        photoUrl =hero.photoUrl,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically (),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }

    }
}

//fungsi untuk membaut item list menampilkan nama dan foto
@Composable
fun HeroListItem(
    name : String,
    photoUrl:String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {  }
    ) {
        AsyncImage(
            model = photoUrl ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )

        Text(
            text =name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )

    }
}

//Fungsi untuk membuat scrolltoTopButton
@Composable
fun ScrollToTopButton(
    onClick : () ->Unit,
    modifier: Modifier = Modifier
){

    Button(
        onClick = onClick,
        modifier = modifier
            //shadow memberikan effek bayangan:
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        //menambahkan warna pada button:
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {

        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
        )

    }

}


//Menambahkan sticky header
@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListPreview(){
    JetHeroesTheme {
        HeroListItem(
            name ="H.O.S. Cokroaminoto" ,
            photoUrl = "",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesPreview(){
    JetHeroesTheme {
        JetHeroesApp()
    }
}