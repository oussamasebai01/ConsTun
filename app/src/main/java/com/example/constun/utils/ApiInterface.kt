package tn.esprit.lolretrofit.utils

import com.example.constun.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("/user/signup")
    fun inscrir(@Query("username") username:String, @Query("password") password:String, @Query("numTel") numTel:Number): Call<User>

    companion object {

        var BASE_URL ="http://192.168.254.227:9090/"

        fun create(): ApiInterface{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }


    @POST("/user/signin")
    fun seConnecter(@Query("username") username:String, @Query("password") password:String): Call<User>

    /*companion object {

        var BASE_URL ="http://172.16.2.4:9090/"

        fun create(): ApiInterface{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }*/

}