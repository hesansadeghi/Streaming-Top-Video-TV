package com.example.streaming_top_video_tv.presentation.downloads

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.uses_cases.downloads.DownloadsUseCase
import com.example.streaming_top_video_tv.models.PlayMovieModel
import com.example.streaming_top_video_tv.util.DownloadManagerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val downloadsUseCase: DownloadsUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(DownloadsUiState())
    val uiState = _uiState.asStateFlow()


    private val _playMovie = MutableStateFlow<PlayMovieModel?>(null)
    val playMovie: StateFlow<PlayMovieModel?> = _playMovie


    init {

        onEvent(DownloadsUiEvent.GetAllData)
    }


    fun onEvent(event: DownloadsUiEvent) {

        when (event) {
            is DownloadsUiEvent.DeleteDownloadedMovie -> deleteDownloadedMovie(event.entity)
            DownloadsUiEvent.GetAllData -> getAllData()
            is DownloadsUiEvent.PlayMovie -> {
                _playMovie.value =
                    PlayMovieModel(event.id, event.url, event.titleMovie)
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun deleteDownloadedMovie(entity: DownloadedMovieEntity) = viewModelScope.launch(
        Dispatchers.IO
    ) {

        val deleteDownloadedMovieUseDeferred =
            async { downloadsUseCase.deleteDownloadedMovieUseCase(entity) }

        deleteDownloadedMovieUseDeferred.await()

        getAllData()

        DownloadManagerUtil.getDownloadManager(context = context).removeDownload(entity.movieId)
    }


    fun getAllData() = viewModelScope.launch {

        val getAllDownloadedMoviesDeferred = async { downloadsUseCase.getAllDownloadedMoviesCase() }
        val getAllDownloadedMoviesResult = getAllDownloadedMoviesDeferred.await()

        _uiState.value = _uiState.value.copy(

            downloadedMovies = getAllDownloadedMoviesResult
        )


    }


}