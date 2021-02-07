package com.katilijiwo.movieplot.ui.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroductionViewModel: ViewModel() {

    private val name : MutableLiveData<String> = MutableLiveData()
    fun setName(name : String){
        this.name.value = name
    }
    fun getName() : MutableLiveData<String> {
        return name
    }

    fun checkName(): Boolean {
        if(name.value!!.length != 6 )
            return false
        if(checkOnlyOneCapital() != 1)
            return false

        return true
    }

    private fun checkOnlyOneCapital(): Int {
        var capitalCounter = 0
        for (i in name.value!!.indices){
            if(name.value!![i].toString().matches(Regex(".*[A-Z].*")))
                capitalCounter++
        }
        return capitalCounter
    }

}