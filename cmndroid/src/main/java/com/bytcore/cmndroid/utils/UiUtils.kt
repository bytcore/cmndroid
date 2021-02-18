package com.bytcore.cmndroid.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

object UiUtils {
    fun getCompatColor(context: Context?, @ColorRes colorRes: Int): Int {
        if (context == null) return Color.TRANSPARENT

        return ResourcesCompat.getColor(context.resources, colorRes, context.theme)
    }

    fun getCompatDrawable(context: Context?, @DrawableRes drawableRes: Int): Drawable? {
        context ?: return null
        return ResourcesCompat.getDrawable(context.resources, drawableRes, context.theme)
    }

    fun getLaunchingIntent(context: Context?): Intent? {
        context ?: return null
        return context.packageManager.getLaunchIntentForPackage(context.packageName)
    }

    fun relaunch(context: Context?) {
        context ?: return

        val intent = getLaunchingIntent(context)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun applyDimen(context: Context?, value: Float): Float {
        context ?: return 0f

        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context.resources.displayMetrics
        )
    }

    fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels
}
