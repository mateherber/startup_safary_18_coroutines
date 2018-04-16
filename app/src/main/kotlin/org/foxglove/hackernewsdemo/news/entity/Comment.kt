package org.foxglove.hackernewsdemo.news.entity

data class Comment(val text: String, val comments: List<Comment>)