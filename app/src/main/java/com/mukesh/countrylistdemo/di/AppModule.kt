package com.mukesh.countrylistdemo.di

import android.content.Context
import com.mukesh.countrylistdemo.BuildConfig
import com.mukesh.countrylistdemo.data.repository.CountryRepository
import com.mukesh.countrylistdemo.data.repository.CountryRepositoryImpl
import com.mukesh.countrylistdemo.data.repository.auth.*
import com.mukesh.countrylistdemo.data.repository.datasource.remote.*
import com.mukesh.countrylistdemo.data.repository.auth.AuthApiService
import com.mukesh.countrylistdemo.data.repository.datasource.remote.api.CountryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
Created by Mukesh on 27/02/23
 Dependency injection Application Module.
 **/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return httpLoggingInterceptor
    }


    @Provides
    @Singleton
    fun provideAuthAuthenticator(@ApplicationContext appContext: Context): AuthTokenManager {
        val preferences = appContext.getSharedPreferences("cred_pref", Context.MODE_PRIVATE)
        return AuthTokenManager(preferences)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(authTokenManager: AuthTokenManager): HeaderInterceptor {
        return HeaderInterceptor(authTokenManager)
    }

    @Provides
    @Singleton
    @Named("header_http_client")
    fun provideContentOkHttpClient(headerInterceptor: HeaderInterceptor, loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(headerInterceptor)
        loggingInterceptor?.let { builder.addInterceptor(it) }
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("content_retrofit_client")
    fun provideContentRetrofitClient(@Named("header_http_client") okHttpClient:OkHttpClient): Retrofit{
        return createRetrofitObject(okHttpClient)
    }

    private fun createRetrofitObject(okHttpClient:OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContentApi(@Named("content_retrofit_client") retrofit: Retrofit): CountryApiService {
        return retrofit.create(CountryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(countryApiService: CountryApiService): RemoteDataSource{
        return RemoteDataSourceImpl(countryApiService)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(remoteDataSource: RemoteDataSource): CountryRepository {
        return CountryRepositoryImpl(remoteDataSource)
    }

    //Auth

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    @Named("auth_http_client")
    fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(authInterceptor)
        loggingInterceptor?.let { builder.addInterceptor(it) }
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("auth_retrofit_client")
    fun provideAuthRetrofitClient(@Named("auth_http_client") okHttpClient:OkHttpClient): Retrofit{
        return createRetrofitObject(okHttpClient)
    }


    @Provides
    @Singleton
    fun provideAuthApiService(@Named("auth_retrofit_client") retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(authApiService: AuthApiService): AuthDataSource {
        return AuthDataSourceImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

}