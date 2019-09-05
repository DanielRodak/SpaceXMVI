package co.untitledkingdom.spacexmvi.test

import co.untitledkingdom.spacexmvi.base.MvvmiIntent
import co.untitledkingdom.spacexmvi.base.MvvmiModel

import io.reactivex.Observable

class TestViewModel(private val interactor: TestInteractor = TestInteractor()): MvvmiModel<TestViewState, TestView, TestAction>(){
    override val defaultViewState: TestViewState
        get() = TestViewState()

    override fun <I : MvvmiIntent> intentToAction(intent: I): Observable<TestAction> =
        when (intent as TestIntent) {
            is TestIntent.GetColor -> interactor.getNewColor()
        }

}