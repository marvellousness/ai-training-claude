package nova.publish.bazarbooks.core.common.validation

import org.junit.Assert.assertEquals
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun `blank email returns required error`() {
        assertEquals(ValidationResult.Invalid("Email is required"), EmailValidator.validate(""))
    }

    @Test
    fun `invalid email format returns format error`() {
        assertEquals(ValidationResult.Invalid("Enter a valid email"), EmailValidator.validate("reader"))
    }

    @Test
    fun `valid email returns valid result`() {
        assertEquals(ValidationResult.Valid, EmailValidator.validate("reader@example.com"))
    }
}
