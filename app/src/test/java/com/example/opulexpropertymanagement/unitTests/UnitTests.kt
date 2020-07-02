package com.example.opulexpropertymanagement.unitTests

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test


class UnitTests {

    @Test
    fun `unit Test Z`() {
        expect(8).notToBe(10)
    }
}