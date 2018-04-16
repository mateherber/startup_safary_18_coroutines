package org.foxglove.hackernewsdemo.news.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.foxglove.hackernewsdemo.news.ui.view.IHackerNewsView
import org.jetbrains.anko.setContentView
import org.kodein.KodeinAware
import org.kodein.android.closestKodein
import org.kodein.generic.instance
import org.kodein.generic.kcontext

class HackerNewsActivity : AppCompatActivity(), KodeinAware {
    override val kodeinContext = kcontext(this)
    override val kodein by closestKodein()

    private val view by instance<IHackerNewsView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view.setContentView(this)
        savedInstanceState?.also {
            view.restore(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        view.save(outState)
    }

    override fun onResume() {
        super.onResume()
        view.resume()
    }

    override fun onPause() {
        super.onPause()
        view.pause()
    }
}



