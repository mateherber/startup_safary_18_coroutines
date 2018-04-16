package org.foxglove.hackernewsdemo.news.data.repository

import kotlinx.coroutines.experimental.rx2.await
import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto
import org.foxglove.hackernewsdemo.news.data.service.HackerNewsService
import org.foxglove.hackernewsdemo.news.domain.repository.IHackerNewsRepository
import org.foxglove.hackernewsdemo.news.entity.Story
import org.foxglove.hackernewsdemo.news.data.transformer.IHackerNewsResultTransformer

class HackerNewsRepository(
    private val hackerNewsService: HackerNewsService,
    private val hackerNewsResultTransformer: IHackerNewsResultTransformer
) : IHackerNewsRepository {
    override suspend fun getTopStories(): List<Story> {
        val comments = mutableMapOf<Int, HackerNewsItemDto>()
        return hackerNewsService.getTopStories().await()
            .take(20)
            .mapNotNull { id ->
                hackerNewsService.getItem(id).await()
                    .takeIf { it.type == "story" }
                    ?.also { itemDto ->
                        fillWithComments(comments, itemDto.kids, 0)
                    }
            }
            .map { itemDto ->
                hackerNewsResultTransformer.transform(itemDto, comments)
            }
    }

    private suspend fun fillWithComments(
        items: MutableMap<Int, HackerNewsItemDto>,
        kids: List<Int?>?,
        depth: Int
    ) {
        if (depth > 0) {
            return
        }
        kids?.filterNotNull()?.take(2)?.forEach { id ->
            hackerNewsService.getItem(id).await()
                .also { itemDto ->
                    items[id] = itemDto
                    fillWithComments(items, itemDto.kids, depth + 1)
                }
        }
    }
}