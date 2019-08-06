package com.pg.mvvmexample.viewmodel

import AppWs
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.notimp.webservice.interfaces.WsListener
import com.pg.mvvmexample.model.UserBean

class ActivityViewModel : ViewModel(), WsListener {
     var userData = MutableLiveData<UserBean>()
    private var isLoading = MutableLiveData<Boolean>()

    fun getUserData(): LiveData<UserBean> {
        return userData
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun callWebservice(int: Int) {
        AppWs.login(int, this)
        isLoading.postValue(true)
    }

    override fun onResponseSuccess(baseResponse: Any) {
        val response = baseResponse as UserBean
        userData.postValue(response)
        isLoading.postValue(false)
    }

    override fun notifyResponseFailed(message: String?, request: Any?) {
        isLoading.postValue(false)
    }
}
