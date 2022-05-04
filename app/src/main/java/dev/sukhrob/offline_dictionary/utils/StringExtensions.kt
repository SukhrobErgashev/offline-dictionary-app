package dev.sukhrob.offline_dictionary.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan


fun String.toColoredSpannable(query: String): Spannable {
    val span = SpannableString(this)
    if (query.isEmpty())
        return span

    
    val start = this.indexOf(query, ignoreCase = true)
    val end = start + query.length

    span.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return span
}