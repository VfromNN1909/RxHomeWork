package com.school.rxhomework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.school.rxhomework.model.api.Repository
import com.school.rxhomework.util.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class ActivityViewModel : ViewModel() {

    private val _subject = PublishSubject.create<Unit>()
    val subject: Observer<Unit> = _subject

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State>
        get() = _state

    init {
        _subject
                .switchMap {
                    Repository.getPosts().toObservable().onErrorReturn {
                        Response.error(
                                404,
                                ResponseBody.create(MediaType.get("text"), "Error")
                        )
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isSuccessful)
                                it.body()?.let { data ->
                                    _state.value = State.Loaded(data)
                                }
                            else
                                _state.value = State.Loaded(listOf())
                        },
                        { _state.value = State.Loaded(listOf()) },
                )

    }
}
