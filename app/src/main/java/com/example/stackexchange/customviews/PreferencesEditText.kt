package com.example.stackexchange.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.stackexchange.R
import com.example.stackexchange.interfaces.DefaultCallback
import com.example.stackexchange.utils.getDrawableByRef
import timber.log.Timber

class PreferencesEditText : CleanableEditText{
    constructor(context: Context)
            : super(context)
    constructor(context: Context, attributeSet: AttributeSet?)
            : super(context, attributeSet, android.R.attr.editTextStyle)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)


    private var preferenceClearIconDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.preferences_clear_drawable)
    private var preferenceIconDrawable: Drawable? = getDrawableByRef(context, R.attr.preference_drawable)
    override var clearIconDrawable: Drawable? = preferenceClearIconDrawable

    protected var preferenceClickCallback: DefaultCallback? = null

    init {
        setDrawableRight(preferenceIconDrawable)
        isSingleLine = true
        imeOptions = EditorInfo.IME_ACTION_DONE
    }


    override fun removeClearIcon() {
        if(isClearIconShown)
        {
            setDrawableRight(preferenceIconDrawable)
            isClearIconShown = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_UP)
            if (isClearIconTouched(event)) {
                Timber.d("CLEAR ICON TOUCHED")
                removeClearIcon()

                text = null
                event.action = MotionEvent.ACTION_CANCEL
            }
            else if (isPrefIconTouched(event)) {
                preferenceClickCallback?.run()

                event.action = MotionEvent.ACTION_CANCEL
            }

        return super.onTouchEvent(event)
    }

    override fun isClearIconTouched(event: MotionEvent?): Boolean {
        if(isClearIconShown){
            val x = event?.x ?: 0F

            if(x >= width - compoundPaddingRight + (preferenceIconDrawable?.intrinsicWidth ?: 0))
                return true
        }

        return false
    }

    protected open fun isPrefIconTouched(event: MotionEvent?): Boolean{
        val x = event?.x ?: 0F

        if(isClearIconShown){
            if(x >= width - compoundPaddingRight && x <= width - compoundPaddingRight + (clearIconDrawable?.intrinsicWidth ?: 0))
                return true
        }
        else{
            if(x >= width - compoundPaddingRight)
                return true
        }

        return false
    }

    fun addPreferenceClickCallback(callback: DefaultCallback) {
        preferenceClickCallback = callback
    }
}