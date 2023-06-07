package com.example.shoppinghelper.tagreader

import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.widget.Toast
import java.util.UUID
import java.util.concurrent.CountDownLatch

class NFCMethods(private val context: Context) {
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)

    private val nfcManager: NFCManager = NFCManager(context, nfcAdapter)

    fun readNfcTag(): String? {
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC is not supported on this device.", Toast.LENGTH_SHORT)
                .show()
            return null
        }
        if (!nfcAdapter.isEnabled) {
            Toast.makeText(context, "Please enable NFC.", Toast.LENGTH_SHORT).show()
            return null
        }
        nfcManager.enableNFC()

        val tag = waitForTag()
        if (tag != null) {
            return nfcManager.readNFC(tag)
        }
        nfcManager.disableNFC()
        Toast.makeText(context, "No NFC tag found.", Toast.LENGTH_SHORT).show()
        return null
    }

    fun writeNfcTag(): String? {
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC is not supported on this device.", Toast.LENGTH_SHORT)
                .show()
            return null
        }
        if (!nfcAdapter.isEnabled) {
            Toast.makeText(context, "Please enable NFC.", Toast.LENGTH_SHORT).show()
            return null
        }
        nfcManager.enableNFC()

        val tag = waitForTag()
        if (tag != null) {
            val nfcId = UUID.randomUUID().toString()
            nfcManager.writeNFC(tag, nfcId)
            return nfcId
        }
        nfcManager.disableNFC()

        Toast.makeText(context, "No NFC tag found.", Toast.LENGTH_SHORT).show()
        return null
    }


    private fun waitForTag(): Tag? {
        val tagLatch = CountDownLatch(1)
        var discoveredTag: Tag? = null
        nfcManager.onTagDiscovered = { tag ->
            discoveredTag = tag
            tagLatch.countDown()
        }
        try {
            tagLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        nfcManager.onTagDiscovered = null
        return discoveredTag
    }


}
