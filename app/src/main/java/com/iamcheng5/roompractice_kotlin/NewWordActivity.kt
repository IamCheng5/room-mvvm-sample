package com.iamcheng5.roompractice_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        val etEditWord = findViewById<EditText>(R.id.newWordAct_et_edit_word)
        val btnSave = findViewById<Button>(R.id.newWordAct_btn_save)
        btnSave.setOnClickListener {
            val replyIntent = Intent()
            val text = etEditWord.text.toString().trim()
            if (TextUtils.isEmpty(text)) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY, text)
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY: String = "com.iamcheng5.roompractice_kotlin.REPLY"
    }
}