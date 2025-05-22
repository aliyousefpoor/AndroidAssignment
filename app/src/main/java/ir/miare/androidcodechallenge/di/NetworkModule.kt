package ir.miare.androidcodechallenge.di

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.logicbase.mockfit.MockFitConfig
import ir.logicbase.mockfit.MockFitInterceptor
import ir.miare.androidcodechallenge.data.RankingRepositoryImpl
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSource
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSourceImpl
import ir.miare.androidcodechallenge.data.service.RankingApi
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import ir.miare.androidcodechallenge.domain.usecase.GetRankingDataUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                MockFitInterceptor(
                    bodyFactory = { input -> context.assets.open(input) },
                    logger = { tag, message -> Log.d(tag, message) },
                    baseUrl = "https://test_baseurl.com/v2/",
                    requestPathToJsonMap = MockFitConfig.REQUEST_TO_JSON,
                    mockFilesPath = "",
                    mockFitEnable = true,
                    apiEnableMock = true,
                    apiIncludeIntoMock = arrayOf(),
                    apiExcludeFromMock = arrayOf(),
                    apiResponseLatency = 1000L
                )
            )
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://test_baseurl.com/v2/")
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RankingApi {
        return retrofit.create(RankingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRankingRemoteDataSourceImpl(api: RankingApi): RankingRemoteDataSourceImpl {
        return RankingRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideRankingRepositoryImpl(dataSource: RankingRemoteDataSource): RankingRepositoryImpl {
        return RankingRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetRankingDataUseCase(repository: RankingRepository): GetRankingDataUseCase {
        return GetRankingDataUseCase(repository)
    }
}