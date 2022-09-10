package com.jaydeepbhayani.league.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.snackbar.Snackbar
import com.jaydeepbhayani.league.R

fun NavController.navigateSafely(
    directions: NavDirections,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    if (currentDestination?.getAction(directions.actionId) != null)
        if (builder == null)
            navigate(directions)
        else
            navigate(directions, navOptions(builder))
}

val Fragment.navController: NavController?
    get() = if (isAdded) findNavController() else null

fun Activity.showKeyboard(view: View) = WindowInsetsControllerCompat(window, view).show(
    WindowInsetsCompat.Type.ime()
)

fun Activity.hideKeyboard(view: View) = WindowInsetsControllerCompat(window, view).hide(
    WindowInsetsCompat.Type.ime()
)

fun View.showKeyboardUsingImm() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

internal fun View?.show() {
    this?.visibility = View.VISIBLE
}

internal fun View?.hide() {
    this?.visibility = View.GONE
}

fun View.disableLook() {
    this.alpha = .5f
    this.isEnabled = false
}

fun View.enableLook() {
    this.alpha = 1f
    this.isEnabled = true
}

internal fun View.visible() {
    animate()
        .setDuration(DefaultAnimationDuration)
        //.translationY(this.height.toFloat())
        .alpha(1.0f)
        .withEndAction {
            this.visibility = View.VISIBLE
        }
}

internal fun View.gone() {
    animate()
        .setDuration(DefaultAnimationDuration)
        .translationY(0f)
        .alpha(0.0f)
        .withStartAction {
            visibility = View.GONE
        }
}

fun View.snackbar(
    @StringRes stringId: Int,
    @DrawableRes drawableId: Int? = null,
    @IdRes anchorViewId: Int? = null,
    color: AppColor? = null,
    vararg formatArgs: Any? = emptyArray(),
    vibrate: Boolean = false,
) = Snackbar.make(this, context.stringResource(stringId, *formatArgs), Snackbar.LENGTH_SHORT)
    .apply {
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        if (anchorViewId != null) setAnchorView(anchorViewId)
        if (drawableId != null) {
            textView?.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableId, 0, 0, 0)
            textView?.compoundDrawablePadding =
                context.dimenResource(R.dimen.spacing_normal).toInt()
            textView?.gravity = Gravity.CENTER
        }
        if (color != null) {
            val backgroundColor = context.colorResource(color.toResource())
            val contentColor = context.colorAttributeResource(R.attr.appBackgroundColor)
            setBackgroundTint(backgroundColor)
            setTextColor(contentColor)
            textView?.compoundDrawablesRelative?.get(0)?.mutate()?.setTint(contentColor)
        } else {
            val backgroundColor = context.colorAttributeResource(R.attr.appPrimaryColor)
            val contentColor = context.colorAttributeResource(R.attr.appBackgroundColor)
            setBackgroundTint(backgroundColor)
            setTextColor(contentColor)
            textView?.compoundDrawablesRelative?.get(0)?.mutate()?.setTint(contentColor)
        }
        if (vibrate) performClickHapticFeedback()
        show()
    }

fun View.snackbar(
    string: String,
    @DrawableRes drawableId: Int? = null,
    @IdRes anchorViewId: Int? = null,
    color: AppColor? = null,
    duration: Int? = null,
    vibrate: Boolean = false,
) = Snackbar.make(this, string, duration ?: Snackbar.LENGTH_SHORT).apply {
    animationMode = Snackbar.ANIMATION_MODE_SLIDE
    val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    if (anchorViewId != null) setAnchorView(anchorViewId)
    if (drawableId != null) {
        textView?.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableId, 0, 0, 0)
        textView?.compoundDrawablePadding = context.dimenResource(R.dimen.spacing_normal).toInt()
        textView?.gravity = Gravity.CENTER
    }
    if (color != null) {
        val backgroundColor = context.colorResource(color.toResource())
        val contentColor = context.colorAttributeResource(R.attr.appBackgroundColor)
        setBackgroundTint(backgroundColor)
        setTextColor(contentColor)
        textView?.compoundDrawablesRelative?.get(0)?.mutate()?.setTint(contentColor)
    } else {
        val backgroundColor = context.colorAttributeResource(R.attr.appPrimaryColor)
        val contentColor = context.colorAttributeResource(R.attr.appBackgroundColor)
        setBackgroundTint(backgroundColor)
        setTextColor(contentColor)
        textView?.compoundDrawablesRelative?.get(0)?.mutate()?.setTint(contentColor)
    }
    if (vibrate) performClickHapticFeedback()
    show()
}

fun View.performClickHapticFeedback() =
    performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
    )

fun View.performLongClickHapticFeedback() =
    performHapticFeedback(
        HapticFeedbackConstants.LONG_PRESS,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
    )

fun Drawable.setRippleColor(colorStateList: ColorStateList) {
    val rippleDrawable = mutate() as RippleDrawable
    rippleDrawable.setColor(colorStateList.withAlpha(32))
}

internal fun String.textStyle(
    context: Context,
    typeface: Int = Typeface.NORMAL,
    colorInt: Int = R.color.black
): SpannableStringBuilder {
    val str = SpannableStringBuilder(String.format(this).replace(this, this))
    this.let {
        str.setSpan(
            StyleSpan(typeface),
            str.indexOf(this),
            str.indexOf(this) + this.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        str.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, colorInt)),
            str.indexOf(this),
            str.indexOf(this) + this.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return str
}

fun <R> (() -> R).withDelay(delay: Long = 250L) {
    Looper.myLooper()?.let {
        Handler(it).postDelayed({ this.invoke() }, delay)
    }
}

fun withDelay(delay: Long = 500, block: () -> Unit) {
    Looper.myLooper()?.let {
        Handler(it).postDelayed(Runnable(block), delay)
    }
}