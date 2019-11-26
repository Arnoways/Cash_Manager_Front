package com.example.cashmanagerfront.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.helpers.Utils
import com.example.cashmanagerfront.objects.Cart
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color

import kotlinx.android.synthetic.main.activity_payment.*
import com.example.cashmanagerfront.objects.Payment
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Payment : AppCompatActivity(), NfcAdapter.ReaderCallback {

    /*
        Barcode variables
     */
    private val LOG_TAG = "Barcode Scanner API"
    private val PHOTO_REQUEST = 10
    private var scanResults: String? = null
    private var decode: String? = null
    private var detector: BarcodeDetector? = null
    private var imageUri: Uri? = null
    private val SAVED_INSTANCE_URI = "uri"
    private val SAVED_INSTANCE_RESULT = "result"
    private var currImagePath: String? = null
    internal var imageFile: File? = null

    /*
        NFC variables
     */
    private var nfcAdapter: NfcAdapter? = null
    private val LOG_NFC = "NFC Reader API"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // set the status to undefined
        paymentStatusText.setText("Payment status : " + Payment.display())
        generateQrCode()

        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI))
            scanResults = savedInstanceState.getString(SAVED_INSTANCE_RESULT)
        }

        // Barcode detector
        detector = BarcodeDetector.Builder(applicationContext)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        // NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
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
        Log.e(LOG_NFC, "\nCard Response: " + Utils.toHex(response))
        isoDep.close()
    }

    override fun onStart() {
        super.onStart()

        // Enable NFC
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)

        // set the status to the good one
        paymentStatusText.setText("Payment status : " + Payment.display())
    }

    /*
     This function use the AwesomeQrCode generator to generate a new qr code with the total price with taxes to pay.
     */
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
                    // the user clicked on the qrcode so he wants to pay with cheques
                    // send to api the method of payment
                    takePicture()
                }
            } else {
                // Oops, something gone wrong.
                Toast.makeText(
                    this,
                    "Error generating QRCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Oops, something gone wrong.
            Toast.makeText(
                this,
                "Error generating QRCode",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
            val mediaScanIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            mediaScanIntent.data = imageUri
            launchMediaScanIntent(mediaScanIntent)
            try {
                val bitmap = decodeBitmapUri(this, imageUri)
                if (detector!!.isOperational && bitmap != null) {
                    val frame = Frame.Builder().setBitmap(bitmap).build()
                    val barcodes = detector!!.detect(frame)
                    for (index in 0 until barcodes.size()) {
                        val code = barcodes.valueAt(index)
                        scanResults = scanResults.toString() + code.displayValue
                        val type = barcodes.valueAt(index).valueFormat
                        when (type) {
                            Barcode.CONTACT_INFO -> Log.i(LOG_TAG, code.contactInfo.title)
                            Barcode.TEXT -> Log.i(LOG_TAG, code.rawValue)
                            else -> Log.i(LOG_TAG, code.rawValue)
                        }
                    }
                    if (barcodes.size() == 0) {
                        scanResults = "Scan Failed"
                    }
                } else {
                    scanResults = "Could not set up the Barcode detector!"
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                    .show()
                Log.e(LOG_TAG, e.toString())
            }

        }

        Log.e(LOG_TAG, scanResults.toString())
    }

    /*
     This function launch the camera and display what's needed to take take a picture
     */
    private fun takePicture() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            imageFile = createImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var authorities: String = applicationContext.packageName

        imageUri = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            Uri.fromFile(imageFile)
        } else {
            FileProvider.getUriForFile(this, authorities, imageFile!!)
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, PHOTO_REQUEST)
        }

    }

    /*
     Generate a file to store the qr code
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir = File(getExternalFilesDir(null), "picture.jpg")
        if (!storageDir.exists()) {
            storageDir.parentFile.mkdirs()
            storageDir.createNewFile()
        }
        currImagePath = storageDir.absolutePath
        return storageDir
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (imageUri != null) {
            Log.d(SAVED_INSTANCE_URI, imageUri!!.toString())
            Log.d(SAVED_INSTANCE_RESULT, scanResults.toString())
        }
    }

    private fun launchMediaScanIntent(mediaScanIntent: Intent) {

        this.sendBroadcast(mediaScanIntent)
    }

    /*
     Decode the qr code previously taken in photo
     */
    @Throws(FileNotFoundException::class)
    private fun decodeBitmapUri(ctx: Context, uri: Uri?): Bitmap? {

        val targetW = 600
        val targetH = 600
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true

        BitmapFactory.decodeStream(ctx.contentResolver.openInputStream(uri!!), null, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        return BitmapFactory.decodeStream(ctx.contentResolver
            .openInputStream(uri), null, bmOptions)
    }

}
