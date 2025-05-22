package ir.miare.androidcodechallenge.data.service

import ir.logicbase.mockfit.Mock
import ir.miare.androidcodechallenge.data.model.FakeData
import retrofit2.Call
import retrofit2.http.GET

interface RankingApi {
    @Mock("data.json")
    @GET("list")
    fun getData(): Call<List<FakeData>>
}