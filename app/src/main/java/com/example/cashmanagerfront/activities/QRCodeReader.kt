package com.example.cashmanagerfront.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.enums.PaymentMethod
import com.example.cashmanagerfront.enums.PaymentStatus
import kotlinx.android.synthetic.main.activity_qrcodereader.*
import com.dlazaro66.qrcodereaderview.QRCodeReaderView as QRCodeReaderViewClass
import com.example.cashmanagerfront.objects.Payment



class QRCodeReader : AppCompatActivity(), QRCodeReaderViewClass.OnQRCodeReadListener {

    var camera: Int = 1 // set the front camera first
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodereader)

        QRCodeReaderCamera.setOnClickListener {
            onPause()
            if (camera == 0) {
                QRCodeReaderView.setFrontCamera()
                camera = 1
            } else {
                QRCodeReaderView.setBackCamera()
                camera = 0
            }
            onResume()
        }

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
            QRCodeReaderView.setFrontCamera()

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
        // set method payment as cheque
        Payment.method = PaymentMethod.CHEQUE.value()
        Payment.status = PaymentStatus.PENDING.value()

        // call payment activity with text
        val ret = null // TODO - Call Api to get response for the cheque
        if (ret == "sucess") {
            Payment.status = PaymentStatus.AUTHORIZED.value()
        } else if (ret == "error") {
            Payment.status = PaymentStatus.REFUSED.value()
        } else {
            Payment.status = PaymentStatus.PENDING.value()
        }
        // display back payment activity
        startActivity(Intent(this, Payment::class.java))
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
