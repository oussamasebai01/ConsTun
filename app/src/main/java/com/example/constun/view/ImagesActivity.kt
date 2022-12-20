package com.example.constun.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.constun.R
import com.example.constun.model.Profile
import com.example.constun.utils.UploadRequestBody
import com.example.constun.utils.getFileName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImagesActivity : AppCompatActivity() , UploadRequestBody.UploadCallback {
    lateinit var image_cin : ImageView
    lateinit var image_permis : ImageView
    lateinit var image_carte : ImageView
    lateinit var image_attestation : ImageView
    lateinit var select : Button
    lateinit var btn : Button
    lateinit var txt_cin : TextView
    private var REQUEST_CODE_IMAGE = 100
    private val mArrayUri = arrayListOf<Uri>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        image_permis=findViewById(R.id.image_permis)
        image_cin=findViewById(R.id.image_cin)
        image_carte=findViewById(R.id.image_carte)
        image_attestation=findViewById(R.id.image_attestation)
        btn = findViewById(R.id.upload)
        txt_cin = findViewById(R.id.txt_cin)
        select = findViewById(R.id.select)
        select.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            gallery.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(gallery,REQUEST_CODE_IMAGE)
        }
        btn.setOnClickListener {
            save()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
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
                            image_cin.setImageURI(mArrayUri.get(0))
                            image_carte.setImageURI(mArrayUri.get(1))
                            image_permis.setImageURI(mArrayUri.get(2))
                            image_attestation.setImageURI(mArrayUri.get(3))

                            //println(mArrayUri)
                        } else {
                            val imageurl: Uri = data.getData()!!
                            mArrayUri.add(imageurl)
                           // image1.setImageURI(mArrayUri.get(0))

                        }
                    }
                }
            }
        }

    }
    val fileArray = arrayListOf<File>()
    val bodyArray = arrayListOf<UploadRequestBody>()
    private fun save(){
        for (u :Uri in mArrayUri){
            var parcelFileDescriptor = contentResolver.openFileDescriptor(u,"r",null) ?:return
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor);
            val file = File(cacheDir, contentResolver.getFileName(u))
            fileArray.add(file)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            val body = UploadRequestBody(file, "image",this)
            bodyArray.add(body)
        }

        val apiInterface = ApiInterface.create()

        apiInterface.saveFile(
            "321",
            MultipartBody.Part.createFormData("imageCIN", fileArray[0].name, bodyArray[0]),
            MultipartBody.Part.createFormData("imagePermis", fileArray[1].name, bodyArray[1]),
            MultipartBody.Part.createFormData("imageCarte", fileArray[2].name, bodyArray[2]),
            MultipartBody.Part.createFormData("imageAttestation", fileArray[3].name, bodyArray[3]),
        ).enqueue(object : retrofit2.Callback<Profile> {


            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Toast.makeText(this@ImagesActivity ,"Connexion error!", Toast.LENGTH_SHORT).show()

            }


            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {

                    Toast.makeText(this@ImagesActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                }


        })
    }

    override fun onProgressUpdate(pecentage: Int) {

    }
}