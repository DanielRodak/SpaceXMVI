package co.untitledkingdom.spacexmvi.base

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class BaseMviFragment<VS : BaseMviViewState, V : BaseMviView<VS>, P : BaseMviPresenter<VS, V, *>> :
    Fragment() {

    private lateinit var presenter: P
    private val retainedTag = "FRAGMENT_HOLDER"
    private val key = "FRAGMENT_BUNDLE"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //        attachPresenter()
        presenter = getPresenter()

        savedInstanceState?.let { savedInstance ->
            presenter.initState(savedInstance.getParcelable(key))
        }

        initialize()
    }

    override fun onStart() {
        super.onStart()
        presenter.bind()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.deinitialize()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveLastViewState(key, outState)
        super.onSaveInstanceState(outState)
    }

    abstract fun view(): V

    abstract fun getPresenter(): P

    private fun initialize() {
        presenter.attachView(view())
    }

    @Suppress("UNCHECKED_CAST")
    private fun attachPresenter() {
        var retainedFragment = activity?.supportFragmentManager?.findFragmentByTag(retainedTag)

        if (retainedFragment == null) {
            retainedFragment = BaseRetainedFragment<VS, P>()
            activity?.supportFragmentManager?.beginTransaction()?.add(retainedFragment, retainedTag)
                ?.commit()

            presenter = getPresenter()
            retainedFragment.setPresenter(presenter)
        } else {
            presenter = (retainedFragment as BaseRetainedFragment<VS, P>).getPresenter() ?: getPresenter()
            retainedFragment.setPresenter(presenter)
            retainedFragment.getInfoFromBundle()
        }
    }
}