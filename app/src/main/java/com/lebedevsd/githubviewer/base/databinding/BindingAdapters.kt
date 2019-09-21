package com.lebedevsd.githubviewer.base.databinding

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

/**
 * Binding adapter that loads an image into an ImageView using Glide
 */
@BindingAdapter("image_url")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

/**
 * Binding adapter that sets the view height based on two mandatory attributes:
 * @param matchParent Boolean, if set to true, will set the view's height to 'match_parent'
 * @param layoutHeight Dimen resource that. If [matchParent] is false, the value from this resource will be used
 *          to set the height of the view.
 */
@BindingAdapter("match_parent", "layout_height", requireAll = true)
fun setLayoutHeight(view: View, matchParent: Boolean, layoutHeight: Float) {
    val params = view.layoutParams
    if (matchParent) {
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
    } else {
        params.height = layoutHeight.roundToInt()
    }
    view.layoutParams = params
}

@BindingAdapter("asyncText")
fun setTextAsync(textView: AppCompatTextView, text: String) {
    val params = TextViewCompat.getTextMetricsParams(textView)
    textView.setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}
