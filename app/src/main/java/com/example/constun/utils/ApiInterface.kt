package tn.esprit.lolretrofit.utils

import com.example.constun.model.Customer
import com.example.constun.model.Profile
import com.example.constun.model.User
import com.example.constun.model.canstat
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiInterface {

    @POST("/user/signup")
    fun inscrir(@Query("email") email:String, @Query("password") password:String): Call<User>

    companion object {

        var BASE_URL ="http://10.0.2.2:9090/"
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
    @POST("/profile/get")
    fun getFile(@Query("_id") _id: String): Call<Profile>

    @PUT("/user/update")
    fun updateProfile(
        @Query("email") email: String,
        @Query("numTel") numTel: Number,
        @Query("matricule") matricule: String,
        @Query("code_assurence") code_assurence: Number,
        @Query("cin") cin: Number,
    ):Call<User>

    @Multipart
    @POST("/profile/add")
    fun saveFile(
          //@Body hashMap: HashMap<String, String>
        @Part("_id") _id: String,
//        @Part("imageCIN\"; filename=\"pp.png\" ") imageCIN: RequestBody?,
//        @Part("imagePermis\"; filename=\"ee.png\" ") imagePermis: RequestBody?,
//        @Part("imageCarte\"; filename=\"ff.png\" ") imageCarte: RequestBody?,
//        @Part("imageAttestation\"; filename=\"gg.png\" ") imageAttestation: RequestBody?,
        @Part imageCIN:MultipartBody.Part,
        @Part imagePermis:MultipartBody.Part,
        @Part imageCarte:MultipartBody.Part,
        @Part imageAttestation:MultipartBody.Part,
    ): Call<Profile>

    @GET("/canstat/SMS")
    fun sendSMS(
    ): Call<canstat>

    @Multipart
    @POST("files/AddCustomer")
    fun addCustomer(
        @Part image: MultipartBody.Part?,
        @Part("customer_name") customername: RequestBody?,
        @Part("reference") refernce: RequestBody?,
    ): Call<Customer?>?

    @Multipart
    @POST("files/AddCustomer")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("customer_name") customer_name: String,
    ):Call<Customer>

    @Multipart
    @POST("canstat/add")
    fun CreateCanstat(
        @Part("nomA") nomA: String?,
        @Part("matriculA") matriculA: String?,
        @Part("code_assurenceA") code_assurenceA: String?,
        @Part("cinA") cinA: String?,
        @Part("numTelA") numTelA: String?,
        @Part("nomB") nomB: String?,
        @Part("matriculB") matriculB: String?,
        @Part("code_assurenceB") code_assurenceB: String?,
        @Part("cinB") cinB: String?,
        @Part("numTelB") numTelB: String?,
        @Part("localisation") localisation: String,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part("description") description: String,
    ):Call<canstat>

    @PUT("user/forget")
    fun ForgetPassword(
        @Query("email") email: String,
    ):Call<User>

    @Multipart
    @POST("/api/Accounts/editaccount")
    fun editUser(
        @Part("file\"; filename=\"pp.png\" ") file: RequestBody?,
        @Part("FirstName") fname: RequestBody?,
        @Part("Id") id: RequestBody?,
    ): Call<User?>?
}