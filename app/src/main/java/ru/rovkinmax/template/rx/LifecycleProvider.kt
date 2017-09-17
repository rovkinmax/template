package ru.rovkinmax.template.rx

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.reactivestreams.Publisher
import java.util.concurrent.CancellationException

class LifecycleProvider {
    private var disposable: Disposable? = null
    private var observable: BehaviorSubject<Any>? = null

    internal fun unsubscribe() {
        if (disposable?.isDisposed?.not() == true) {
            observable?.onNext(Any())
            disposable?.dispose()
        }
        disposable = null
        observable = null
    }

    fun <T> lifecycle(): LifecycleTransformer<T, T> {
        if (observable == null || disposable == null) {
            observable = BehaviorSubject.create()
            disposable = observable!!.subscribe {}
        }
        return LifecycleTransformer(observable!!)
    }
}

class LifecycleTransformer<T : R, R>(private val lifecycle: Observable<Any>) : ObservableTransformer<T, R>, MaybeTransformer<T, R>,
        FlowableTransformer<T, R>, CompletableTransformer, SingleTransformer<T, R> {
    override fun apply(upstream: Single<T>): SingleSource<R> {
        return upstream.takeUntil(lifecycle.firstOrError()).map { it }
    }

    override fun apply(upstream: Completable): CompletableSource {
        return Completable.ambArray(upstream, lifecycle.flatMapCompletable { Completable.error(CancellationException()) })
    }

    override fun apply(upstream: Flowable<T>): Publisher<R> = upstream.takeUntil(lifecycle.toFlowable(BackpressureStrategy.LATEST)).map { it }

    override fun apply(upstream: Maybe<T>): MaybeSource<R> = upstream.takeUntil(lifecycle.firstElement()).map { it }

    override fun apply(upstream: Observable<T>): ObservableSource<R> = upstream.takeUntil(lifecycle).map { it }
}