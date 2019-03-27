package co.untitledkingdom.spacexmvi.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMviActivity<VS : BaseMviViewState, V : BaseMviView<VS, *>, in M : BaseViewModel<VS, V, *>>(
    private val modelClass: Class<M>
) : AppCompatActivity() {

    private lateinit var viewModel: M
    private val key = "ACTIVITY_BUNDLE"

    protected abstract fun getView(): V

    // TODO think how it should be initialized due to dagger injection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(modelClass)

        if (!viewModel.alreadyInitialized() && savedInstanceState != null) {
            viewModel.setInitialViewState(
                savedInstanceState.getParcelable(key)
            )
        }

        initialize()
    }

    override fun onStart() {
        super.onStart()
        viewModel.bind()
    }

    override fun onStop() {
        viewModel.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.unsubscribe()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(key, viewModel.getViewState())
        super.onSaveInstanceState(outState)
    }

    private fun initialize() {
        viewModel.attachView(getView())
    }
}