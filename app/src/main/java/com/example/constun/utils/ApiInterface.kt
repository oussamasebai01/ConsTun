package tn.esprit.lolretrofit.utils

import android.provider.ContactsContract.CommonDataKinds.Callable
import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.constun.model.User
import com.example.constun.model.Profile
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiInterface {

    @POST("/user/signup")
    fun inscrir(@Query("email") email:String, @Query("password") password:String): Call<User>

    companion object {

        var BASE_URL ="http://10.217.20.2:9090/"

        fun create(): ApiInterface{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }


    @POST("/user/signin")
    fun seConnecter(@Query("email") email:String, @Query("password") password:String): Call<User>

    @PUT("/user/update")
    fun updateProfile(@Query("email") email:String, @Query("numTel") numTel:Number,
                      @Query("matricule") matricule:String, @Query("code_assurence") code_assurence:Number, @Query("cin") cin:Number):Call<User>

    @POST("/profile/")
    fun saveFile(@Query("imageCIN") imageCIN:String, @Query("imagePermis") imagePermis:String,
                 @Query("imageCarte") imageCarte:String, @Query("imageAttestation") imageAttestation:String): Call<Profile>
}