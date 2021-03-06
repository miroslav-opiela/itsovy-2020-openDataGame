package sk.itsovy.android.opendatagame

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnImageClickListener {

    var isPlaying = false
    lateinit var adapter: NamesAdapter
    val model: NamesViewModel by viewModels() {
        NamesViewModelFactory((application as NamesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //T!  bud T alebo T?
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            onFabClicked()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNames)
        adapter = NamesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemTouchHelper.attachToRecyclerView(recyclerView)

        model.data.observe(this, Observer { records ->
            records?.let {
                Log.d("OBSERVER", it.toString())
            }
        })
    }

    private fun onFabClicked() {
        if (!isPlaying) {
            // ZACINA NOVA HRA

            // vyrobit novy zoznam a data dat adapter
            val list = model.getRandomList(count = 4)
            adapter.data = list
        } else {
            // VYHODNITIME HRU
            if (adapter.win()) {
                Toast.makeText(this, "WINNER", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "LOSER", Toast.LENGTH_LONG).show()
            }


            //model.insert(Record("Andrej", 4000000))
        }
        // isPlaying=false -> visibleCounts=false
        adapter.visibleCounts = isPlaying
        isPlaying = !isPlaying
    }

    private val itemTouchHelper by lazy {

        var simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN
                        or ItemTouchHelper.START or ItemTouchHelper.END, 0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = recyclerView.adapter as NamesAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //nothing
                }

            }

        //return@lazy
        ItemTouchHelper(simpleItemTouchCallback)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                model.reloadNames()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onImageClick(viewHolder: NamesAdapter.NamesViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}