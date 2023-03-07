package com.example.fitpeo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.fitpeo.data.ApiService
import com.example.fitpeo.data.BaseRemoteDataSource
import com.example.fitpeo.data.model.Photos
import com.example.fitpeo.ui.adapter.PhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val service: ApiService) : BaseRemoteDataSource() {

    fun getPhotosList(): Flow<PagingData<Photos>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotosPagingSource(service = service)
            }
        ).flow
    }
}