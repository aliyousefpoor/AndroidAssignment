package ir.miare.androidcodechallenge.domain.repository

import ir.miare.androidcodechallenge.data.model.FakeData
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    suspend fun getRankingData(): Flow<List<FakeData>>
}