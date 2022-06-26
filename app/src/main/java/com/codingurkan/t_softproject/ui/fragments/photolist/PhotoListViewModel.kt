package com.codingurkan.t_softproject.ui.fragments.photolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingurkan.t_softproject.model.Hit
import com.codingurkan.t_softproject.repository.PhotoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val repository: PhotoListRepository) : ViewModel() {

    val photoList = MutableLiveData<List<Hit>>()
    private var job: Job? = null
    private var errorMessage = MutableLiveData<String?>().also { it.value = null }
    val pageNumber = MutableLiveData<Int>().also { it.value = 1 }


    fun downloadPhotos(page : Int , per_page : Int) {
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = repository.photoListRequest(page , per_page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { _data ->
                        photoList.postValue(_data.hits) }
                } else {
                    errorMessage.postValue(response.message())
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}