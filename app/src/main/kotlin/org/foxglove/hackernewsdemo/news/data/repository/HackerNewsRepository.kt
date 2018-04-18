package org.foxglove.hackernewsdemo.news.data.repository

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto
import org.foxglove.hackernewsdemo.news.data.service.IHackerNewsService
import org.foxglove.hackernewsdemo.news.data.transformer.IHackerNewsResultTransformer
import org.foxglove.hackernewsdemo.news.domain.repository.IHackerNewsRepository
import org.foxglove.hackernewsdemo.news.entity.Story
import kotlin.coroutines.experimental.coroutineContext

class HackerNewsRepository(
    private val hackerNewsService: IHackerNewsService,
    private val hackerNewsResultTransformer: IHackerNewsResultTransformer
) : IHackerNewsRepository {
    override suspend fun getTopStories(): List<Story> {
        val comments = mutableMapOf<Int, HackerNewsItemDto>()
        return hackerNewsService.getTopStories()
            ?.take(20)
            ?.filterNotNull()
            ?.map { id ->
                async(coroutineContext + CommonPool) {
                    hackerNewsService.getItem(id)
                        ?.takeIf { it.type == "story" }
                        ?.also { itemDto ->
                            fillWithComments(comments, itemDto.kids, 0)
                        }?.let {
                            hackerNewsResultTransformer.transform(it, comments)
                        }
                }
            }
            ?.mapNotNull { promise ->
                promise.await()
            } ?: emptyList()
    }

    private suspend fun fillWithComments(
        items: MutableMap<Int, HackerNewsItemDto>,
        kids: List<Int?>?,
        depth: Int
    ) {
        if (depth > 0) {
            return
        }
        kids
            ?.filterNotNull()
            ?.take(2)
            ?.map { id ->
                launch {
                    hackerNewsService.getItem(id)
                        ?.also { itemDto ->
                            items[id] = itemDto
                            fillWithComments(items, itemDto.kids, depth + 1)
                        }
                }
            }
            ?.forEach {
                it.join()
            }
    }
}