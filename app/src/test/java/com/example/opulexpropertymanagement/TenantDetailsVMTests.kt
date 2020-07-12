package com.example.opulexpropertymanagement

import android.content.SharedPreferences
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.di.AppModule
import com.example.opulexpropertymanagement.di.DaggerAppComponent
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.models.Tenant
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

//@ExperimentalCoroutinesApi
//@ExtendWith(ContentTestExtension::class)
@RunWith(JUnit4::class)
class TenantDetailsVMTests {
    @get:Rule val rule = InstantTaskExecutorRule()

    @Mock lateinit var repo : Repo
    @Mock lateinit var tenant : Tenant
    @Mock lateinit var sharedPrefs : SharedPreferences
    @Mock lateinit var appContext: App
    val appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    val uriObj = Uri.parse("content://com.android.providers.media.documents/document/image%3A40")

//    @get:Rule public val rule: TestRule = InstantTaskExecutorRule()

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(appContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(appContext.appComponent).thenReturn(appComponent)

//        Dispatchers.setMain(Dispatchers.)
//        Dispatchers.setMain(this
//            .getStore(TEST_COROUTINE_DISPATCHER_NAMESPACE)
//            .get(TEST_COROUTINE_DISPATCHER_KEY, TestCoroutineDispatcher::class.java)!!)
    }

    @Test
    fun `my test`() {
        val mutableLiveData = MutableLiveData<String>()

        mutableLiveData.postValue("test")

        Assert.assertEquals("test", mutableLiveData.value)
    }

    @Test
    fun `test addDocument`() {
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
//        tenantDetailsVM.addDocument(uriObj, "A good document")
    }
}