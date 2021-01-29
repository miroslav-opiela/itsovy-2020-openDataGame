package sk.itsovy.android.opendatagame

import androidx.lifecycle.ViewModel

class NamesViewModel : ViewModel() {

    val data = mutableListOf<Record>(
        Record("Anton", 4000),
        Record("Milan", 1000),
        Record(name = "Filip", count = 2500),
        Record("Rastislav", 500),
        Record("Tibor", 150),
        Record("Stefan", 350)
    )

    /**
     * z hlavneho zoznamu vrati podzoznam dlzky COUNT
     * ktory bude nahodny a zamiesany
     */
    fun getRandomList(count: Int) : List<Record> {
        data.shuffle()
        // alternativne vlozit do novej premennej vysledok
        // data.shuffled() ak nechceme modifikovat data
        return data.subList(0, count)
    }
}