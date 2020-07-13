package com.example.opulexpropertymanagement

import android.content.SharedPreferences
import com.example.opulexpropertymanagement.layers.data_layer.SharedPrefWrapper
import com.example.opulexpropertymanagement.layers.z_ui.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit


@ExperimentalCoroutinesApi
class SharedPrefWrapperTests {
    @get:Rule val mockitoRule = MockitoJUnit.rule()
    @Mock lateinit var sharedPrefs: SharedPreferences
    @Mock lateinit var editor: SharedPreferences.Editor


    @Before
    fun setUp() {
        `when`(sharedPrefs.edit()).thenReturn(editor)
    }

    @Test
    fun `read user`() = runBlockingTest {
        // given
        val sharedPrefWrapper = SharedPrefWrapper(sharedPrefs)
        `when`(sharedPrefs.getString(any(), anyOrNull())).doReturn("AString")

        // stimulate
        val actual = sharedPrefWrapper.readUser()

        // check result
        Assert.assertTrue(actual is User)
    }

    @Test
    fun `read null user`() = runBlockingTest {
        // given
        val sharedPrefWrapper = SharedPrefWrapper(sharedPrefs)
        `when`(sharedPrefs.getString(any(), anyOrNull())).doReturn(null)

        // stimulate
        val actual = sharedPrefWrapper.readUser()

        // check result
        Assert.assertEquals(null, actual)
    }
}