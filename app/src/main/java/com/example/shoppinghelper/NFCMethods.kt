package com.example.shoppinghelper;

import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.util.concurrent.CountDownLatch

class NFCMethods(private val context: Context) {
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
    private var tagListener: NfcAdapter.ReaderCallback? = null

    private val nfcManager: NFCManager = NFCManager(context, nfcAdapter)

    fun processNfcTag(): String? {
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC is not supported on this device.", Toast.LENGTH_SHORT)
                .show()
            return null
        }

        // Check if NFC is enabled
        if (!nfcAdapter.isEnabled) {
            Toast.makeText(context, "Please enable NFC.", Toast.LENGTH_SHORT).show()
            return null
        }

        // Enable foreground dispatch to capture NFC tag
        nfcManager.enableNFC()

        // Wait for the NFC tag to be scanned
        val tag = waitForTag()

        nfcManager.disableNFC()

        if (tag != null) {
            // Read the NFC tag
            val message = nfcManager.readNFC(tag)
            Toast.makeText(context, "Message from tag: $message", Toast.LENGTH_SHORT).show()
            return message
        }

        Toast.makeText(context, "No NFC tag found.", Toast.LENGTH_SHORT).show()
        return null
    }


    private fun waitForTag(): Tag? {
        val tagLatch = CountDownLatch(1)
        var discoveredTag: Tag? = null

        // Register the onTagDiscovered callback
        nfcManager.onTagDiscovered = { tag ->
            discoveredTag = tag
            tagLatch.countDown()
        }

        try {
            tagLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Clear the onTagDiscovered callback
        nfcManager.onTagDiscovered = null

        return discoveredTag
    }


}
