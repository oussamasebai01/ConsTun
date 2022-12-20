package com.example.constun.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.constun.R
import com.example.constun.databinding.ActivityCreat3Binding
import com.example.constun.model.Customer
import com.example.constun.utils.RealPathUtil
import com.example.constun.utils.UploadRequestBody
import com.example.constun.utils.getFileName
import com.example.constun.utils.snackbar
import kotlinx.android.synthetic.main.activity_creat3.*
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class CreatActivity3 : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private  var image : Uri? =null
    private var image_2 : Uri? =null
    lateinit var image1 : ImageView
    lateinit var image2 : ImageView
    lateinit var btncam : Button
    lateinit var name : EditText
    lateinit var save : Button
    lateinit var binding : ActivityCreat3Binding
    var path: String? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreat3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = intent
        var  nomA=intent.getStringExtra("nomA")
        var matriculA=intent.getStringExtra("matriculA")
        var codeA = intent.getStringExtra("matriculA")
        var cinA = intent.getStringExtra("cin")
        var numA = intent.getStringExtra("num")

        var  nomB=intent.getStringExtra("nomB")
        var matriculB=intent.getStringExtra("matriculB")
        var codeB = intent.getStringExtra("codeB")
        var cinB = intent.getStringExtra("cinB")
        var numB = intent.getStringExtra("numB")

        binding.location.setText(numA.toString())
        println(numA)
        println("heelo"+codeB)
        image1 = findViewById(R.id.img1)
        image2 = findViewById(R.id.img2)
        btncam = findViewById(R.id.btncam)
        //name = findViewById(R.id.name)
        save = findViewById(R.id.save)
        img1.setOnClickListener{
            openImageChooser()
        }
        img2.setOnClickListener {
            openImageChooser()
        }
//        btncam.setOnClickListener {
//            val intent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
//
//              try {
//                startActivityForResult(intent,code)
//               }catch (e:ActivityNotFoundException)
//               {
//            Toast.makeText(this,"Error: "+e.localizedMessage,Toast.LENGTH_SHORT).show()
//               }
//              }
        save.setOnClickListener {
           uploadImage()
        }

            }
    private fun openImageChooser(){
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE)
        }
    }
    companion object{
        private const val REQUEST_CODE_IMAGE = 100
    }
    @SuppressLint("Recycle")
    private fun uploadImage()
    {
        val Uris = mutableListOf<Uri>(image!!,image_2!!)
        var parcelFileDescriptor = contentResolver.openFileDescriptor(image!!,"r",null) ?:return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir,contentResolver.getFileName(image!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file,"image",this)
        val apiInterface = ApiInterface.create()

        apiInterface.uploadImage(
            MultipartBody.Part.createFormData("image",file.name,body),
            name.text.toString()
            //name.text.toString()
        ).enqueue(object: Callback<Customer>{
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {

               // layout_root.snackbar(response.body()?.customer_name.toString())
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                //layout_root.snackbar(t.message!!)
            }

        } )



    }

    //@SuppressLint("SuspiciousIndentation")
    @Deprecated("Deprecated in Java")
    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK)
        {
           when(requestCode){
               REQUEST_CODE_IMAGE ->{
                   image= data?.data
                   image_2 = data?.data
                   image1.setImageURI(image)
               }
           }
        }

    }

    override fun onProgressUpdate(pecentage: Int) {

    }

    fun addCustomer(name: String?, reference: String?) {
        val retrofit = Retrofit.Builder().baseUrl("http://172.16.4.130:9090/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val cus_name = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val cus_reference = RequestBody.create(MediaType.parse("multipart/form-data"), reference)
        val apiService: ApiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<Customer?>? = apiService.addCustomer(body, cus_name, cus_reference)
        call?.enqueue(object : Callback<Customer?> {
            override fun onResponse(
                call: Call<Customer?>,
                response: Response<Customer?>,
            ) {
                if (response.isSuccessful()) {
                    assert(response.body() != null)
                    if (response.body().toString().equals("200")) {
                        Toast.makeText(applicationContext, "Customer Added", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "not Added", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Customer?>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


}

private fun Uri?.setImageBitmap(imageBitmap: Bitmap) {

}
