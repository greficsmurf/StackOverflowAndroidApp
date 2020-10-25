package com.example.stackexchange.binding

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.stackexchange.R
import com.example.stackexchange.vo.ResourceStatus
import com.google.android.material.switchmaterial.SwitchMaterial

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, isVisible: Boolean){
    if(isVisible)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.LOADED -> setVisibility(view, false)
        ResourceStatus.FAILED -> setVisibility(view, false)
        ResourceStatus.LOADING -> setVisibility(view, true)
        null -> setVisibility(view, true)
    }
}

@BindingAdapter("android:isErrorVisible")
fun setErrorVisibility(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.FAILED -> setVisibility(view, true)
        else -> setVisibility(view, false)
    }
}

@BindingAdapter("android:isAnswered")
fun setAnsweredBackground(view: TextView, isAnswered: Boolean){
    if(isAnswered){
        view.setTextColor(Color.WHITE)
        view.background = ContextCompat.getDrawable(view.context, R.drawable.is_answered_drawable)
    }
}

fun setThemeSwitch(view: SwitchCompat, themeMode: Int?){

}