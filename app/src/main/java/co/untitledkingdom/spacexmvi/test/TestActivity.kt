package co.untitledkingdom.spacexmvi.test

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import co.untitledkingdom.spacexmvi.R
import co.untitledkingdom.spacexmvi.base.MvvmiActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_test.changeColorButton
import kotlinx.android.synthetic.main.activity_test.colorView

class TestActivity : MvvmiActivity<TestViewState, TestView, TestViewModel>(), TestView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun changeColorButtonClicked(): Observable<TestIntent> =
        RxView.clicks(changeColorButton).map { TestIntent.GetColor }

    override fun setViewModel() = ViewModelProviders.of(this)[TestViewModel::class.java]

    override fun setView(): TestView = this

    override fun render(viewState: TestViewState) {
        with(viewState) {
            setDisplayColor(displayColor)
        }
    }

    override fun emitIntent(): Observable<TestIntent> = changeColorButtonClicked()

    private fun setDisplayColor(color: Int) {
        colorView.setBackgroundColor(color)
    }
}
