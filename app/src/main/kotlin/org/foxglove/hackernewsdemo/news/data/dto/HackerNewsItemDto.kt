package org.foxglove.hackernewsdemo.news.data.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class HackerNewsItemDto(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("by") val by: String?,
    @JsonProperty("type") val type: String?,
    @JsonProperty("time") val time: Int?,
    @JsonProperty("parent") val parent: Int?,
    @JsonProperty("kids") val kids: List<Int?>?,
    @JsonProperty("descendants") val descendants: Int?,
    @JsonProperty("score") val score: Int?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("url") val url: String?,
    @JsonProperty("text") val text: String?
)