package com.iamcheng5.roompractice_kotlin

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    val sortMethod: MutableLiveData<Sort> = MutableLiveData(Sort.ASC)

    val allWords: LiveData<List<Word>> = Transformations.switchMap(sortMethod) {
        if (it==Sort.ASC) repository.allWordsASC.asLiveData() else repository.allWordsDESC.asLiveData()
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

}

enum class Sort{
    ASC,DESC
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
