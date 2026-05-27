package nova.publish.bazarbooks.core.common.validation

object PasswordValidator {
    fun validate(value: String): ValidationResult = when {
        value.isBlank() -> ValidationResult.Invalid("Password is required")
        value.length < 8 -> ValidationResult.Invalid("Password must be at least 8 characters")
        else -> ValidationResult.Valid
    }
}
