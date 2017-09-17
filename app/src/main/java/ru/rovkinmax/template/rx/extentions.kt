package ru.rovkinmax.template.rx

import io.reactivex.*

fun <T> Observable<T>.async(): Observable<T> = subscribeOn(RxSchedulers.io()).observeOn(RxSchedulers.main())

fun <T> Flowable<T>.async(): Flowable<T> = subscribeOn(RxSchedulers.io()).observeOn(RxSchedulers.main())

fun <T> Single<T>.async(): Single<T> = subscribeOn(RxSchedulers.io()).observeOn(RxSchedulers.main())

fun <T> Maybe<T>.async(): Maybe<T> = subscribeOn(RxSchedulers.io()).observeOn(RxSchedulers.main())

fun Completable.async(): Completable = subscribeOn(RxSchedulers.io()).observeOn(RxSchedulers.main())

fun <T> Observable<T>.toListMayBe(): Maybe<List<T>> = toList().toMaybe()

fun <T> Flowable<T>.toListMayBe(): Maybe<List<T>> = toList().toMaybe()