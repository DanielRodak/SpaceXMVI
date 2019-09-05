package co.untitledkingdom.spacexmvi.test

import co.untitledkingdom.spacexmvi.base.MvvmiAction

sealed class TestAction : MvvmiAction<TestViewState> {

    class ColorFetched(private val color: Int) : TestAction() {
        override fun reduce(previousState: TestViewState): TestViewState {
            return TestViewState(color)
        }
    }

}