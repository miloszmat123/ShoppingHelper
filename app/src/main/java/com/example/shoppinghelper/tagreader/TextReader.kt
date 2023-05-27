package com.example.shoppinghelper.tagreader

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.Locale

class TextReader(
    private val context: Context
) : OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private var text: String? = null

    fun speakText(text: String) {
        this.text = text
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = Locale.getDefault()
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}