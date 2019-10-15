package com.niravjoshi.proof_of_concept.api

import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO
import retrofit2.Call
import retrofit2.http.GET


/**
 * [RetrofitSingleton] :
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 */

interface IAppWebApi {
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getFeedsDetails(): Call<FeedsDTO?>
}