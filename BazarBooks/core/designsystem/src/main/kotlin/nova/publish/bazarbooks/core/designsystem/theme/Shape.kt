package nova.publish.bazarbooks.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val BazarShapes = Shapes(
    small = RoundedCornerShape(BazarDimensions.CardRadius),
    medium = RoundedCornerShape(BazarDimensions.ButtonRadius),
    large = RoundedCornerShape(BazarDimensions.SheetCornerRadius),
)
