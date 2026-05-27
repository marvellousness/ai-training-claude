package nova.publish.bazarbooks.feature.orders.model

data class OrderStatusUiModel(val orderId: String, val steps: List<String>, val currentStep: Int)
