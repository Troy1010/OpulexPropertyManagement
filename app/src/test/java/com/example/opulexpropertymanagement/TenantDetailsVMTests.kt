package com.example.opulexpropertymanagement

import android.content.Context
import android.content.SharedPreferences
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
    @Mock
    lateinit var repo: Repo
    @Mock
    lateinit var tenant: Tenant
//    @Mock
//    val uriObj = Uri.parse("content://com.android.providers.media.documents/document/image%3A40")
//    @Mock
//    lateinit var App: AppClass

    @Before
    fun setUp() {
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)


        MockitoAnnotations.initMocks(this)
        // initialize App Singleton
        //  instantiate AppClass and assign it to AppClass.Companion.instance
        //  *This setter is private, so we must use reflection
        val setInstanceMethod = App.Companion::class.java.getDeclaredMethod("setInstance", App::class.java)
        setInstanceMethod.isAccessible = true
        setInstanceMethod.invoke(App.Companion, App())
        //  instantiate component
//        App.appComponent = DaggerApplicationComponentZ.builder().applicationModuleZ(AppModule()).build()
    }

    @Test
    fun `test addDocument`() {
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
//        uriObj.parse("content://com.android.providers.media.documents/document/image%3A40")
//        tenantDetailsVM.addDocument(uriObj, "A good document")
    }
}