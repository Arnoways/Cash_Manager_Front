package com.example.cashmanagerfront.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashmanagerfront.R
import android.nfc.tech.Ndef
import android.nfc.tech.MifareUltralight
import android.nfc.tech.MifareClassic
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcV
import android.nfc.tech.NfcF
import android.nfc.tech.NfcB
import android.nfc.tech.NfcA
import android.widget.TextView
import android.os.Parcelable
import android.nfc.NfcAdapter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.content.Intent
import android.content.IntentFilter
import android.app.PendingIntent
import android.icu.text.SimpleDateFormat
import android.nfc.Tag
import android.util.Log
import com.example.cashmanagerfront.helpers.Utils
import kotlinx.android.synthetic.main.activity_choose_payement_type.*
import java.math.BigInteger
import java.util.*
import kotlin.experimental.and


class ChoosePayementType : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_payement_type)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        val response = isoDep.transceive(Utils.hexStringToByteArray(
            "00A4040007A0000002471001"))
        runOnUiThread { textView2.append("\nCard Response: "
                + Utils.toHex(response)) }
        isoDep.close()
    }
}
