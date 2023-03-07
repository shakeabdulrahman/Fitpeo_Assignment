package com.example.fitpeo.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.ConcatAdapter
import com.example.fitpeo.base.PrivateSharedPrefManager
import com.example.fitpeo.common.Constants.Companion.CURRENT_POSITION
import com.example.fitpeo.data.model.Photos
import com.example.fitpeo.data.repository.MainRepository
import com.example.fitpeo.ui.adapter.PhotosAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    @Inject
    lateinit var privateSharedPreferencesManager: PrivateSharedPrefManager

    var adapter: PhotosAdapter? = null
    var concatAdapter: ConcatAdapter? = null
    var selectedPhoto: Photos = Photos()


    fun onPhotoSelected(photos: Photos) {
        selectedPhoto = photos
    }

    fun collectUiState() {
        viewModelScope.launch {
            getPhotosList().collectLatest { photos ->
                adapter?.submitData(photos)
            }
        }
    }

    private fun getPhotosList(): Flow<PagingData<Photos>> {
        return repository.getPhotosList().cachedIn(viewModelScope)
    }
}