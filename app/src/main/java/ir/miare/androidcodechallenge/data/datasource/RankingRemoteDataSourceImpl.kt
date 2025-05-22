package ir.miare.androidcodechallenge.data.datasource

import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.service.RankingApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RankingRemoteDataSourceImpl @Inject constructor(private val api: RankingApi) :
    RankingRemoteDataSource {
    override suspend fun getRankingData(): Flow<List<FakeData>?> = flow {
        val response = api.getData()
        if (response.isSuccessful) {
            emit(response.body())
        } else {

        }
    }
}