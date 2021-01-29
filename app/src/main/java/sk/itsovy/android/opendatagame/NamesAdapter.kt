package sk.itsovy.android.opendatagame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class NamesAdapter : RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {

    val data = mutableListOf<Record>(
        Record("Anton", 4000),
        Record("Milan", 1000),
        Record(name="Filip", count=2500),
        Record("Rastislav", 500),
        Record("Tibor", 150),
        Record("Stefan", 350)
    )

    fun moveItem(from: Int, to: Int) {
        Collections.swap(data, from, to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout,
            parent, false)
        return NamesViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class NamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewCount = itemView.findViewById<TextView>(R.id.textViewNumber)

        fun bind(item: Record) {
            textViewName.text = item.name
            textViewCount.text = "Count: ${item.count}"
        }

    }
}