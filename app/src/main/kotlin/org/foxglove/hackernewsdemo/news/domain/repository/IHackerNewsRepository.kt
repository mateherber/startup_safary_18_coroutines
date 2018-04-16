package org.foxglove.hackernewsdemo.news.domain.repository

import org.foxglove.hackernewsdemo.news.entity.Story

interface IHackerNewsRepository {
    suspend fun getTopStories(): List<Story>
}