package org.foxglove.hackernewsdemo.news.presentation.presenter

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.foxglove.hackernewsdemo.news.domain.repository.IHackerNewsRepository
import org.foxglove.hackernewsdemo.news.ui.view.IHackerNewsView

class HackerNewsPresenter(private val hackerNewsRepository: IHackerNewsRepository) : IHackerNewsPresenter {
    private var hackerNewsView: IHackerNewsView? = null

    private var job: Job? = null
    override fun attach(hackerNewsView: IHackerNewsView) {
        this.hackerNewsView = hackerNewsView
        job = Job()
    }

    override fun detach() {
        job?.cancel()
        job = null
        hackerNewsView = null
    }

    override fun fetchNews() {
        hackerNewsView?.apply {
            launch(UI) {
                showProgress()
                try {
                    val topStories = async {
                        hackerNewsRepository.getTopStories()
                    }.await()
                    showTopStories(topStories)
                } catch (throwable: Throwable) {
                    showError(throwable.message)
                } finally {
                    hideProgress()
                }
            }
        }
    }
}