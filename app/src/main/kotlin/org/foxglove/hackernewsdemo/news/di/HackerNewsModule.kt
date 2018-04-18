package org.foxglove.hackernewsdemo.news.di

import android.app.Activity
import org.foxglove.hackernewsdemo.news.data.repository.HackerNewsRepository
import org.foxglove.hackernewsdemo.news.data.service.HackerNewsRetrofit
import org.foxglove.hackernewsdemo.news.data.service.HackerNewsService
import org.foxglove.hackernewsdemo.news.data.service.IHackerNewsService
import org.foxglove.hackernewsdemo.news.data.transformer.HackerNewsResultTransformer
import org.foxglove.hackernewsdemo.news.data.transformer.IHackerNewsResultTransformer
import org.foxglove.hackernewsdemo.news.ui.view.HackerNewsView
import org.foxglove.hackernewsdemo.news.ui.view.IHackerNewsView
import org.foxglove.hackernewsdemo.news.domain.repository.IHackerNewsRepository
import org.foxglove.hackernewsdemo.news.presentation.presenter.HackerNewsPresenter
import org.foxglove.hackernewsdemo.news.presentation.presenter.IHackerNewsPresenter
import org.kodein.Kodein
import org.kodein.android.androidScope
import org.kodein.generic.bind
import org.kodein.generic.instance
import org.kodein.generic.scoped
import org.kodein.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val newsModule = Kodein.Module {
    bind<IHackerNewsView>() with scoped(androidScope<Activity>()).singleton { HackerNewsView(instance()) }
    bind<IHackerNewsResultTransformer>() with singleton { HackerNewsResultTransformer() }
    bind<HackerNewsRetrofit>() with singleton {
        Retrofit.Builder()
            .baseUrl("https://hacker-news.firebaseio.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(instance()))
            .build()
            .create(HackerNewsRetrofit::class.java)
    }
    bind<IHackerNewsService>() with singleton { HackerNewsService(instance()) }
    bind<IHackerNewsRepository>() with singleton { HackerNewsRepository(instance(), instance()) }
    bind<IHackerNewsPresenter>() with scoped(androidScope<Activity>()).singleton {
        HackerNewsPresenter(instance())
    }
}