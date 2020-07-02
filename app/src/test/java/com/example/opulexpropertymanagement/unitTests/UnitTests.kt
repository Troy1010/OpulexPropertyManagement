package com.example.opulexpropertymanagement.unitTests

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import com.example.opulexpropertymanagement.util.DoTheOtherOtherThing
import com.example.opulexpropertymanagement.util.DoTheOtherThing
import com.example.opulexpropertymanagement.util.Util
import org.junit.jupiter.api.Test



class UnitTests {

    @Test
    fun `unit Test Z`() {
        expect(8).notToBe(10)
    }

    @Test
    fun `Calling Util function`() {
        expect(Util().Hello()).toBe("Hi there")
    }

    @Test
    fun `Hunt Boar`() {
        expect(Util.HuntBoar()).toBe("Got it")
    }

    @Test
    fun `Do The Other Thing`() {
        expect(DoTheOtherThing()).toBe("Pineapple collection complete")
    }

    @Test
    fun `Do The Other Other Thing`() {
        expect(DoTheOtherOtherThing()).toBe("Eggs hidden")
    }
}