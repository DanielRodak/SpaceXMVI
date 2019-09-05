package co.untitledkingdom.spacexmvi.test

import co.untitledkingdom.spacexmvi.base.MvvmiIntent

sealed class TestIntent : MvvmiIntent {
    object GetColor : TestIntent()
}