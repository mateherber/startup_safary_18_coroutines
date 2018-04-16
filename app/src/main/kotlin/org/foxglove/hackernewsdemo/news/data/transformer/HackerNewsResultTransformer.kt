package org.foxglove.hackernewsdemo.news.data.transformer

import org.foxglove.hackernewsdemo.news.data.dto.HackerNewsItemDto
import org.foxglove.hackernewsdemo.news.entity.Comment
import org.foxglove.hackernewsdemo.news.entity.Story

class HackerNewsResultTransformer : IHackerNewsResultTransformer {
    override fun transform(
        story: HackerNewsItemDto,
        comments: Map<Int, HackerNewsItemDto>
    ): Story {
        if (story.type != "story" || story.title == null || story.url == null) {
            throw IllegalStateException("Item must be of valid story type.")
        }
        return Story(story.title, story.url, transformComments(story.kids, comments))
    }

    private fun transformComments(
        kids: List<Int?>?,
        comments: Map<Int, HackerNewsItemDto>
    ): List<Comment> {
        if (kids != null) {
            return kids.filterNotNull().mapNotNull {
                comments[it]?.let { itemDto ->
                    itemDto.text?.let {
                        Comment(it, transformComments(itemDto.kids, comments))
                    }
                }
            }
        }
        return emptyList()
    }
}