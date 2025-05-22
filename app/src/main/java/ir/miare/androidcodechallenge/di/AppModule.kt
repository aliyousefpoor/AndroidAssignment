package ir.miare.androidcodechallenge.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.RankingRepositoryImpl
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSource
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSourceImpl
import ir.miare.androidcodechallenge.data.service.RankingApi
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import ir.miare.androidcodechallenge.domain.usecase.GetRankingDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRankingRemoteDataSource(
        rankingRemoteDataSourceImpl: RankingRemoteDataSourceImpl
    ): RankingRemoteDataSource

    @Provides
    @Singleton
    fun provideRankingRemoteDataSourceImpl(api: RankingApi): RankingRemoteDataSourceImpl {
        return RankingRemoteDataSourceImpl(api)
    }

    @Binds
    @Singleton
    abstract fun bindRankingRepository(rankingRepositoryImpl: RankingRepositoryImpl): RankingRepository

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