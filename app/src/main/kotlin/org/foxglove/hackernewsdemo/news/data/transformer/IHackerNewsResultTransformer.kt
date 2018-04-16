package org.foxglove.hackernewsdemo.news.data.transformer

import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto
import org.foxglove.hackernewsdemo.news.entity.Story

interface IHackerNewsResultTransformer {
    fun transform(
        story: HackerNewsItemDto,
        comments: Map<Int, HackerNewsItemDto>
    ): Story
}