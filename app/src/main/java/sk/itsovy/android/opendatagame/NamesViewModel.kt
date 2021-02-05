package sk.itsovy.android.opendatagame

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    /**
     * z hlavneho zoznamu vrati podzoznam dlzky COUNT
     * ktory bude nahodny a zamiesany
     */
    fun getRandomList(count: Int) : List<Record> {
        val cachedData = data.value ?: defaultData
        val shuffledList = cachedData.shuffled()
        return shuffledList.subList(0, min(count, shuffledList.size))
    }
}