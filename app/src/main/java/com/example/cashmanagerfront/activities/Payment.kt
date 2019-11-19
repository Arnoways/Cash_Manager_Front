package com.example.cashmanagerfront.activities

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.objects.Cart
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.background.GifBackground
import com.github.sumimakito.awesomeqr.option.color.Color
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.activity_payment.*
import java.io.File

class Payment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        generateQrCode()
    }

    fun generateQrCode() {
        val color = Color()
        color.light = 0xFFFFFFFF.toInt() // for blank spaces
        color.dark = 0xFFFF8C8C.toInt() // for non-blank spaces
        color.background = 0xFFFFFFFF.toInt() // for the background (will be overriden by background images, if set)
        color.auto = false // set to true to automatically pick out colors from the background image (will only work if background image is present)

        val renderOption = RenderOption()
        renderOption.content = Cart.getTotalTtc().toString() // content to encode
        renderOption.size = 800 // size of the final QR code image
        renderOption.borderWidth = 20 // width of the empty space around the QR code
        renderOption.ecl = ErrorCorrectionLevel.M // (optional) specify an error correction level
        renderOption.patternScale = 0.35f // (optional) specify a scale for patterns
        renderOption.roundedPatterns = true // (optional) if true, blocks will be drawn as dots instead
        renderOption.clearBorder = true // if set to true, the background will NOT be drawn on the border area
        renderOption.color = color // set a color palette for the QR code

        try {
            val result = AwesomeQrRenderer.render(renderOption)
            if (result.bitmap != null) {
                // play with the bitmap
                paymentQrCodeImageView.setImageBitmap(result.bitmap)
                paymentQrCodeImageView.setOnClickListener {
                    startActivity(Intent(this, QRCodeReader::class.java))
                }
            } else {
                // Oops, something gone wrong.
                println("ERROR PROCESSING QR CODE 2")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Oops, something gone wrong.
        }
    }
}
