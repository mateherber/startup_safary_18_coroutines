package org.foxglove.hackernewsdemo.core

import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.util.TypedValue
import android.view.ViewManager
import net.cachapa.expandablelayout.ExpandableLayout
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.ctx
import org.jetbrains.anko.custom.ankoView

fun Context.attr(@AttrRes attribute: Int): TypedValue {
    val typed = TypedValue()
    ctx.theme.resolveAttribute(attribute, typed, true)
    return typed
}

fun Context.dimenAttr(@AttrRes attribute: Int): Int =
    TypedValue.complexToDimensionPixelSize(attr(attribute).data, resources.displayMetrics)

@ColorInt
fun Context.colorAttr(@AttrRes attribute: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(attr(attribute).resourceId, ctx.theme)
    } else {
        resources.getColor(attr(attribute).resourceId)
    }
}

inline fun ViewManager.expandableLayout(init: (@AnkoViewDslMarker ExpandableLayout).() -> Unit): ExpandableLayout {
    return ankoView({ ctx: Context -> ExpandableLayout(ctx) }, theme = 0) { init() }
}