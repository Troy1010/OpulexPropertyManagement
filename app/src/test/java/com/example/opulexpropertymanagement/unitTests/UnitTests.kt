package com.example.opulexpropertymanagement.unitTests

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import ch.tutteli.atrium.api.fluent.en_GB.notToBe
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import com.example.opulexpropertymanagement.zzTestUtil.getOrAwaitValue
import com.example.opulexpropertymanagement.util.generateUniqueID
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UnitTests {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setupLiveData() {
            ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
                override fun postToMainThread(runnable: Runnable) = runnable.run()
                override fun isMainThread(): Boolean = true
            })
        }
        @AfterAll
        @JvmStatic
        fun clearLiveDataExecutor() {
            ArchTaskExecutor.getInstance().setDelegate(null)
        }
    }

    @Test
    fun `Two Unique Ids must not match`() {
        val id1 = generateUniqueID()
        val id2 = generateUniqueID()
        expect(id1).notToBe(id2)
    }

    @Test
    fun `Convert RX To LiveData`() {
//        val x = PublishSubject.create<Int>()
//        val liveData = convertRXToLiveData(x)
//        logq("liveData:$liveData")
//        x.onNext(34)
        val liveData = MutableLiveData<Int>()
        liveData.value = 34
        liveData.getOrAwaitValue().also { value ->
            expect(value).toBe(34)
        }
        liveData.value = 38
//        x.onNext(34)
    }
}