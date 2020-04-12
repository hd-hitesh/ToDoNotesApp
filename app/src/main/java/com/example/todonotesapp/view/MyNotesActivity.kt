package com.example.todonotesapp.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.clickListeners.ItemClickListeners
import com.example.todonotesapp.db.Notes
import com.example.todonotesapp.utils.StoreSession
import com.example.todonotesapp.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Time

import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {

     lateinit var fullName : String
     lateinit var fabAddNotes: FloatingActionButton
     val TAG = "MyNotesActivity"
     lateinit var recyclerViewNotes: RecyclerView
    val ADD_NOTES_CODE = 100
     var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindView()
        setUpSharedPreference()
        getIntentData()
        getDataFromDataBase()

        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               // setUpDialogBox()
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent,ADD_NOTES_CODE)
            }
        })

        // Log.d("MyNotesActivity",fullName);
        supportActionBar?.title = fullName
        setUpRecyclerView()
        setUpWorkManager()
    }

    private fun setUpWorkManager() {
        val constrain = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java,1,TimeUnit.MINUTES).setConstraints(constrain).build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        val listOfNotes = notesDao.getAll()
        Log.d(TAG,listOfNotes.size.toString())
        notesList.addAll(listOfNotes)

    }

    private fun setUpSharedPreference() {
        StoreSession.init(this)
    }

    private fun getIntentData() {
        val intent = intent
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)?:""
        if (fullName.isEmpty()) {
            fullName = StoreSession.readString(PrefConstant.FULL_NAME)!!
            Log.d(TAG, fullName)
        }
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setUpDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDesciption)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        //Dialog
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val notes = Notes(title = title,description = description)
                notesList.add(notes)
                addNotesToDb(notes)
            } else {
                Toast.makeText(this@MyNotesActivity, "Empty", Toast.LENGTH_SHORT).show()
            }
            dialog.hide()
        }
        dialog.show()
    }

    private fun addNotesToDb(notes: Notes) {
        //insertion of notes in db
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setUpRecyclerView() {
        val itemClickListeners = object : ItemClickListeners {
            override fun onUpdate(notes: Notes) {
                // update the value
                Log.d(TAG,notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }

        val notesAdapter = NotesAdapter(notesList, itemClickListeners)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       // if(resultCode ==ADD_NOTES_CODE && resultCode == Activity.RUSULT_OK){
        Log.d("Ress","+++"+resultCode.toString()+"+++")
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val notes = Notes(title = title!!,description = description!!, imagePath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
            notesList.add(notes)
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)
       // }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.blog){
            Log.d(TAG,"clicked")
            val  intent = Intent(this@MyNotesActivity , BlogActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.logout){
            Log.d(TAG,"clicked")
            StoreSession.init(this)
            StoreSession.write(PrefConstant.IS_LOGGED_IN,false)
            val  intent = Intent(this@MyNotesActivity , LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
