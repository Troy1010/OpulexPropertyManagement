package com.example.opulexpropertymanagement.extraTests

//import androidx.lifecycle.MutableLiveData
//import ch.tutteli.atrium.api.fluent.en_GB.notToBe
//import ch.tutteli.atrium.api.fluent.en_GB.toBe
//import ch.tutteli.atrium.api.verbs.expect
//import com.example.opulexpropertymanagement.zzTestUtil.TL
//import com.example.opulexpropertymanagement.zzTestUtil.BaseTestClass
//import com.example.opulexpropertymanagement.zzTestUtil.getOrAwaitValue
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//
//@ExtendWith(BaseTestClass::class)
//class SampleTests {
//    @Test
//    fun `LiveData simple test`() {
//        TL.logz("starting LiveData simple test")
//        val liveData = MutableLiveData<Int>()
//        liveData.value = 22
//        liveData.value = 34
//        liveData.getOrAwaitValue().also { value ->
//            expect(value) {
//                toBe(34)
//                notToBe(22)
//                notToBe(38)
//            }
//        }
//        liveData.value = 38
//    }
//}