package org.foxglove.hackernewsdemo.news.data.service

import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto

interface IHackerNewsService {
    suspend fun getItem(itemId: Int): HackerNewsItemDto?
    suspend fun getTopStories(): List<Int?>?
}