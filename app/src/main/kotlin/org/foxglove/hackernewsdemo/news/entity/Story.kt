package org.foxglove.hackernewsdemo.news.entity

data class Story(val title: String, val url: String, val comments: List<Comment>)
