package com.example.opulexpropertymanagement.extraTests

import com.example.opulexpropertymanagement.zzTestUtil.MyCoroutinesRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SampleCoroutineTests {
    @get:Rule val coroutineRule = MyCoroutinesRule(TestCoroutineDispatcher())

    @Test
    fun `my coroutine test`() = runBlockingTest {

        var x = "no hi"
        CoroutineScope(Dispatchers.Main).launch {
            x = "hi"
            println("I am doing some cool stuff in a coroutine")
        }
        Assert.assertEquals("hi", x)
    }
}