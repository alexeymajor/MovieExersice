package ru.avm.movieexersice

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button

class QuitDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_quit)

        findViewById<Button>(R.id.confirmQuit).setOnClickListener { cancel() }
        findViewById<Button>(R.id.rejectQuit).setOnClickListener { dismiss() }
    }

}