package com.example.stackexchange.binding

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.stackexchange.R
import com.example.stackexchange.vo.ResourceStatus
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        ResourceStatus.AUTH_REJECTED -> setVisibility(view, false)
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

@BindingAdapter("android:isAuthVisible")
fun setAuthVisible(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.AUTH_REJECTED -> setVisibility(view, true)
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

@BindingAdapter("android:imageUrl")
fun setImage(view: ImageView, link: String?){
    Glide.with(view).load(link).into(view)
}

@BindingAdapter("android:date")
fun setPrefixDate(view: TextView, date: Date?){
    val sdf = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, Locale.getDefault())
    date?.let {cDate ->
        val time = sdf.format(cDate)

        view.text = view.context.getString(R.string.profile_creation_time, time)
    }
}

