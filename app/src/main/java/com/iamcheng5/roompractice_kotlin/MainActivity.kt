package com.iamcheng5.roompractice_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val wordListAdapter = WordListAdapter(object : WordListAdapter.ItemClickListener {
            override fun onDeleteClick(word: Word) {
                wordViewModel.delete(word)
            }
        })
        recyclerView.adapter = wordListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allWords.observe(this, { words ->
            wordListAdapter.submitList(words)
        })

        val newWordActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    wordViewModel.insert(Word(result.data!!.getStringExtra(NewWordActivity.EXTRA_REPLY)!!))
                } else {
                    Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG)
                        .show()
                }
            }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            newWordActivityLauncher.launch(Intent(this, NewWordActivity::class.java))
        }

        val toggleButton = findViewById<ToggleButton>(R.id.toggle_btn)
        toggleButton.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
            wordViewModel.sortMethod.value = if (b) Sort.ASC else Sort.DESC
        }

    }
}
