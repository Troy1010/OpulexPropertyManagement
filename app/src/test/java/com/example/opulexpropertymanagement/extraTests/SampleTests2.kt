package com.example.opulexpropertymanagement.extraTests

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import ch.tutteli.atrium.api.fluent.en_GB.notToBe
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import com.example.opulexpropertymanagement.zzTestUtil.TL
import com.example.opulexpropertymanagement.zzTestUtil.getOrAwaitValue
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


class SampleTests2 {
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
    fun `LiveData simple test`() {
        TL.logz("starting LiveData simple test")
        val liveData = MutableLiveData<Int>()
        liveData.value = 22
        liveData.value = 34
        liveData.getOrAwaitValue().also { value ->
            expect(value) {
                toBe(34)
                notToBe(22)
                notToBe(38)
            }
        }
        liveData.value = 38
    }
}