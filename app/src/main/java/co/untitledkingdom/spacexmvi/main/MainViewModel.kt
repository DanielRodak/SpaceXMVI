package co.untitledkingdom.spacexmvi.main

import co.untitledkingdom.spacexmvi.base.BaseMviIntent
import co.untitledkingdom.spacexmvi.base.BaseViewModel
import io.reactivex.Observable

class MainViewModel(private val mainInteractor: MainInteractor = MainInteractor()) :
    BaseViewModel<MainViewState, MainView, MainAction>() {

    override val defaultViewState: MainViewState
        get() = MainViewState()

    override fun <I : BaseMviIntent> intentToAction(intent: I): Observable<MainAction> =
        when (intent) {
            is MainIntent.FetchRocketsState -> mainInteractor.fetchRocketList().startWith(
                MainAction.ShowProgress
            )
            is MainIntent.ClearState -> just(MainAction.ClearStates)
            MainIntent.DisplayFragmentState(true) -> just(MainAction.DisplayFragment(true))
            MainIntent.DisplayFragmentState(false) -> just(MainAction.DisplayFragment(false))
            else -> just(MainAction.Nothing)
        }

    //    override fun <I : BaseMviIntent> intentWithoutAction(intent: I) {
    //        when (intent) {
    //            MainIntent.DisplayFragmentState(true) -> navigator.displayFragment(true)
    //        }
    //    }
}