package com.example.ToDo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ToDo.Adapter.ToDoAdapter
import com.example.ToDo.Database.NotesDatabase
import com.example.ToDo.Models.Note
import com.example.ToDo.Models.ToDoViewModel
import com.example.ToDo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ToDoAdapter.NotesClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NotesDatabase
    private lateinit var viewModel: ToDoViewModel
    private lateinit var adapter: ToDoAdapter
    private lateinit var selectedNote: Note

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note") as? Note
            if (note != null) {
                viewModel.updateNote(note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ToDoViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = NotesDatabase.getDatabase(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = ToDoAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as? Note
                if (note != null) {
                    viewModel.insertNote(note)
                }
            }
        }

        binding.FABadd.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterList(newText)
                }
                return true
            }
        })

        // Sorting menu
        binding.sortButton.setOnClickListener {
            showSortOptions(it)
        }
    }

    private fun showSortOptions(anchor: android.view.View) {
        PopupMenu(this, anchor).apply {
            menuInflater.inflate(R.menu.sort_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sort_by_date_asc -> sortByDateAsc()
                    R.id.sort_by_date_desc -> sortByDateDesc()
                }
                true
            }
            show()
        }
    }

    private fun sortByDateAsc() {
        viewModel.allnotes.value?.let { notes ->
            val sortedNotes = notes.sortedBy { it.date }
            adapter.updateList(sortedNotes)
        }
    }

    private fun sortByDateDesc() {
        viewModel.allnotes.value?.let { notes ->
            val sortedNotes = notes.sortedByDescending { it.date }
            adapter.updateList(sortedNotes)
        }
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this, AddNote::class.java)
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }




    // Implement the new method onDeleteButtonClicked from NotesClickListener interface
    override fun onDeleteButtonClicked(note: Note) {
        // Handle delete button click here
        viewModel.deleteNote(note)
    }
}
