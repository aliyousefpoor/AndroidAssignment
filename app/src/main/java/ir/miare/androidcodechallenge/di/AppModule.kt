package ir.miare.androidcodechallenge.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.RankingRepositoryImpl
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSource
import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSourceImpl
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRankingRemoteDataSource(
        rankingRemoteDataSourceImpl: RankingRemoteDataSourceImpl
    ): RankingRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRankingRepository(rankingRepositoryImpl: RankingRepositoryImpl):RankingRepository
}