package com.example.androidlatestupdate

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)

//        simpleObserver()
//        createObservable()

        button.clicks()
            .throttleFirst(1500,TimeUnit.MILLISECONDS)
            .subscribe {
            Log.e("Project ","Button Clicked")
        }

        implementNetworkCall()
    }

    private fun implementNetworkCall() {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        val productService=retrofit.create(ProductService::class.java)
        productService.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("API CALL ",it.toString())
            }
    }

    private fun createObservable() {
        val observable=Observable.create<String> {
            it.onNext("One")
            it.onNext("two")
            it.onComplete()
        }

        observable.subscribe(object:Observer<String>{
            override fun onSubscribe(d: Disposable) {

                Log.e("Project 1","Subscribed")
            }

            override fun onError(e: Throwable) {
                Log.e("Project 1","Error ${e.message}")
            }

            override fun onComplete() {
                Log.e("Project 1","Compleated")
            }

            override fun onNext(t: String) {
// all the data update will come  in this function
                Log.e("Project 1","On Next $t")
            }

        })
    }

    private fun simpleObserver() {
        val list = listOf("A", "B", "C")
        val observable = Observable.fromIterable(list)

        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

                Log.e("Project ","Subscribed")
            }

            override fun onError(e: Throwable) {
                Log.e("Project ","Error ${e.message}")
            }

            override fun onComplete() {
                Log.e("Project ","Compleated")
            }

            override fun onNext(t: String) {
// all the data update will come  in this function
                Log.e("Project ","On Next $t")
            }

        })
    }
}