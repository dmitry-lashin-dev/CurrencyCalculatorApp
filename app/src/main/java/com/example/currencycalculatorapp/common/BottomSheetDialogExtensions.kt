package com.example.currencycalculatorapp.common

import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun BottomSheetDialog.setStateExpandedAndHideable() {
    val bottomSheet =
        this.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
    bottomSheet?.let {
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(it)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isHideable = true
        behavior.isDraggable = true
        behavior.isFitToContents = true
    }
}