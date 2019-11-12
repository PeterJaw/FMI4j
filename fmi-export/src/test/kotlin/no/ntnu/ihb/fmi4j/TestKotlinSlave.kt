package no.ntnu.ihb.fmi4j

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestKotlinSlave {

    @Test
    fun testKotlinSlave() {

        val md = KotlinTestingSlave().definekt().modelDescription
        Assertions.assertEquals("container.value", md.modelVariables.scalarVariable[0].name)
        Assertions.assertEquals("container.container2.value2", md.modelVariables.scalarVariable[1].name)

    }

}
