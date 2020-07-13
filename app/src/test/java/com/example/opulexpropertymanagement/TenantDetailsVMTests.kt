package com.example.opulexpropertymanagement

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.zzTestUtil.MyCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TenantDetailsVMTests {
    @get:Rule val liveDataRule = InstantTaskExecutorRule()
    @get:Rule val coroutineRule = MyCoroutinesRule(TestCoroutineDispatcher())
    @get:Rule val initializeMocksRule = MockitoJUnit.rule()
    @Mock lateinit var repo : Repo
    @Mock lateinit var tenant : Tenant
    val uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A40")

    @Test
    fun `test addDocument`() {
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
        tenantDetailsVM.addDocument(uri, "A good document")
    }
}