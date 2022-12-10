package com.udacity.project4.locationreminders.savereminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentDataViewModel:ViewModel() {
    var location = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var des = MutableLiveData<String>()
    var lat = MutableLiveData<Double>()
    var long = MutableLiveData<Double>()
}