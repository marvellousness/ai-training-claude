package nova.publish.bazarbooks.core.common.validation

import org.junit.Assert.assertEquals
import org.junit.Test

class PasswordValidatorTest {
    @Test
    fun `blank password returns required error`() {
        assertEquals(ValidationResult.Invalid("Password is required"), PasswordValidator.validate(""))
    }

    @Test
    fun `short password returns length error`() {
        assertEquals(ValidationResult.Invalid("Password must be at least 8 characters"), PasswordValidator.validate("short"))
    }

    @Test
    fun `valid password returns valid result`() {
        assertEquals(ValidationResult.Valid, PasswordValidator.validate("bookpass1"))
    }
}
