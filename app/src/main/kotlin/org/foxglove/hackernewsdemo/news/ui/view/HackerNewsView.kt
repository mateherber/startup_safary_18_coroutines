package org.foxglove.hackernewsdemo.news.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.toast
import net.cachapa.expandablelayout.ExpandableLayout
import net.cachapa.expandablelayout.ExpandableLayout.VERTICAL
import org.foxglove.hackernewsdemo.R
import org.foxglove.hackernewsdemo.core.colorAttr
import org.foxglove.hackernewsdemo.core.dimenAttr
import org.foxglove.hackernewsdemo.core.expandableLayout
import org.foxglove.hackernewsdemo.core.openUrl
import org.foxglove.hackernewsdemo.news.entity.Story
import org.foxglove.hackernewsdemo.news.presentation.presenter.IHackerNewsPresenter
import org.foxglove.hackernewsdemo.news.ui.activity.HackerNewsActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class HackerNewsView(
    private val presenter: IHackerNewsPresenter
) : IHackerNewsView {
    private lateinit var context: Context

    private lateinit var content: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun restore(savedInstanceState: Bundle) {
    }

    override fun save(outState: Bundle) {
    }

    override fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = GONE
    }

    override fun showError(message: String?) {
        context.toast(message ?: "Unknown Error", Toast.LENGTH_LONG)
    }

    @SuppressLint("SetTextI18n")
    override fun showTopStories(topStories: List<Story>) {
        content.removeAllViews()
        AnkoContext.createDelegate(content).run {
            topStories.forEach { story ->
                verticalLayout {
                    lateinit var expandableLayout: ExpandableLayout
                    textView {
                        text = story.title
                        textSize = 16f
                        setTypeface(null, BOLD)
                        setTextColor(ContextCompat.getColor(ctx, R.color.text_primary_dark))
                        setOnClickListener {
                            ctx.openUrl(story.url)
                        }
                    }.lparams {
                        topMargin = dip(4)
                        marginStart = dip(8)
                        marginEnd = dip(8)
                        bottomMargin = dip(1)
                    }
                    textView {
                        text = "${story.comments.size} Comments"
                        textSize = 14f
                        setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary_dark))
                        setOnClickListener {
                            expandableLayout.isExpanded = !expandableLayout.isExpanded
                        }
                    }.lparams {
                        topMargin = dip(1)
                        marginStart = dip(8)
                        marginEnd = dip(8)
                        bottomMargin = dip(4)
                    }
                    expandableLayout = expandableLayout {
                        isExpanded = false
                        orientation = VERTICAL
                        verticalLayout {
                            setOnClickListener {
                                expandableLayout.isExpanded = !expandableLayout.isExpanded
                            }
                            story.comments.forEach { comment ->
                                textView {
                                    text = Html.fromHtml(comment.text)
                                }.lparams {
                                    topMargin = dip(2)
                                    marginStart = dip(24)
                                    marginEnd = dip(8)
                                    bottomMargin = dip(2)
                                }
                            }
                        }
                    }.lparams {
                        bottomMargin = dip(8)
                        topMargin = dip(8)
                    }
                }
            }
        }
    }

    override fun resume() {
        presenter.attach(this)
        presenter.fetchNews()
    }

    override fun pause() {
        presenter.detach()
    }

    override fun createView(ui: AnkoContext<HackerNewsActivity>) = ui.run {
        context = ctx
        verticalLayout {
            themedToolbar(R.style.ThemeOverlay_AppCompat_ActionBar) {
                title = ""
                elevation = dip(4).toFloat()
                backgroundColor = ctx.colorAttr(android.R.attr.colorPrimary)
                setTitleTextColor(ContextCompat.getColor(ctx, R.color.text_primary_light))
                popupTheme = R.style.ThemeOverlay_AppCompat_Light
                imageView {
                    imageResource = R.mipmap.ic_launcher
                }.lparams {
                    gravity = Gravity.START
                    width = wrapContent
                    height = wrapContent
                }
                textView {
                    text = ctx.getString(R.string.app_name)
                    textColorResource = R.color.text_primary_light
                    textSize = 20f
                }.lparams {
                    gravity = Gravity.CENTER
                    width = wrapContent
                    height = wrapContent
                }
            }.lparams {
                width = matchParent
                height = ctx.dimenAttr(R.attr.actionBarSize)
            }.also { owner.setSupportActionBar(it) }
            frameLayout {
                progressBar = progressBar {
                    isIndeterminate = true
                    indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(ctx, R.color.primary),
                        PorterDuff.Mode.SRC_IN
                    )
                    visibility = GONE
                }.lparams {
                    gravity = Gravity.CENTER
                }
                scrollView {
                    content = verticalLayout {
                        backgroundColorResource = R.color.background_card
                    }.lparams {
                        height = matchParent
                        width = matchParent
                    }
                }.lparams {
                    height = matchParent
                    width = matchParent
                }
            }.lparams {
                height = matchParent
                width = matchParent
            }
        }
    }
}
