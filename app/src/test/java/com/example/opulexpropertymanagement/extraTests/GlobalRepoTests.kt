package com.example.opulexpropertymanagement.extraTests

import com.example.opulexpropertymanagement.layers.z_ui.GlobalRepo
import com.example.opulexpropertymanagement.util.Display
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GlobalRepoTests {

    @Mock
    lateinit var globalRepo: GlobalRepo
    @Before
    fun doAMockitoPreparation() {
        globalRepo = Mockito.spy(GlobalRepo)
    }
    @Test
    fun testDisplayAsStaredEmail_correct() {
        Mockito
            .`when`(globalRepo.getUserEmail(Mockito.anyString()))
            .thenReturn("BobsEmail@gmail.com")
        val display = Display
        val staredEmail = display.asStaredEmail(globalRepo.getUserEmail("55"))
        Assert.assertEquals("****Email@gmail.com", staredEmail)
    }
    @Test
    fun testDisplayAsStaredEmail_incorrect() {
        Mockito
            .`when`(globalRepo.getUserEmail(Mockito.anyString()))
            .thenReturn("BobsEmail@gmail.com")
        val display = Display
        val staredEmail = display.asStaredEmail(globalRepo.getUserEmail("55"))
        Assert.assertNotEquals("****", staredEmail)
    }
}