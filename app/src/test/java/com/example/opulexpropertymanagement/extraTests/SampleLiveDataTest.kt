package com.example.opulexpropertymanagement.extraTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SampleLiveDataTest {
    @get:Rule val liveDataRule = InstantTaskExecutorRule()

    @Test
    fun `my test`() {
        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.postValue("test")
        Assert.assertEquals("test", mutableLiveData.value)
    }
}