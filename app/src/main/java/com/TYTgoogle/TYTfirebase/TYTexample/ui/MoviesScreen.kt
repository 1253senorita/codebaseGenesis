package com.TYTgoogle.TYTfirebase.TYTexample.ui

/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.TYTgoogle.TYTfirebase.TYTexample.MainViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.serialization.Serializable

@Composable
fun MoviesScreen(
    onMovieClicked: (id: String) -> Unit,
    user: FirebaseUser?,
    mainViewModel: MainViewModel,
    onNavigateToMovieDetail: () -> Unit // 파라미터 타입 변경 권장: Function<Unit> -> () -> Unit
) {
    // TODO(developer): run the query to list movies
}


/**
 * Used to display each movie item in the list.
 */
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    tileWidth: Dp = 150.dp,
    movieId: String,
    movieTitle: String,
    movieImageUrl: String,
    movieGenre: String? = null,
    onMovieClicked: (movieId: String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .sizeIn(maxWidth = tileWidth)
            .clickable {
                onMovieClicked(movieId)
            },
    ) {
        AsyncImage(
            model = movieImageUrl,
            contentDescription = movieTitle, // contentDescription 추가 권장
            contentScale = ContentScale.Crop,
            modifier = Modifier.aspectRatio(9f / 16f)
        )
        Text(
            text = movieTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        movieGenre?.let {
            Text(
                text = it,
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// MovieItem 데이터 클래스를 여기에 정의합니다.
data class MovieItem(val id: String, val title: String)

@Serializable
object MoviesRoute