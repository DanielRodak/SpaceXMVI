package co.untitledkingdom.spacexmvi.test

import android.graphics.Color
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TestInteractor {

    fun getNewColor(): Observable<TestAction> =
        Observable.just(getRandomColor()).map<TestAction> {
            TestAction.ColorFetched(it)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun getRandomColor() =
         listOf(Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE, Color.CYAN).shuffled().first()

}