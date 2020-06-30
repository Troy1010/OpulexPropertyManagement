package com.example.opulexpropertymanagement

import com.example.opulexpropertymanagement.aa.repo.RegisterRepo
import com.example.opulexpropertymanagement.aa.repo.SharedPref
import com.example.opulexpropertymanagement.aa.repo.UserRepo
import com.example.opulexpropertymanagement.aa.ui.GlobalRepo
import com.example.opulexpropertymanagement.util.Display
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepoTests {

    @Mock
    lateinit var userRepo: UserRepo
    lateinit var globalRepo: GlobalRepo
    @Before
    fun doAMockitoPreparation() {
        userRepo = Mockito.spy(UserRepo())
        globalRepo = Mockito.spy(GlobalRepo)
    }
    @Test
    fun testDisplayAsStaredEmail_correct() {
        Mockito
            .`when`(userRepo.getUserEmail(Mockito.anyString()))
            .thenReturn("BobsEmail@gmail.com")
        val display = Display
        val staredEmail = display.asStaredEmail(userRepo.getUserEmail("55"))
        Assert.assertEquals("****Email@gmail.com", staredEmail)
    }
    @Test
    fun testDisplayAsStaredEmail_incorrect() {
        Mockito
            .`when`(userRepo.getUserEmail(Mockito.anyString()))
            .thenReturn("BobsEmail@gmail.com")
        val display = Display
        val staredEmail = display.asStaredEmail(userRepo.getUserEmail("55"))
        Assert.assertNotEquals("****", staredEmail)
    }
}