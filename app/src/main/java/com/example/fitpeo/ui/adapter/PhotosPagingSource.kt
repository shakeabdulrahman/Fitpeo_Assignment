package com.example.fitpeo.ui.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fitpeo.base.PrivateSharedPrefManager
import com.example.fitpeo.data.ApiService
import com.example.fitpeo.data.model.Photos
import javax.inject.Inject

class PhotosPagingSource(private val service: ApiService) : PagingSource<Int, Photos>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            val response = service.getPhotos(pageNumber, pageSize)
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            val nextKey = if (response.isEmpty()) null else pageNumber + 1
            LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            Log.d("SERVICE_ERROR", e.message.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}