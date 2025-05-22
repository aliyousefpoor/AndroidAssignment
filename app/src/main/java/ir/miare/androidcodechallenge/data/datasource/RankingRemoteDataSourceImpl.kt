package ir.miare.androidcodechallenge.data.datasource

import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.service.RankingApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RankingRemoteDataSourceImpl @Inject constructor(private val api: RankingApi) :
    RankingRemoteDataSource {
    override suspend fun getRankingData(): Flow<List<FakeData>?> {
        return channelFlow {
            try {
                api.getData().enqueue(object : Callback<List<FakeData>> {
                    override fun onResponse(
                        call: Call<List<FakeData>>,
                        response: Response<List<FakeData>>
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (response.isSuccessful) {
                                trySend(response.body())
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<FakeData>>, t: Throwable) {

                    }

                })
            }catch (e:Exception){

            }
            awaitClose()
        }
    }
}