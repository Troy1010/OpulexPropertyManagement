package com.example.opulexpropertymanagement

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.zzTestUtil.MyCoroutinesRule
import com.example.opulexpropertymanagement.zzTestUtil.getOrAwaitValue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import junitparams.JUnitParamsRunner
import junitparams.NamedParameters
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit


@ExperimentalCoroutinesApi
@RunWith(JUnitParamsRunner::class)
open class TenantDetailsVMTests {
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRule = MyCoroutinesRule(TestCoroutineDispatcher())
    @get:Rule
    val initializeMocksRule = MockitoJUnit.rule()
    @Mock
    lateinit var repo: Repo
    val uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A40")

    @Test
    @Parameters(method = "testAddDocumentValues")
    fun testAddDocument(document:Document, tenant:Tenant) = coroutineRule.testDispatcher.runBlockingTest {
        // given
        Mockito.`when`(repo.addDocument(anyString(), any(), anyString()))
            .doReturn(AddDocumentResult.Success(document))
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
        // stimulate
        tenantDetailsVM.addDocument(uri, "A good document")
        val actual = tenantDetailsVM.addDocumentResult.getOrAwaitValue()
        // assert
        Assert.assertNotNull(actual)
        Assert.assertThat(actual, instanceOf(AddDocumentResult.Success::class.java))
        if (actual is AddDocumentResult.Success) {
            Assert.assertEquals(document, actual.document)
        }
    }
    @NamedParameters("grownups")
    open fun testAddDocumentValues(): Array<Any>? {
        val tenant = Tenant(
            "123456", "123", "778", "123 Fake St",
            "GoodCarrot@gmail.com", "111-222-3333", "Bob"
        )
        return arrayOf(
            arrayOf(Document("654", "795", "A good doc"), tenant),
            arrayOf(Document("341", "224", "Some other doc"), tenant)
        )
    }
}