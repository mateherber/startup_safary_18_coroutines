package org.foxglove.hackernewsdemo.core

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent

fun Context.openUrl(url: String) {
    CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))
}