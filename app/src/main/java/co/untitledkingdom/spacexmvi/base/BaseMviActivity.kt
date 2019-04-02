package co.untitledkingdom.spacexmvi.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMviActivity<VS : BaseMviViewState, V : BaseMviView<VS>, P : BaseMviPresenter<VS, V, *>> :
    AppCompatActivity() {

    private lateinit var featurePresenter: P
    private val retainedTag = "HOLDER"

    override fun onCreate(savedInstanceState: Bundle?) {
        injection()
        super.onCreate(savedInstanceState)
        attachPresenter()

        initialize()
    }

    protected open fun injection() {}

    override fun onStart() {
        super.onStart()
        featurePresenter.bind()
    }

    override fun onStop() {
        featurePresenter.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        featurePresenter.deinitialize()
        super.onDestroy()
    }

    abstract fun getView(): V

    abstract fun getPresenter(): P

    private fun initialize() {
        featurePresenter.attachView(getView())
    }

    private fun attachPresenter() {
        if (!retainedFragmentHasInstance()) {
            createRetainedFragment()
        } else {
            getPresenterFromRetainedFragment()
        }
    }

    private fun retainedFragmentHasInstance() =
        (supportFragmentManager.findFragmentByTag(retainedTag) != null)

    private fun createRetainedFragment() {
        val retainedFragment = BaseRetainedFragment<VS, P>()
        supportFragmentManager.beginTransaction()
            .add(retainedFragment, retainedTag).commit()

        featurePresenter = getPresenter()
        setPresenterInstance(retainedFragment)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getPresenterFromRetainedFragment() {
        val retainedFragment = supportFragmentManager.findFragmentByTag(retainedTag)

        featurePresenter =
            (retainedFragment as BaseRetainedFragment<VS, P>).getPresenter() ?: getPresenter()
        setPresenterInstance(retainedFragment)

        retainedFragment.getInfoFromBundle()?.let { featurePresenter.initState(it) }
    }

    private fun setPresenterInstance(retainedFragment: BaseRetainedFragment<VS, P>) {
        retainedFragment.setPresenter(featurePresenter)
    }
}