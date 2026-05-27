package nova.publish.bazarbooks.core.common.validation

object EmailValidator {
    private val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validate(value: String): ValidationResult {
        val trimmed = value.trim()
        return when {
            trimmed.isEmpty() -> ValidationResult.Invalid("Email is required")
            !emailPattern.matches(trimmed) -> ValidationResult.Invalid("Enter a valid email")
            else -> ValidationResult.Valid
        }
    }
}
