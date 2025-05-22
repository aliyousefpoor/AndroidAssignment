package ir.miare.androidcodechallenge.data.datasource

import ir.miare.androidcodechallenge.data.model.FakeData
import kotlinx.coroutines.flow.Flow

interface RankingRemoteDataSource {
    suspend fun getRankingData(): Flow<List<FakeData>?>
}