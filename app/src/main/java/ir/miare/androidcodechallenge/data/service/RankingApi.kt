package ir.miare.androidcodechallenge.data.service

import ir.logicbase.mockfit.Mock
import ir.miare.androidcodechallenge.FakeData
import retrofit2.http.GET

interface RankingApi {
    @Mock("data.json")
    @GET("list")
    fun getData(): retrofit2.Response<List<FakeData>>
}