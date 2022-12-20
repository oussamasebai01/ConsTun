package com.example.constun.view

import CODE
import MATRICUL
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.constun.R
import com.example.constun.model.canstat
import com.example.constun.utils.UploadRequestBody
import com.example.constun.utils.getFileName
import com.example.constun.utils.snackbar
import kotlinx.android.synthetic.main.activity_create1.*
import kotlinx.android.synthetic.main.fragment_third.view.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

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
            fileArray.add(file)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            val body = UploadRequestBody(file, "image", this)
            bodyArray.add(body)
        }
        val apiInterface = ApiInterface.create()

        apiInterface.CreateCanstat(
            mSharedPref.getString(NOMA,""),
            mSharedPref.getString(MATRICUL,""),
            mSharedPref.getString(CODE,""),
            mSharedPref.getString(CIN,""),
            mSharedPref.getString(NUMERO,""),
            mSharedPref.getString(NOMB,""),
            mSharedPref.getString(MATRICULB,""),
            mSharedPref.getString(CODEB,""),
            mSharedPref.getString(CINB,""),
            mSharedPref.getString(NUMEROB,""),
            localisation.text.toString(),
            MultipartBody.Part.createFormData("image1", fileArray[0].name, bodyArray[0]),
            MultipartBody.Part.createFormData("image2", fileArray[1].name, bodyArray[1]),
            "aa"

        ).enqueue(object : Callback<canstat> {

            override fun onResponse(call: Call<canstat>, response: Response<canstat>) {
             //   layout_root.snackbar(response.body()?.localisation.toString())
                println("jawii behi")
            }

            override fun onFailure(call: Call<canstat>, t: Throwable) {
                layout_root.snackbar(t.message!!)
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
        image1 = view.findViewById(R.id.img1)
        image2 = view.findViewById(R.id.img2)
        creatbtn = view.findViewById(R.id.save)
        sendbtn = view.findViewById(R.id.sms)
        selectbtn = view.findViewById(R.id.select)
        localisation = view.findViewById(R.id.editTextLocation)
        gallery = view.findViewById(R.id.gallery)
        gallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            gallery.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(gallery,REQUEST_CODE_IMAGE)
        }
        creatbtn.setOnClickListener {
            uploadImage()
        }
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

    override fun onProgressUpdate(pecentage: Int) {
    }
}