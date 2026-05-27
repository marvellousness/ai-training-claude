package nova.publish.bazarbooks.core.common.validation

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Invalid(val message: String) : ValidationResult
}
