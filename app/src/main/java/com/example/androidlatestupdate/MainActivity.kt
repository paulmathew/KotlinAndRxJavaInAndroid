package com.example.androidlatestupdate

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val productService =
        RetrofitClientInstance.retrofitInstance.create(ProductService::class.java)
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)

//        simpleObserver()
//        createObservable()

        button.clicks()
            .throttleFirst(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("Project ", "Button Clicked")
                implementNetworkCall()
            }


    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun implementNetworkCall() {
        Toast.makeText(this, "API CALL STARTED", Toast.LENGTH_LONG).show()
        compositeDisposable.add(
            productService.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> Log.e("API CALL ", response.toString()) },
                    { error -> Log.e("API CALL ", error.toString()) }
                )
        )


    }

    private fun createObservable() {
        val observable = Observable.create<String> {
            it.onNext("One")
            it.onNext("two")
            it.onComplete()
        }

        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

                Log.e("Project 1", "Subscribed")
            }

            override fun onError(e: Throwable) {
                Log.e("Project 1", "Error ${e.message}")
            }

            override fun onComplete() {
                Log.e("Project 1", "Compleated")
            }

            override fun onNext(t: String) {
// all the data update will come  in this function
                Log.e("Project 1", "On Next $t")
            }

        })
    }

    private fun simpleObserver() {
        val list = listOf("A", "B", "C")
        val observable = Observable.fromIterable(list)

        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

                Log.e("Project ", "Subscribed")
            }

            override fun onError(e: Throwable) {
                Log.e("Project ", "Error ${e.message}")
            }

            override fun onComplete() {
                Log.e("Project ", "Compleated")
            }

            override fun onNext(t: String) {
// all the data update will come  in this function
                Log.e("Project ", "On Next $t")
            }

        })
    }
}