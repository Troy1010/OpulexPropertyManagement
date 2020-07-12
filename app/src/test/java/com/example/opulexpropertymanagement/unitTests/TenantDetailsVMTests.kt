package com.example.opulexpropertymanagement.unitTests

import com.example.opulexpropertymanagement.App
import com.example.opulexpropertymanagement.AppClass
import com.example.opulexpropertymanagement.di.ApplicationModuleZ
import com.example.opulexpropertymanagement.di.DaggerApplicationComponentZ
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.zzTestUtil.ContentTestExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
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
        MockitoAnnotations.initMocks(this)
        // initialize App Singleton
        //  instantiate AppClass and assign it to AppClass.Companion.instance
        //  *This setter is private, so we must use reflection
        val setInstanceMethod = AppClass.Companion::class.java.getDeclaredMethod("setInstance", AppClass::class.java)
        setInstanceMethod.isAccessible = true
        setInstanceMethod.invoke(AppClass.Companion, AppClass())
        //  instantiate component
        App.appComponent = DaggerApplicationComponentZ.builder().applicationModuleZ(ApplicationModuleZ()).build()
    }

    @Test
    fun `test addDocument`() {
        val tenantDetailsVM = TenantDetailsVM(tenant, repo)
//        uriObj.parse("content://com.android.providers.media.documents/document/image%3A40")
//        tenantDetailsVM.addDocument(uriObj, "A good document")
    }
}