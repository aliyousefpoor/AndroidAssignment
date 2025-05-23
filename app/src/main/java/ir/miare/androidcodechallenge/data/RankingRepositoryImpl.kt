package ir.miare.androidcodechallenge.data

import ir.miare.androidcodechallenge.data.datasource.RankingRemoteDataSource
import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Result
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(private val dataSource: RankingRemoteDataSource) :
    RankingRepository {
    override suspend fun getRankingData(): Flow<Result<List<FakeData>?>> {
        return dataSource.getRankingData()
    }
}