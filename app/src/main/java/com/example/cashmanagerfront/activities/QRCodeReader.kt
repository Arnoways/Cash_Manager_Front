package com.example.cashmanagerfront.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.cashmanagerfront.R
import kotlinx.android.synthetic.main.activity_qrcodereader.*
import com.dlazaro66.qrcodereaderview.QRCodeReaderView as QRCodeReaderViewClass



class QRCodeReader : AppCompatActivity(), QRCodeReaderViewClass.OnQRCodeReadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodereader)

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission has already been granted
            QRCodeReaderView.setOnQRCodeReadListener(this)
            // Use this function to enable/disable decoding
            QRCodeReaderView.setQRDecodingEnabled(true)
            // Use this function to change the autofocus interval (default is 5 secs)
            QRCodeReaderView.setAutofocusInterval(2000L)
            // Use this function to enable/disable Torch
//            QRCodeReaderView.setTorchEnabled(true)
            // Use this function to set front camera preview
            QRCodeReaderView.setFrontCamera()
            // Use this function to set back camera preview
//            QRCodeReaderView.setBackCamera()

        }
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    override fun onQRCodeRead(text: String, points: Array<PointF>) {
        println("Qr Code reader =>")
        println(text)
        // stop camera
        onPause()
        // call payment activity with text

    }

    override fun onResume() {
        super.onResume()
        QRCodeReaderView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        QRCodeReaderView.stopCamera()
    }
}
