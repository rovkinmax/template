package ru.rovkinmax.template.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.rovkinmax.template.rx.LifecycleProvider


abstract class Presenter<V : MvpView> : MvpPresenter<V>() {

    private val provider = LifecycleProvider()

    protected fun <T> lifecycle() = provider.lifecycle<T>()

    override fun destroyView(view: V) {
        super.destroyView(view)
        provider.unsubscribe()
    }
}