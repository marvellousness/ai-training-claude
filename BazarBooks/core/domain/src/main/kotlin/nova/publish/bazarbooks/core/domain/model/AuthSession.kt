package nova.publish.bazarbooks.core.domain.model

enum class AuthSession {
    Unauthenticated,
    Guest,
    Authenticated,
    Expired,
}
