package ir.miare.androidcodechallenge.data.datasource

import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Result
import kotlinx.coroutines.flow.Flow

interface RankingRemoteDataSource {
    suspend fun getRankingData(): Flow<Result<List<FakeData>?>>
}