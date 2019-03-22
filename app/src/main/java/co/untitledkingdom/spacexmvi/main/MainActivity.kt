package co.untitledkingdom.spacexmvi.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.untitledkingdom.spacexmvi.R
import co.untitledkingdom.spacexmvi.base.BaseMviActivity
import co.untitledkingdom.spacexmvi.list.RocketsAdapter
import co.untitledkingdom.spacexmvi.models.Rocket
import co.untitledkingdom.spacexmvi.simple.SimpleFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.clearButton
import kotlinx.android.synthetic.main.activity_main.errorTextView
import kotlinx.android.synthetic.main.activity_main.fragmentButton
import kotlinx.android.synthetic.main.activity_main.fragmentContainer
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.rocketsRecyclerView
import kotlinx.android.synthetic.main.activity_main.showMeRocketsButton

class MainActivity :
    BaseMviActivity<MainViewState, MainView, MainMviPresenter>(),
    MainView {

    private val rocketsAdapter = RocketsAdapter()

    private val buttonSubject = PublishSubject.create<Boolean>()
    private val clearSubject = PublishSubject.create<Boolean>()
    private val fragmentSubject = PublishSubject.create<Boolean>()
    private val defaultTag = "TAG"

    private var presenter: MainMviPresenter =
        MainMviPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMeRocketsButton.setOnClickListener {
            fragmentContainer.visibility = View.GONE
            removeFragment()
            if (rocketsAdapter.itemCount == 0) buttonSubject.onNext(true)
        }

        clearButton.setOnClickListener {
            clearSubject.onNext(true)
            removeFragment()
        }

        fragmentButton.setOnClickListener {
            fragmentSubject.onNext(true)
        }

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

    override fun render(viewState: MainViewState) {
        with(viewState) {
            showProgressBar(progress)
            showError(error)
            showRocketList(rocketList)
            displayFragment(displayFragment)
        }
    }

    override fun getView(): MainView = this

    override fun getPresenter(): MainMviPresenter = presenter

    override fun emitButtonClick(): Observable<Boolean> = buttonSubject

    override fun emitClearButton(): Observable<Boolean> = clearSubject

    override fun emitFragmentClick(): Observable<Boolean> = fragmentSubject

    private fun displayFragment(showFragment: Boolean) {
        if (showFragment) {
            removeFragment()
            fragmentContainer.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, SimpleFragment(), defaultTag)
                .commit()
            fragmentSubject.onNext(false)
        }
    }

    private fun removeFragment() {
        supportFragmentManager.findFragmentByTag(defaultTag)?.let {
            fragmentContainer.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }
}