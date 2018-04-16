package org.foxglove.hackernewsdemo.application

import android.app.Application
import android.content.Context
import org.foxglove.hackernewsdemo.application.di.applicationModule
import org.foxglove.hackernewsdemo.news.di.newsModule
import org.kodein.Kodein
import org.kodein.KodeinAware
import org.kodein.generic.bind
import org.kodein.generic.instance

class HackerNewsApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind<Context>() with instance(this@HackerNewsApplication)
        import(applicationModule)
        import(newsModule)
    }
}

