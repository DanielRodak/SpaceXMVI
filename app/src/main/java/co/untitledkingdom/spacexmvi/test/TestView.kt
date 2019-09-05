package co.untitledkingdom.spacexmvi.test

import co.untitledkingdom.spacexmvi.base.MvvmiView
import io.reactivex.Observable

interface TestView : MvvmiView<TestViewState, TestIntent>{
    fun changeColorButtonClicked() : Observable<TestIntent>
}