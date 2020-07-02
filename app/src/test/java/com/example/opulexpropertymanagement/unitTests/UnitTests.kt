package com.example.opulexpropertymanagement.unitTests

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import com.example.opulexpropertymanagement.util.generateUniqueID
import org.junit.jupiter.api.Test

class UnitTests {
    @Test
    fun `Two Unique Ids must not match`() {
        val id1 = generateUniqueID()
        val id2 = generateUniqueID()
        expect(id1).notToBe(id2)
    }

    @Test
    fun `Convert RX To LiveData`() {
//        logz("ConvertRXToLiveData..")
//        val x = PublishSubject.create<Int>()
//        val liveData = convertRXToLiveData(x)
//        logz("liveData:$liveData")
//        x.onNext(34)
//        liveData.getOrAwaitValue().also { value ->
//            expect(value).toBe(34)
//        }
    }
}