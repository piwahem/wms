package com.example.feature.core.network

import com.example.feature.login.data.AuthenticationResponse
import com.example.feature.userinformation.data.UserInformationResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login")
    fun signIn(@Field("user_id") userId: String,
                   @Field("pass_word") password: String,
                   @Field("mac_address") deviceId: String
    ): Response<AuthenticationResponse>

    @GET("user/information")
    suspend fun getUserInformation(
    ): Response<UserInformationResponse>

    companion object {

        private lateinit var retrofit: Retrofit
        private var client = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            addInterceptor(NetworkErrorInterceptor())
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                val tlsSocketFactory = TLSSocketFactory()
//                sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
//            }
        }.build()
        private var converter = GsonConverterFactory.create(GsonBuilder().setLenient().create())

        private fun baseUrl(): String {
            return "https://xxx.yyy.zzz/"
        }

        fun getClient(): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl())
                .client(client)
                .addConverterFactory(converter)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit
        }
    }
}