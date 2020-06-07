package com.enigma.wordskeeper

import android.util.Base64
import android.view.View
import java.text.SimpleDateFormat

fun String.encrypt(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
}

fun String.decrypt(): String {
    return String(Base64.decode(this.toByteArray(), Base64.DEFAULT))
}

fun View.hide(){
    this.visibility = View.GONE
}


fun View.show(){
    this.visibility = View.VISIBLE
}



fun Long.convertTOTimeFormat():String{
    val formatter = SimpleDateFormat("dd-M-yyyy hh:mm")
    return formatter.format(this)
}