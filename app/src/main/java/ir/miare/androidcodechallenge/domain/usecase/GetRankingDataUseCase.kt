package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Result
import ir.miare.androidcodechallenge.domain.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRankingDataUseCase @Inject constructor(private val repository: RankingRepository) {
    suspend fun invoke(): Flow<Result<List<FakeData>?>> {
        return repository.getRankingData()
    }
}