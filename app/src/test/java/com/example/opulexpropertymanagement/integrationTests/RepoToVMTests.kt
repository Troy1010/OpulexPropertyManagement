package com.example.opulexpropertymanagement.integrationTests

import com.example.opulexpropertymanagement.util.ContentTestExtension
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.IntStream
import java.util.stream.Stream


@ExperimentalCoroutinesApi
@ExtendWith(ContentTestExtension::class)
class RepoToVMTests(val testDispatcher: TestCoroutineDispatcher) {
//    companion object {
//        @JvmStatic
//        private fun qwer() = Stream.of(1,4,5,6,7,8,23)
//    }
    private fun qwer() = Stream.of(1,4,5,6,7,8,23)

    @DisplayName("Test for multiples of 5")
    @ParameterizedTest
//    @ValueSource(strings = arrayOf("Blue", "Hamburger"))
    @MethodSource("qwer")
    fun multiplesOf5(input: Int) {
//        val result: String = fizzBuzz.play(input)
//        Assertions.assertEquals("Buzz", result)
        logz("running test. input:$input")
    }

//    fun mySource(): IntStream? {
//        return IntStream.range(1,60)
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("mySource")
//    fun test1(test: Any) = testDispatcher.runBlockingTest {
//        logz("Hi")
//    }
}