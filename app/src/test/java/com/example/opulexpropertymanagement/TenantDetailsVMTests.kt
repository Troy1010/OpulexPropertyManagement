package com.example.opulexpropertymanagement

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.example.opulexpropertymanagement.di.AppModule
import com.example.opulexpropertymanagement.di.DaggerAppComponent
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.zzTestUtil.ContentTestExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExtendWith(ContentTestExtension::class)
class TenantDetailsVMTests {
    @Mock lateinit var repo : Repo
    @Mock lateinit var tenant : Tenant
    @Mock lateinit var sharedPrefs : SharedPreferences
    @Mock lateinit var appContext: App
    val appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    @Mock lateinit var uri: Uri
    val uriObj = Uri.parse("content://com.android.providers.media.documents/document/image%3A40")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(appContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(appContext.appComponent).thenReturn(appComponent)
    }

    @Test
    fun `test addDocument`() {
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
//        uriObj.parse("content://com.android.providers.media.documents/document/image%3A40")
        tenantDetailsVM.addDocument(uriObj, "A good document")
    }
}