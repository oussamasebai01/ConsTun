//package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.constun.R
import com.example.constun.databinding.FragmentProfileBinding
import com.example.constun.model.User
import com.example.constun.view.*
import com.example.constun.view.LOGIN
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

const val PREF_NAME = "LOGIN_PREF_LOL"
const val LOGIN = "LOGIN"
const val PASSWORD = "PASSWORD"
const val IS_REMEMBRED = "IS_REMEMBRED"
const val NUMERO = "22222222"
const val MATRICUL ="1TUN1"
const val CODE = "1"
const val CIN = "01"



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var Emailprofile : TextView
    lateinit var NumTelprofile : TextView
    lateinit var matriculeprofile : TextView
    lateinit var caaprofile : TextView
    lateinit var cinprofile : TextView
    lateinit var updateprofile :Button
    lateinit var mSharedPref: SharedPreferences
    lateinit var btnQR : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }
    private fun getInfo(){
        val apiInterface = ApiInterface.create()
        val id = mSharedPref.getString(ID,"")
//        val mat = mSharedPref.getString(MATRICUL,"")
//        val cod = mSharedPref.getString(CODE,"")
//        val cin = mSharedPref.getString(CIN,"")
        println(id)
        apiInterface.getInfo(id.toString()).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if (user != null) {
                    Toast.makeText(activity, "update Success", Toast.LENGTH_SHORT).show()
                    mSharedPref.edit().apply{
                            //putBoolean(IS_REMEMBRED, true)
                            putString(NUMERO, user.numTel)
                            putString(MATRICUL, user.matricule)
                            putString(CODE, user.code_assurence)
                            putString(CIN, user.cin)
                        }.apply()

                } else {
                    Toast.makeText(activity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private  fun doConnect() {



            val apiInterface = ApiInterface.create()
            val id = mSharedPref.getString(ID,"")
            apiInterface.updateProfile(id.toString(), NumTelprofile.text.toString().toInt(),matriculeprofile.text.toString(), caaprofile.text.toString().toInt(), cinprofile.text.toString().toInt()).enqueue(object :
                Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()

                }



                override fun onResponse(call: Call<User>, response: Response<User>) {



                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(activity, "update Success", Toast.LENGTH_SHORT).show()
//                        mSharedPref.edit().apply{
//                            //putBoolean(IS_REMEMBRED, true)
//                            putString(NUMERO, NumTelprofile.text.toString())
//                            putString(MATRICUL, matriculeprofile.text.toString())
//                            putString(CODE, caaprofile.text.toString())
//                            putString(CIN, cinprofile.text.toString())
//                        }.apply()

                    } else {
                        Toast.makeText(activity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v = inflater.inflate(R.layout.fragment_profile, container, false)

        mSharedPref= v.context.getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE
        )
        Emailprofile = v.findViewById(R.id.Emailprofile)
        NumTelprofile = v.findViewById(R.id.NumTelprofile)
        matriculeprofile = v.findViewById(R.id.matriculeprofile)
        caaprofile = v.findViewById(R.id.caaprofile)
        cinprofile = v.findViewById(R.id.cinprofile)
        updateprofile = v.findViewById(R.id.updateprofile)
        btnQR = v.findViewById(R.id.btnQR)
        NumTelprofile.text = mSharedPref.getString(NUMERO,"")
        matriculeprofile.text = mSharedPref.getString(MATRICUL,"")
        caaprofile.text = mSharedPref.getString(CODE,"")
        cinprofile.text = mSharedPref.getString(CIN,"")

        Emailprofile.setText(mSharedPref.getString(LOGIN,""))
        getInfo()
        updateprofile.setOnClickListener {

            doConnect()

            mSharedPref.edit().apply{
                //putBoolean(IS_REMEMBRED, true)
                putString(NUMERO, NumTelprofile.text.toString())
                putString(MATRICUL, matriculeprofile.text.toString())
                putString(CODE, caaprofile.text.toString())
                putString(CIN, cinprofile.text.toString())
            }.apply()

        }

        v.findViewById<Button>(R.id.file).setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(),ImagesActivity::class.java)
            startActivity(intent)
        }
        btnQR.setOnClickListener {
            val text1 = NumTelprofile.text
            val text2 = matriculeprofile.text
            val text3 = caaprofile.text
            val text4 = cinprofile.text
            if (text1.isNotBlank()&&text2.isNotBlank()&&text3.isNotBlank()&&text4.isNotBlank()){
                val bitmap = generateQRCode(text1.toString(),text2.toString(),text3.toString(),text4.toString())
                imageQR.setImageBitmap(bitmap)
            }
        }
        return v
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun generateQRCode(text1: String,text2: String,text3: String,text4 : String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        try {
            val bitMatrix = codeWriter.encode(text2 + ":"+ text3 + ":"+text4 + ":" +text1,
                BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width){
                for (y in 0 until height){
                    bitmap.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }

        }catch (e: WriterException){
            Log.d("","gererateQRCode : ${e.message}")
        }


        return bitmap
    }
}