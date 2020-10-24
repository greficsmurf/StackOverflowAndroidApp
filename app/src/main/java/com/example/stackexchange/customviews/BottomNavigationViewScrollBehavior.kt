package com.example.stackexchange.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ScrollingView
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

class BottomNavigationViewScrollBehavior(
        val context: Context,
        val attrs: AttributeSet
        ) : CoordinatorLayout.Behavior<BottomNavigationView>() {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))
    }



}