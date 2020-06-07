package com.enigma.wordskeeper.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.enigma.wordskeeper.*
import com.enigma.wordskeeper.addwords.AddWordActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity() {
    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    private lateinit var wordListAdapter: WordsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordListAdapter =
            WordsListAdapter(onWordClickedListener = { itemPosition, itemWord ->
                onWordClicked(
                    itemWord
                )
            })
        wordsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordListAdapter

        }
        addNewWordFab.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }
        observeOnWords()
    }

    private fun onWordClicked(itemWord: WordModel) {
        val intent = Intent(this, AddWordActivity::class.java)
        intent.putExtra("EXTRA_WORD", itemWord)
        startActivity(intent)
    }

    private fun observeOnWords() {
        homeViewModel.getListOfWords().observe(this, Observer {
            if (it == null) {
                wordsRecyclerView.visibility = View.GONE
                noWordTextView.visibility = View.VISIBLE
            } else {
                wordListAdapter.submitList(it)
                noWordTextView.visibility = View.GONE
                wordsRecyclerView.visibility = View.VISIBLE
            }
        })
    }
}
