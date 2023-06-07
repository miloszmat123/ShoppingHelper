package com.example.shoppinghelper.tagreader

import android.app.Activity
import android.content.Context
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef

class NFCManager(private val context: Context, private val nfcAdapter: NfcAdapter?) :
    NfcAdapter.ReaderCallback {


    var onTagDiscovered: ((tag: Tag) -> Unit)? = null

    fun enableNFC() {
        nfcAdapter?.enableReaderMode(
            context as Activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B or NfcAdapter.FLAG_READER_NFC_F or NfcAdapter.FLAG_READER_NFC_V,
            null
        )
    }

    fun disableNFC() {
        nfcAdapter?.disableReaderMode(context as Activity)
    }

    override fun onTagDiscovered(tag: Tag) {
        onTagDiscovered?.invoke(tag)
    }

    fun readNFC(tag: Tag): String? {
        val ndef = Ndef.get(tag)
        val ndefMessage = ndef?.cachedNdefMessage
        val data = ndefMessage?.records?.getOrNull(0)?.payload // Assuming there is only one record
        return data?.decodeToString()
    }



    fun writeNFC(tag: Tag, message: String) {
        val ndef = Ndef.get(tag)
        try {
            ndef.connect()
            val ndefRecord = NdefRecord.createTextRecord(null, message)
            val ndefMessage = NdefMessage(arrayOf(ndefRecord))
            ndef.writeNdefMessage(ndefMessage)
        } catch (e: Exception) {
            // Log the exception
            e.printStackTrace()
        } finally {
            try {
                ndef.close()
            } catch (e: Exception) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }


}
