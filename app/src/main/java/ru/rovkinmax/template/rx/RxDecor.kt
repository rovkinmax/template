package ru.rovkinmax.template.rx


import io.reactivex.*
import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher
import retrofit2.HttpException
import ru.rovkinmax.template.view.EmptyView
import ru.rovkinmax.template.view.ErrorView
import ru.rovkinmax.template.view.LoadingView
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object RxDecor {

    fun <T> loading(view: LoadingView): LoadingViewTransformer<T, T> = LoadingViewTransformer(view)

    fun error(view: ErrorView, errorMapper: ErrorMapper = DefaultMapper()): Consumer<Throwable> {
        return Consumer { e ->
            Timber.tag(javaClass.simpleName).d(e, "")
            when (e) {
                is HttpException -> {
                    if (e.code() == HttpURLConnection.HTTP_UNAUTHORIZED) view.logout()
                    else view.showErrorMessage(errorMapper.provideMessage(e))
                }
                is SocketTimeoutException -> view.showNetworkError()
                is UnknownHostException -> view.showNetworkError()
                else -> view.showUnexpectedError()
            }
        }
    }

    fun <T> emptyStub(view: EmptyView): EmptyStubTransformer<T, T> = EmptyStubTransformer(view)

    class EmptyStubTransformer<T : R, R>(private val view: EmptyView) : ObservableTransformer<T, R>, FlowableTransformer<T, R> {

        override fun apply(upstream: Flowable<T>): Publisher<R> {
            return upstream.doOnSubscribe { view.hideEmptyStub() }.switchIfEmpty(emptyFlowable(view)).map { it }
        }

        override fun apply(upstream: Observable<T>): ObservableSource<R> {
            return upstream.doOnSubscribe { view.hideEmptyStub() }.switchIfEmpty(emptyObservable(view)).map { it }
        }

        private fun <T> emptyObservable(view: EmptyView): Observable<T> {
            return Observable.create<T>({ it.onComplete() }).doOnComplete({ view.showEmptyStub() })
        }

        private fun <T> emptyFlowable(view: EmptyView): Flowable<T> {
            return Flowable.create<T>({ it.onComplete() }, BackpressureStrategy.ERROR).doOnComplete { view.showEmptyStub() }
        }
    }

    class LoadingViewTransformer<T : R, R>(private val loadingView: LoadingView) : ObservableTransformer<T, R>,
            SingleTransformer<T, R>, FlowableTransformer<T, R>, CompletableTransformer, MaybeTransformer<T, R> {

        override fun apply(upstream: Completable): CompletableSource {
            return upstream
                    .doOnSubscribe { loadingView.showLoadingIndicator() }
                    .doFinally { loadingView.hideLoadingIndicator() }
        }

        override fun apply(upstream: Flowable<T>): Publisher<R> {
            return upstream
                    .doOnSubscribe { loadingView.showLoadingIndicator() }
                    .doFinally { loadingView.hideLoadingIndicator() }
                    .map { it }
        }

        override fun apply(upstream: Single<T>): SingleSource<R> {
            return upstream
                    .doOnSubscribe { loadingView.showLoadingIndicator() }
                    .doFinally { loadingView.hideLoadingIndicator() }
                    .map { it }
        }

        override fun apply(upstream: Observable<T>): ObservableSource<R> {
            return upstream
                    .doOnSubscribe { loadingView.showLoadingIndicator() }
                    .doFinally { loadingView.hideLoadingIndicator() }
                    .map { it }
        }

        override fun apply(upstream: Maybe<T>): MaybeSource<R> {
            return upstream
                    .doOnSubscribe { loadingView.showLoadingIndicator() }
                    .doFinally { loadingView.hideLoadingIndicator() }
                    .map { it }
        }
    }
}