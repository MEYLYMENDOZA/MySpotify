package dev.lchang.appue.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

// **********************************************
// 1. MODELOS DE DATOS Y DATOS DE PRUEBA (MOCK DATA)
// **********************************************

// Clase para representar una canción
data class Song(
    val title: String,
    val artist: String,
    val albumCoverUrl: String,
    val isNewRelease: Boolean = false,
    val isPlaying: Boolean = false
)

// PORTADA PRINCIPAL Y MINIATURAS (Bebe Rexha - Better Mistakes)
// URL de Deezer para la portada de Better Mistakes (el dominio compatible)
const val BEBE_REXHA_COVER_URL = "https://cdn-images.dzcdn.net/images/cover/60edd94332c8fbd8b0f48f7bfd5fe6b8/1900x1900-000000-81-0-0.jpg"

// URL Alternativa funcional (Puedes usarla o no para las miniaturas)
const val ALTERNATE_COVER_URL = "https://cdn-images.dzcdn.net/images/cover/60edd94332c8fbd8b0f48f7bfd5fe6b8/1900x1900-000000-81-0-0.jpg"

// Lista de canciones simuladas (MOCK DATA)
val mockPlaylistSongs = listOf(
    Song("Sacrifice", "Bebe Rexha", BEBE_REXHA_COVER_URL),
    Song("Sabotage", "Bebe Rexha", ALTERNATE_COVER_URL),
    Song("Better Mistakes", "Bebe Rexha", BEBE_REXHA_COVER_URL, isPlaying = true), // Reproduciéndose
    Song("Die For A Man (feat. Lil Uzi Vert)", "Bebe Rexha", BEBE_REXHA_COVER_URL),
    Song("Baby I'm Jealous (feat. Doja Cat)", "Bebe Rexha", ALTERNATE_COVER_URL),
    Song("Empty", "Bebe Rexha", BEBE_REXHA_COVER_URL),
    Song("My Dear Love (feat. Ty Dolla )", "Bebe Rexha", BEBE_REXHA_COVER_URL),
)

// **********************************************
// 2. FUNCIÓN PRINCIPAL DE LA PANTALLA
// **********************************************

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = { SpotifyBottomNavigation() },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black)
            ) {
                item {
                    PlaylistHeader()
                }
                item {
                    PlayButtonRow()
                }
                items(mockPlaylistSongs) { song ->
                    SongListItem(song = song)
                }
                item {
                    Spacer(Modifier.height(70.dp))
                }
            }
        }
    )
}

// **********************************************
// 3. COMPONENTES REUTILIZABLES
// **********************************************

@Composable
fun PlaylistHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        // Imagen de la portada - USA LA URL DE DEEZER DE BEBE REXHA
        Image(
            painter = rememberAsyncImagePainter(BEBE_REXHA_COVER_URL),
            contentDescription = "Cover: Bebe Rexha - Better Mistakes",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Columna para el texto del título
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Bebe Rexha - Better Mistakes",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Segundo álbum de estudio de Bebe Rexha (2021).",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícono de Spotify (Placeholder)
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Spotify",
                    tint = Color(0xFF1DB954),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Bebe Rexha • 800,000 saves • 35m",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun PlayButtonRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(end = 16.dp)
        )
        FloatingActionButton(
            onClick = { /* TODO: Play playlist */ },
            containerColor = Color(0xFF1DB954),
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun SongListItem(song: Song) {
    val backgroundColor = if (song.isPlaying) Color(0xFF8B0000) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del álbum
        Image(
            painter = rememberAsyncImagePainter(song.albumCoverUrl),
            contentDescription = song.title,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        // Columna de título y artista
        Column(modifier = Modifier.weight(1f)) {
            if (song.isNewRelease) {
                Text(
                    text = "New release",
                    color = Color(0xFF1DB954),
                    fontSize = 10.sp
                )
            }
            Text(
                text = song.title,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (song.isPlaying) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = song.artist,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Ícono de opciones de canción
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun SpotifyBottomNavigation() {
    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier.height(70.dp)
    ) {
        val items = listOf("Home", "Search", "Your Library")
        val icons = listOf(Icons.Default.PlayArrow, Icons.Default.MoreVert, Icons.Default.MoreVert)

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item, modifier = Modifier.size(24.dp)) },
                label = { Text(item) },
                selected = item == "Home",
                onClick = { /* TODO: Handle navigation */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.7f),
                    indicatorColor = Color.Black
                )
            )
        }
    }
}
