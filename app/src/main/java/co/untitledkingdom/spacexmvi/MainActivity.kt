package co.untitledkingdom.spacexmvi

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.untitledkingdom.spacexmvi.base.BaseMviActivity
import co.untitledkingdom.spacexmvi.list.RocketsAdapter
import co.untitledkingdom.spacexmvi.models.Rocket
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.clearButton
import kotlinx.android.synthetic.main.activity_main.errorTextView
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.rocketsRecyclerView
import kotlinx.android.synthetic.main.activity_main.showMeRocketsButton

class MainActivity :
        BaseMviActivity<MainView, MainViewModel>(MainViewModel::class.java),
        MainView {

    private val rocketsAdapter = RocketsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** */
        initializeViewModel()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rocketsRecyclerView.layoutManager = LinearLayoutManager(this)
        rocketsRecyclerView.adapter = rocketsAdapter
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            rocketsRecyclerView.visibility = View.GONE
            errorTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            rocketsRecyclerView.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun showError(show: Boolean) {
        if (show) {
            rocketsRecyclerView.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
        } else {
            errorTextView.visibility = View.GONE
        }
    }

    private fun showRocketList(rocketList: List<Rocket>) {
        rocketsAdapter.setRocketList(rocketList)
    }

    /** */
    override fun render(viewState: MainViewState) {
        with(viewState) {
            showProgressBar(progress)
            showError(error)
            showRocketList(rocketList)
        }
    }

    override fun emitButtonClick(): Observable<Boolean> =
            RxView.clicks(showMeRocketsButton).map { true }

    override fun emitClearButton(): Observable<Boolean> =
            RxView.clicks(clearButton).map { true }

    /** */
    override fun initializeViewModel() {
        initialize(this)
    }
}