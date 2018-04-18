package org.foxglove.hackernewsdemo.news.data.service

import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.rx2.await
import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto

class HackerNewsService(private val hackerNewsRetrofit: HackerNewsRetrofit) :
    IHackerNewsService {
    override suspend fun getItem(itemId: Int): HackerNewsItemDto? {
        return hackerNewsRetrofit.getItem(itemId)
            .subscribeOn(Schedulers.io())
            .await()
    }

    override suspend fun getTopStories(): List<Int?>? {
        return hackerNewsRetrofit.getTopStories()
            .subscribeOn(Schedulers.io())
            .await()
    }
}