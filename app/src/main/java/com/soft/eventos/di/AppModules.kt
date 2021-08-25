package com.soft.eventos.di

import com.soft.eventos.BuildConfig
import com.soft.eventos.data.api.ApiService
import com.soft.eventos.data.repository.EventsRepository
import com.soft.eventos.ui.detailsEvents.DetailsEventsViewModel
import com.soft.eventos.ui.eventList.ListEventsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModules {

    val Project = module {
        single { providerApiEvents() }
        single { EventsRepository(get()) }
        viewModel { ListEventsViewModel(get()) }
        viewModel { DetailsEventsViewModel(get()) }
    }

    private fun providerApiEvents(): ApiService {

        val client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)


        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(logger)

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(ApiService::class.java)
    }
}