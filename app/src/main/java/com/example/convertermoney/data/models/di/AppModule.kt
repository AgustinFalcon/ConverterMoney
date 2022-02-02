package com.example.convertermoney.data.models.di

import com.example.convertermoney.data.models.CurrencyApi
import com.example.convertermoney.main.DefaultMainRepository
import com.example.convertermoney.main.MainRepository
import com.example.convertermoney.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val BASE_URL = "http://api.exchangeratesapi.io/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Provide a retrofit instance
    @Singleton
    @Provides
    fun provideCurrencyApi() : CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    //Provide a getUrl instance
    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi) : MainRepository = DefaultMainRepository(api = api)


    //Provide a Coroutines for the ViewModels
    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}