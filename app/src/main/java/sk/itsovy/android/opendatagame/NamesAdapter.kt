package sk.itsovy.android.opendatagame

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class NamesAdapter(val listener: OnImageClickListener) :
    RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {

    var data = listOf<Record>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun moveItem(from: Int, to: Int) {
        Collections.swap(data, from, to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_layout,
            parent, false
        )
        return NamesViewHolder(view, listener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class NamesViewHolder(itemView: View, val listener: OnImageClickListener) :
        RecyclerView.ViewHolder(itemView) {

        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewCount = itemView.findViewById<TextView>(R.id.textViewNumber)
        private val image = itemView.findViewById<ImageView>(R.id.reorderIcon)

        fun bind(item: Record) {
            textViewName.text = item.name
            textViewCount.text = "Count: ${item.count}"

            image.setOnTouchListener { _, motionEvent ->

                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    listener.onImageClick(NamesViewHolder@ this)
                }

                return@setOnTouchListener true
            }

        }

    }
}