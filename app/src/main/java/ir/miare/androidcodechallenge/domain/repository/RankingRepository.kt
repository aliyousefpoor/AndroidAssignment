package ir.miare.androidcodechallenge.domain.repository

import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Result
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    suspend fun getRankingData(): Flow<Result<List<FakeData>?>>
}