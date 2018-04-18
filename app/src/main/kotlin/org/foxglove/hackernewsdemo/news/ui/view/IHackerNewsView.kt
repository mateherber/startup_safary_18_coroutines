package org.foxglove.hackernewsdemo.news.ui.view

import android.os.Bundle
import org.foxglove.hackernewsdemo.news.entity.Story
import org.foxglove.hackernewsdemo.news.ui.activity.HackerNewsActivity
import org.jetbrains.anko.AnkoComponent

interface IHackerNewsView : AnkoComponent<HackerNewsActivity> {
    fun restore(savedInstanceState: Bundle)
    fun save(outState: Bundle)
    fun resume()
    fun pause()
    fun showProgress()
    fun showError(message: String?)
    fun hideProgress()
    fun showTopStories(topStories: List<Story>)
}
