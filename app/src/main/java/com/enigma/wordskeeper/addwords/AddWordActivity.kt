package com.enigma.wordskeeper.addwords


import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.enigma.wordskeeper.*
import com.enigma.wordskeeper.R
import kotlinx.android.synthetic.main.activity_add_word.*
import java.util.*
import java.util.concurrent.TimeUnit


class AddWordActivity : AppCompatActivity() {
    private val WORD_CREATED_TIME = "word_creationDate"
    private val addWordViewModel by lazy {
        ViewModelProviders.of(this).get(AddWordViewModel::class.java)

    }
    var selectedOpenTime: Long = 0
    private var timeSlotArray = arrayListOf(1, 3, 5, 10, 15, 30)
    private lateinit var sleepTimerListAdapter: TimerListAdapter
    private  var wordModel:WordModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.add_word_title)
        createDateTextView.text = Calendar.getInstance().time.time.convertTOTimeFormat()
        if (intent.hasExtra("EXTRA_WORD")) {
            wordModel = ((intent.getSerializableExtra("EXTRA_WORD") as? WordModel))
            wordEditText.setText(wordModel?.word)
            wordEditText.isEnabled = false
            createDateTextView.text = wordModel?.creationDate?.convertTOTimeFormat()
            saveWordButton.text = getString(R.string.delete_word)
            selectedOpenTime = wordModel?.openWordAt!!

            UnlockTextView.hide()
            recyclerViewTimeSlot.hide()
        }

        sleepTimerListAdapter = TimerListAdapter(
            context = this,
            onTimeSlotChecked = { position -> onItemSelected(position) })

        recyclerViewTimeSlot.apply {
            layoutManager =
                LinearLayoutManager(this@AddWordActivity, LinearLayoutManager.VERTICAL, false)
            adapter = sleepTimerListAdapter
        }
        sleepTimerListAdapter.setTimeSlots()

        saveWordButton.setOnClickListener {
            if (wordEditText.text.isNullOrBlank()) {
                Toast.makeText(this, "the field is blank", Toast.LENGTH_LONG).show()
            } else if (selectedOpenTime <= 0 && !intent.hasExtra("EXTRA_WORD")) {
                Toast.makeText(this, "MUST select time ", Toast.LENGTH_LONG).show()
            } else {
                if (wordModel !=null) {
                    wordModel?.id?.let { it1 ->
                        addWordViewModel.deleteWord(
                            it1
                        )
                        WorkManager.getInstance(this).cancelUniqueWork(wordModel?.creationDate.toString()).toString()
                    }
                } else {
                    val creationTime = Calendar.getInstance().time.time
                    addWordViewModel.saveWord(
                        WordModel(
                            word = wordEditText.text.toString().encrypt(),
                            creationDate = creationTime,
                            openWordAt = selectedOpenTime
                        )
                    )
                    val workRequest = OneTimeWorkRequest.Builder(WordUnLockJob::class.java)
                        .setInputData(
                            Data.Builder().putLong(WORD_CREATED_TIME, creationTime)
                                .build()
                        ).setInitialDelay(selectedOpenTime, TimeUnit.MINUTES)
                        .build()
                    WorkManager.getInstance(this).enqueueUniqueWork(creationTime.toString(),ExistingWorkPolicy.REPLACE,workRequest)
                }
                Toast.makeText(this, "Saved successfully ", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    }

    private fun onItemSelected(position: Int) {
        selectedOpenTime = timeSlotArray.get(position).toLong()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}