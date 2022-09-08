package com.jaydeepbhayani.league.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import com.jaydeepbhayani.league.R

fun Context.colorResource(@ColorRes id: Int) = ResourcesCompat.getColor(resources, id, null)
fun Context.stringResource(@StringRes id: Int, vararg formatArgs: Any? = emptyArray()) =
    getString(id, *formatArgs)

fun Context.dimenResource(@DimenRes id: Int) = resources.getDimension(id)
fun Context.colorAttributeResource(@AttrRes id: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)


fun AppColor.toResource(): Int = when (this) {
    AppColor.Blue -> R.color.colorAccentBlue
    AppColor.Gray -> R.color.colorAccentGray
    AppColor.Pink -> R.color.colorAccentPink
    AppColor.Cyan -> R.color.colorAccentCyan
    AppColor.Purple -> R.color.colorAccentPurple
    AppColor.Red -> R.color.colorAccentRed
    AppColor.Yellow -> R.color.colorAccentYellow
    AppColor.Orange -> R.color.colorAccentOrange
    AppColor.Green -> R.color.colorAccentGreen
    AppColor.Brown -> R.color.colorAccentBrown
    AppColor.BlueGray -> R.color.colorAccentBlueGray
    AppColor.Teal -> R.color.colorAccentTeal
    AppColor.Indigo -> R.color.colorAccentIndigo
    AppColor.DeepPurple -> R.color.colorAccentDeepPurple
    AppColor.DeepOrange -> R.color.colorAccentDeepOrange
    AppColor.DeepGreen -> R.color.colorAccentDeepGreen
    AppColor.LightBlue -> R.color.colorAccentLightBlue
    AppColor.LightGreen -> R.color.colorAccentLightGreen
    AppColor.LightRed -> R.color.colorAccentLightRed
    AppColor.LightPink -> R.color.colorAccentLightPink
    AppColor.Black -> R.color.colorAccentBlack
    AppColor.Error -> R.color.error
    AppColor.Success -> R.color.success
}

val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

enum class AppColor {
    Gray, Blue, Pink, Cyan, Purple, Red,
    Yellow, Orange, Green, Brown, BlueGray, Teal,
    Indigo, DeepPurple, DeepOrange, DeepGreen,
    LightBlue, LightGreen, LightRed, LightPink, Black, Error, Success
}

enum class Font { Nunito }