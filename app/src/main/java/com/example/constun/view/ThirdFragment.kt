package com.example.constun.view

import CODE
import MATRICUL
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.constun.R
import com.example.constun.model.canstat
import com.example.constun.utils.UploadRequestBody
import com.example.constun.utils.getFileName
import com.example.constun.utils.snackbar
import kotlinx.android.synthetic.main.fragment_third.view.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_third.*

@Suppress("UNREACHABLE_CODE")
class ThirdFragment : Fragment(), UploadRequestBody.UploadCallback {

    private  var image : Uri? =null
    private var image_2 : Uri? =null
    lateinit var image1 : ImageView
    lateinit var image2: ImageView
    lateinit var creatbtn : Button
    lateinit var sendbtn : Button
    lateinit var selectbtn : Button
    lateinit var localisation : EditText
    private val mArrayUri = arrayListOf<Uri>()
    lateinit var mSharedPref: SharedPreferences
    lateinit var gallery : Button
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mSharedPrefs: SharedPreferences
    lateinit var description : EditText

    companion object{
        private const val REQUEST_CODE_IMAGE = 100
    }
    val fileArray = arrayListOf<File>()
    val bodyArray = arrayListOf<UploadRequestBody>()

    @SuppressLint("Recycle")
    private fun uploadImage() {
        for (u: Uri in mArrayUri) {
            var parcelFileDescriptor =
                requireActivity().contentResolver.openFileDescriptor(u, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(u))
            println("file"+file)
            fileArray.add(file)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            val body = UploadRequestBody(file, "image", this)
            println("body"+body)
            bodyArray.add(body)
        }
        val apiInterface = ApiInterface.create()

        apiInterface.CreateCanstat(
            mSharedPref.getString(NOMA,""),
            mSharedPref.getString(MATRICUL,""),
            mSharedPref.getString(CODE,""),
            mSharedPref.getString(CIN,""),
            mSharedPref.getString(NUMERO,""),
            mSharedPrefs.getString(NOMB,""),
            mSharedPrefs.getString(MATRICULEB,""),
            mSharedPrefs.getString(CODEB,""),
            mSharedPrefs.getString(CINB,""),
            mSharedPrefs.getString(NUMEROB,""),
            localisation.text.toString(),
            MultipartBody.Part.createFormData("image1", fileArray[0].name, bodyArray[0]),
            MultipartBody.Part.createFormData("image2", fileArray[1].name, bodyArray[1]),
            description.text.toString()

        ).enqueue(object : Callback<canstat> {

            override fun onResponse(call: Call<canstat>, response: Response<canstat>) {
             //   layout_root.snackbar(response.body()?.localisation.toString())
                println("jawii behi")
            }

            override fun onFailure(call: Call<canstat>, t: Throwable) {
               // layout_root.snackbar(t.message!!)
            }

        })
    }
    @SuppressLint("MissingInflatedId", "IntentReset")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view =inflater.inflate(R.layout.fragment_third, container, false)
        mSharedPref= view.context.getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE
        )
        mSharedPrefs = view.context.getSharedPreferences(NAME, AppCompatActivity.MODE_PRIVATE)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        image1 = view.findViewById(R.id.img1)
        image2 = view.findViewById(R.id.img2)
        creatbtn = view.findViewById(R.id.save)
        sendbtn = view.findViewById(R.id.sms)
        selectbtn = view.findViewById(R.id.select)
        localisation = view.findViewById(R.id.editTextLocation)
        gallery = view.findViewById(R.id.gallery)
        description = view.findViewById(R.id.description)
        gallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            gallery.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(gallery,REQUEST_CODE_IMAGE)
        }
        selectbtn.setOnClickListener { checkLocationPermission() }
        sendbtn.setOnClickListener {

            if(valide() && bodyArray.size == 2) {
                val apiInterface = ApiInterface.create()

                apiInterface.sendSMS(localisation.text.toString()).enqueue(object :
                    Callback<canstat> {

                    override fun onFailure(call: Call<canstat>, t: Throwable) {

                        println("failure")

                    }

                    override fun onResponse(call: Call<canstat>, response: Response<canstat>) {

                        val constat = response.body()
                        if (constat != null) {
                            println("SMS sent")

                        } else {
                            println("SMS not sent")
                        }
                    }

                })
            }
            else
                Toast.makeText(requireContext(), "verfiee !!", Toast.LENGTH_SHORT).show()
        }
        creatbtn.setOnClickListener {
            uploadImage()
        }
        println("555555"+ mSharedPref.getString(NOMA,""))
        println( "666666"+mSharedPrefs.getString(NOMB,""))
        println("7777777"+mSharedPref.getString(MATRICUL,""))

        return view
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
    @SuppressLint("SetTextI18n")
    private fun checkLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this.requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101)
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                val text = "${it.latitude},${it.longitude}"
                localisation.setText(text)
                Toast.makeText(requireContext(),
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun valide():Boolean{
        if(localisation.text!!.isEmpty()||description.text!!.isEmpty())
            return false
        else
            return true
    }

    override fun onProgressUpdate(pecentage: Int) {
    }
}