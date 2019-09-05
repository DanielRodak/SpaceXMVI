package co.untitledkingdom.spacexmvi.test

import android.graphics.Color
import co.untitledkingdom.spacexmvi.base.MvvmiViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestViewState(
    val displayColor: Int = Color.BLACK
): MvvmiViewState