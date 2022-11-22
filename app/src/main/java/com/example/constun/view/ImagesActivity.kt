package com.example.constun.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.constun.R
import com.example.constun.model.Profile
import com.example.constun.model.User
import retrofit2.Call
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface
import javax.security.auth.callback.Callback

class ImagesActivity : AppCompatActivity() {
    lateinit var image_cin : ImageView
    lateinit var image_permis : ImageView
    lateinit var image_carte : ImageView
    lateinit var image_attestation : ImageView
    lateinit var btn : Button
    lateinit var txt_cin : TextView
    var pickedPhoto : Uri? = null
    var pickedBitmap : Bitmap? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        image_permis=findViewById(R.id.image_permis)
        image_cin=findViewById(R.id.image_cin)
        image_carte=findViewById(R.id.image_carte)
        image_attestation=findViewById(R.id.image_attestation)
        btn = findViewById(R.id.signUp)
        txt_cin = findViewById(R.id.txt_cin)
        btn.setOnClickListener {
            save()
        }
    }

    fun TakePhoto0(view: View) {
       if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_DENIED){
           ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
       }else{

           val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
           startActivityForResult(galeriIntext,2)
       }
    }
    fun TakePhoto1(view: View) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{

            val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
        }
    }
    fun TakePhoto2(view: View) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{

            val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
        }
    }
    fun TakePhoto3(view: View) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{

            val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==2 &&resultCode == Activity.RESULT_OK && data != null){
            pickedPhoto = data.data
            if(pickedPhoto != null){
                if(Build.VERSION.SDK_INT>=28){
                    val source = ImageDecoder.createSource(this.contentResolver,pickedPhoto!!)
                    pickedBitmap = ImageDecoder.decodeBitmap(source)
                    image_cin.setImageBitmap(pickedBitmap)
                    txt_cin.text = image_cin.toString()
                    image_permis.setImageBitmap(pickedBitmap)
                    image_carte.setImageBitmap(pickedBitmap)
                    image_attestation.setImageBitmap(pickedBitmap)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun save(){

        val apiInterface = ApiInterface.create()

        apiInterface.saveFile(
            image_cin.toString(),
            image_permis.toString(),
            image_carte.toString(),
            image_attestation.toString(),
        ).enqueue(object : retrofit2.Callback<Profile> {


            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Toast.makeText(this@ImagesActivity ,"Connexion error!", Toast.LENGTH_SHORT).show()

            }


            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {

                    Toast.makeText(this@ImagesActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                }


        })
    }
}