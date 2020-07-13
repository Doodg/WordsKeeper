package com.enigma.wordskeeper.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.enigma.wordskeeper.R
import com.enigma.wordskeeper.decrypt
import com.enigma.wordskeeper.repository.model.WordModel
import com.enigma.wordskeeper.repository.database.WordsKeeperDatabase
import com.enigma.wordskeeper.ui.home.HomeActivity


class WordUnLockJob(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    private val WORD_CREATED_TIME = "word_creationDate"

    override fun doWork(): Result {
        val createtionData = inputData.getLong(WORD_CREATED_TIME, -1)
        val wordmodel =
            WordsKeeperDatabase.getInstance(context = context).wordDao().getWord(createtionData)
        WordsKeeperDatabase.getInstance(context = context).wordDao()
            .saveWord(
                WordModel(
                    id = wordmodel.id,
                    word = wordmodel.word.decrypt(),
                    creationDate = wordmodel.creationDate,
                    isWordOpened = true
                )
            )
        displayNotification(wordmodel.word)
        return Result.success()
    }

    private fun displayNotification(word: String) {

        val contentPendingIntent = PendingIntent.getActivity(
            context, 0, Intent(
                context,
                HomeActivity::class.java
            ),
            PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "wordKeeper",
                "wordKeeper",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification =
            NotificationCompat.Builder(applicationContext, "wordKeeper")
                .setContentTitle("Word is ready to open")
                .setContentText(word.decrypt())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(contentPendingIntent)
        notificationManager.notify(1, notification.build())
    }
}