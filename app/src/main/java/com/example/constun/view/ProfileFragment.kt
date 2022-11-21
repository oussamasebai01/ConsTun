//package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.constun.R
import com.example.constun.databinding.FragmentProfileBinding
import com.example.constun.model.User
import com.example.constun.view.ImagesActivity
import com.example.constun.view.LOGIN
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface





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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    private  fun doConnect() {



            val apiInterface = ApiInterface.create()

            apiInterface.updateProfile(Emailprofile.text.toString(), NumTelprofile.text.toString().toInt(),matriculeprofile.text.toString(), caaprofile.text.toString().toInt(), cinprofile.text.toString().toInt()).enqueue(object :
                Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()

                }



                override fun onResponse(call: Call<User>, response: Response<User>) {



                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(activity, "update Success", Toast.LENGTH_SHORT).show()

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

        Emailprofile = v.findViewById(R.id.Emailprofile)
        NumTelprofile = v.findViewById(R.id.NumTelprofile)
        matriculeprofile = v.findViewById(R.id.matriculeprofile)
        caaprofile = v.findViewById(R.id.caaprofile)
        cinprofile = v.findViewById(R.id.cinprofile)
        updateprofile = v.findViewById(R.id.updateprofile)


        mSharedPref= v.context.getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE
        )
        Emailprofile.text = mSharedPref.getString(LOGIN,"")
        updateprofile.setOnClickListener {
            doConnect()
        }

        v.findViewById<Button>(R.id.file).setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(),ImagesActivity::class.java)
            startActivity(intent)
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
}