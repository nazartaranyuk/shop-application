package com.nazartaraniuk.shopapplication.presentation.search_screen

import android.util.Log
import com.nazartaraniuk.shopapplication.domain.usecases.GetSearchProductsUseCase
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchFragmentPresenter @Inject constructor(
    getSearchProductsUseCase: GetSearchProductsUseCase,
    private val mapper: ToUiModelMapper
) : MainContract.Presenter {

    private val TAG = "Main Presenter"
    var view: MainContract.View? = null
    private val compositeDisposable = CompositeDisposable()
    private val _searchDataObservable = getSearchProductsUseCase().map { list ->
        list.map(mapper::toProductItemModel)
    }
    private val searchDataObservable: Observable<List<ProductItemModel>>
        get() = _searchDataObservable

    fun attachView(view: MainContract.View) { this.view = view }

    private val observer: DisposableObserver<List<ProductItemModel>>
        get() = object : DisposableObserver<List<ProductItemModel>>() {
            override fun onNext(t: List<ProductItemModel>) {
                view?.displayData(t)
            }

            override fun onError(e: Throwable) {
                view?.displayError(e.message ?: "Unknown error")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }

        }

    fun filterList(list: List<ProductItemModel>, text: String): List<ProductItemModel> {
        return list.filter { it.title.lowercase().contains(text.lowercase()) }
    }

    override fun getAllData() {
        val searchDataDisposable = searchDataObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchDataDisposable)
    }
}