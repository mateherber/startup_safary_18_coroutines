package org.foxglove.hackernewsdemo.news.data.service

import io.reactivex.Single
import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsRetrofit {
    @GET("v0/item/{itemId}.json?print=pretty")
    fun getItem(@Path("itemId") itemId: Int): Single<HackerNewsItemDto?>

    @GET("v0/topstories.json?print=pretty")
    fun getTopStories(): Single<List<Int?>?>
}

