package ir.miare.androidcodechallenge.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import ir.miare.androidcodechallenge.domain.usecase.GetRankingDataUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRankingRemoteDataSource()

    @Provides
    @Singleton
    fun provideRankingRemoteDataSourceImpl() {
    }

    @Binds
    @Singleton
    abstract fun bindRankingRepository()

    @Provides
    @Singleton
    fun provideRankingRepositoryImpl(retrofit: Retrofit) {
    }

    @Provides
    @Singleton
    fun provideGetRankingDataUseCase(repository: RankingRepository): GetRankingDataUseCase {
        return GetRankingDataUseCase(repository)
    }
}