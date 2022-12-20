package com.example.constun.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.PermissionRequest
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.constun.R
import com.example.constun.model.canstat
import com.example.constun.utils.UploadRequestBody
import com.example.constun.utils.getFileName
import com.example.constun.utils.snackbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_creat3.*
import kotlinx.android.synthetic.main.activity_create1.*
import kotlinx.android.synthetic.main.activity_create1.img1
import kotlinx.android.synthetic.main.activity_create1.save
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class CreateActivity1 : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private val mArrayUri = arrayListOf<Uri>()
    private var image: Uri? = null
    lateinit var nomA: EditText
    lateinit var matriculA: EditText
    lateinit var codeA: EditText
    lateinit var cinA: EditText
    lateinit var numA: EditText
    lateinit var nomB: EditText
    lateinit var matriculB: EditText
    lateinit var codeB: EditText
    lateinit var cinB: EditText
    lateinit var numB: EditText
    lateinit var image1: ImageView
    lateinit var image2: ImageView
    lateinit var location: EditText
    lateinit var mSharedPref: SharedPreferences
    lateinit var locationManager: LocationManager
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var editQR1: EditText
    lateinit var btnScan: Button
    lateinit var btnsms: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1)
        nomA = findViewById(R.id.nomA)
        matriculA = findViewById(R.id.matriculeA)
        codeA = findViewById(R.id.codeA)
        cinA = findViewById(R.id.cinA)
        numA = findViewById(R.id.NumTelA)
        nomB = findViewById(R.id.nomB)
        matriculB = findViewById(R.id.matriculeB)
        codeB = findViewById(R.id.codeB)
        cinB = findViewById(R.id.cinB)
        numB = findViewById(R.id.NumTelB)
        image1 = findViewById(R.id.img1)
        image2 = findViewById(R.id.img2)
        location = findViewById(R.id.editTextLocation)
        editQR1 = findViewById(R.id.editQR1)
        btnScan = findViewById(R.id.btnScan)
        btnsms = findViewById(R.id.sms)


        btnScan.setOnClickListener {
            val scanActivityIntent = Intent(this, scanActivity::class.java)
            startActivity(scanActivityIntent)
        }
        editQR1.setText(getIntent().getStringExtra("data"));
        val separated: List<String> = editQR1.text.split(":")
        matriculB.setText(separated[0])
        codeB.setText(separated[1])
        cinB.setText(separated[2])
        numB.setText(separated[3])
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        button.setOnClickListener {
            checkLocationPermission()
        }

        img1.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
           // startActivityForResult(gallery)
        }
        save.setOnClickListener {
            uploadImage()
        }
        btnsms.setOnClickListener {
            SendSMS()
        }


    }

    private fun SendSMS() {
        val apiInterface = ApiInterface.create()

        apiInterface.sendSMS().enqueue(object : Callback<canstat> {
            override fun onResponse(call: Call<canstat>, response: Response<canstat>) {
                Toast.makeText(this@CreateActivity1, "Message Send!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<canstat>, t: Throwable) {
                Toast.makeText(this@CreateActivity1, "Message Send!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(it, CreateActivity1.REQUEST_CODE_IMAGE)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101)
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                location.setText("${it.latitude},${it.longitude}")
                Toast.makeText(applicationContext,
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }

    companion object {
        private const val REQUEST_CODE_IMAGE = 100
    }

    val fileArray = arrayListOf<File>()
    val bodyArray = arrayListOf<UploadRequestBody>()

    @SuppressLint("Recycle")
    private fun uploadImage() {
        for (u: Uri in mArrayUri) {
            var parcelFileDescriptor =
                contentResolver.openFileDescriptor(u, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(u))
            fileArray.add(file)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            val body = UploadRequestBody(file, "image", this)
            bodyArray.add(body)
        }
        val apiInterface = ApiInterface.create()

        apiInterface.CreateCanstat(
            nomA.text.toString(),
            matriculA.text.toString(),
            codeA.text.toString(),
            cinA.text.toString(),
            numA.text.toString(),
            nomB.text.toString(),
            matriculB.text.toString(),
            codeB.text.toString(),
            cinB.text.toString(),
            numB.text.toString(),
            location.text.toString(),
            MultipartBody.Part.createFormData("image1", fileArray[0].name, bodyArray[0]),
            MultipartBody.Part.createFormData("image2", fileArray[1].name, bodyArray[1]),
            "aa"

        ).enqueue(object : Callback<canstat> {

            override fun onResponse(call: Call<canstat>, response: Response<canstat>) {
                layout_root.snackbar(response.body()?.nomA.toString())
            }

            override fun onFailure(call: Call<canstat>, t: Throwable) {
                layout_root.snackbar(t.message!!)
            }

        })


    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CreateActivity1.REQUEST_CODE_IMAGE -> {
                    //image = data?.data
                    //image1.setImageURI(image)
                    if (data != null) {
                        if (data.clipData != null) {
                            val mClipData = data.clipData
                            val cout = data.clipData!!.itemCount
                            for (i in 0 until cout) {
                                // adding imageuri in array
                                val imageurl: Uri = data.clipData!!.getItemAt(i).uri
                                mArrayUri.add(imageurl)
                            }
                            // setting 1st selected image into image switcher
                            image1.setImageURI(mArrayUri.get(0))
                            image2.setImageURI(mArrayUri.get(1))

                            println(mArrayUri)
                        } else {
                            val imageurl: Uri = data.getData()!!
                            mArrayUri.add(imageurl)
                            image1.setImageURI(mArrayUri.get(0))

                        }
                    }
                }
            }
        }

    }

    override fun onProgressUpdate(pecentage: Int) {

    }
}