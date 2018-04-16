package org.foxglove.hackernewsdemo.news.presentation.presenter

import org.foxglove.hackernewsdemo.news.ui.view.IHackerNewsView

interface IHackerNewsPresenter {
    fun attach(hackerNewsView: IHackerNewsView)
    fun detach()
    fun fetchNews()
}
