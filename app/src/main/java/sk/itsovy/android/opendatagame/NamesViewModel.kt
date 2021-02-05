package sk.itsovy.android.opendatagame

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlin.math.min

class NamesViewModel(private val repository: NamesRepository) : ViewModel() {

    val defaultData = mutableListOf<Record>(
        Record("Anton", 4000),
        Record("Milan", 1000),
        Record(name = "Filip", count = 2500),
        Record("Rastislav", 500),
        Record("Tibor", 150),
        Record("Stefan", 350)
    )

    val data: LiveData<List<Record>> = repository.allNames.asLiveData()

    fun insert(record: Record) = viewModelScope.launch {
        repository.insert(record)
    }

    /**
     * z hlavneho zoznamu vrati podzoznam dlzky COUNT
     * ktory bude nahodny a zamiesany
     */
    fun getRandomList(count: Int): List<Record> {
        Log.d("DATABASE", data.value.toString())
        val cachedData = data.value ?: defaultData
        val shuffledList = cachedData.shuffled()
        return shuffledList.subList(0, min(count, shuffledList.size))
    }
}

class NamesViewModelFactory(private val repository: NamesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NamesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}