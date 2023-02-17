package com.bersyte.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.bersyte.weatherapp.api.ApiService
import com.bersyte.weatherapp.db.AppDatabase
import com.bersyte.weatherapp.db.weatherDao
import com.bersyte.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context):AppDatabase =
        Room.databaseBuilder(context,AppDatabase::class.java,"AppDatabase")
            .build()

    @Provides
    fun providesPostDao(appDatabase: AppDatabase):weatherDao =
        appDatabase.getDao()

   /* @Provides
    fun providesPostRepository(dao: weatherDao,apiServiceImp: ApiServiceImp):PostRepository =
        PostRepository(postDao,apiServiceImp)
*/
}