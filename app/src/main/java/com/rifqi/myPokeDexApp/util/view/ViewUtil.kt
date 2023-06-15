package com.rifqi.myPokeDexApp.util.view

import android.view.View
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout

fun showShimmer(
    shimmerLayout: ShimmerFrameLayout,
    loadingView: View,
    actualLayout: View? = null,
    isLoading: Boolean
) {
    actualLayout?.visibility = if(isLoading) View.INVISIBLE else View.VISIBLE
    loadingView.isVisible = isLoading
    if(isLoading){
        shimmerLayout.startShimmer()
    } else {
        shimmerLayout.stopShimmer()
    }
}