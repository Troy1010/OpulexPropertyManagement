package com.example.opulexpropertymanagement.unitTests

import com.example.opulexpropertymanagement.layers.repo.MaintenancesRepo
import com.example.opulexpropertymanagement.zzTestUtil.ContentTestExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(ContentTestExtension::class)
class TestSomething {


    @Test
    fun `test a repo`() {
        val repo = MaintenancesRepo()
        repo.addMaintenance()
    }
}