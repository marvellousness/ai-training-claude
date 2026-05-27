package nova.publish.bazarbooks.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

data class BazarColorSpec(
    val name: String,
    val color: Color,
    val hex: String,
)

data class BazarTypographySpec(
    val name: String,
    val figmaStyle: String,
    val fontFamily: String,
    val size: TextUnit,
    val lineHeight: TextUnit,
    val weight: String,
    val letterSpacing: String,
)

data class BazarDimensionSpec(
    val name: String,
    val value: Dp,
)

object BazarDesignSpec {
    const val FigmaFile = "Bazar Books"
    const val FigmaStyleguidePage = "1:2"
    const val FigmaDesignPage = "0:1"

    val colors = listOf(
        BazarColorSpec("primary50", BazarPalette.Primary50, "#FAF9FD"),
        BazarColorSpec("primary100", BazarPalette.Primary100, "#E5DEF8"),
        BazarColorSpec("primary200", BazarPalette.Primary200, "#CABCEF"),
        BazarColorSpec("primary300", BazarPalette.Primary300, "#A28CE0"),
        BazarColorSpec("primary400", BazarPalette.Primary400, "#7D64C3"),
        BazarColorSpec("primary500", BazarPalette.Primary500, "#54408C"),
        BazarColorSpec("primary600", BazarPalette.Primary600, "#352368"),
        BazarColorSpec("primary700", BazarPalette.Primary700, "#251554"),
        BazarColorSpec("primary800", BazarPalette.Primary800, "#10052F"),
        BazarColorSpec("primary900", BazarPalette.Primary900, "#09031B"),
        BazarColorSpec("gray50", BazarPalette.Gray50, "#FAFAFA"),
        BazarColorSpec("gray100", BazarPalette.Gray100, "#F5F5F5"),
        BazarColorSpec("gray200", BazarPalette.Gray200, "#E8E8E8"),
        BazarColorSpec("gray300", BazarPalette.Gray300, "#D6D6D6"),
        BazarColorSpec("gray400", BazarPalette.Gray400, "#B8B8B8"),
        BazarColorSpec("gray500", BazarPalette.Gray500, "#A6A6A6"),
        BazarColorSpec("gray600", BazarPalette.Gray600, "#7A7A7A"),
        BazarColorSpec("gray700", BazarPalette.Gray700, "#454545"),
        BazarColorSpec("gray800", BazarPalette.Gray800, "#292929"),
        BazarColorSpec("gray900", BazarPalette.Gray900, "#121212"),
        BazarColorSpec("white", BazarPalette.White, "#FFFFFF"),
        BazarColorSpec("blue", BazarPalette.Blue, "#3784FB"),
        BazarColorSpec("green", BazarPalette.Green, "#34A853"),
        BazarColorSpec("orange", BazarPalette.Orange, "#FF8C39"),
        BazarColorSpec("red", BazarPalette.Red, "#EF5A56"),
        BazarColorSpec("yellow", BazarPalette.Yellow, "#F5BE00"),
    )

    val typography = listOf(
        BazarTypographySpec("heading1", "Heading/H1", "Open Sans", BazarTextStyles.Heading1.fontSize, BazarTextStyles.Heading1.lineHeight, "Bold", "0px"),
        BazarTypographySpec("heading2", "Heading/H2", "Open Sans", BazarTextStyles.Heading2.fontSize, BazarTextStyles.Heading2.lineHeight, "Bold", "0px"),
        BazarTypographySpec("heading3", "Heading/H3", "Open Sans", BazarTextStyles.Heading3.fontSize, BazarTextStyles.Heading3.lineHeight, "Bold", "-3%"),
        BazarTypographySpec("heading4", "Heading/H4", "Open Sans", BazarTextStyles.Heading4.fontSize, BazarTextStyles.Heading4.lineHeight, "Bold", "-3%"),
        BazarTypographySpec("heading5", "Heading/H5", "Open Sans", BazarTextStyles.Heading5.fontSize, BazarTextStyles.Heading5.lineHeight, "Bold", "-3%"),
        BazarTypographySpec("heading6", "Heading/H6", "Open Sans", BazarTextStyles.Heading6.fontSize, BazarTextStyles.Heading6.lineHeight, "Bold", "0.3px"),
        BazarTypographySpec("body18Medium", "Body/18/Medium", "Roboto", BazarTextStyles.Body18Medium.fontSize, BazarTextStyles.Body18Medium.lineHeight, "Medium", "0.3px"),
        BazarTypographySpec("body16Semibold", "Body/16/Semibold", "Roboto", BazarTextStyles.Body16Semibold.fontSize, BazarTextStyles.Body16Semibold.lineHeight, "SemiBold", "0px"),
        BazarTypographySpec("body16Medium", "Body/16/Medium", "Roboto", BazarTextStyles.Body16Medium.fontSize, BazarTextStyles.Body16Medium.lineHeight, "Medium", "0px"),
        BazarTypographySpec("body16Regular", "Body/16/Regular", "Roboto", BazarTextStyles.Body16Regular.fontSize, BazarTextStyles.Body16Regular.lineHeight, "Regular", "0px"),
        BazarTypographySpec("body14Bold", "Body/14/Bold", "Roboto", BazarTextStyles.Body14Bold.fontSize, BazarTextStyles.Body14Bold.lineHeight, "Bold", "0.3px"),
        BazarTypographySpec("body14Semibold", "Body/14/Semibold", "Roboto", BazarTextStyles.Body14Semibold.fontSize, BazarTextStyles.Body14Semibold.lineHeight, "SemiBold", "0px"),
        BazarTypographySpec("body14Medium", "Body/14/Medium", "Roboto", BazarTextStyles.Body14Medium.fontSize, BazarTextStyles.Body14Medium.lineHeight, "Medium", "0px"),
        BazarTypographySpec("body14Regular", "Body/14/Regular", "Roboto", BazarTextStyles.Body14Regular.fontSize, BazarTextStyles.Body14Regular.lineHeight, "Regular", "0px"),
        BazarTypographySpec("body12Bold", "Body/12/Bold", "Roboto", BazarTextStyles.Body12Bold.fontSize, BazarTextStyles.Body12Bold.lineHeight, "Bold", "0.3px"),
        BazarTypographySpec("body12Semibold", "Body/12/Semibold", "Roboto", BazarTextStyles.Body12Semibold.fontSize, BazarTextStyles.Body12Semibold.lineHeight, "SemiBold", "0px"),
        BazarTypographySpec("body12Medium", "Body/12/Medium", "Roboto", BazarTextStyles.Body12Medium.fontSize, BazarTextStyles.Body12Medium.lineHeight, "Medium", "0px"),
        BazarTypographySpec("body12Regular", "Body/12/Regular", "Roboto", BazarTextStyles.Body12Regular.fontSize, BazarTextStyles.Body12Regular.lineHeight, "Regular", "0px"),
    )

    val dimensions = listOf(
        BazarDimensionSpec("mobileScreenWidth", BazarDimensions.MobileScreenWidth),
        BazarDimensionSpec("mobileScreenHeight", BazarDimensions.MobileScreenHeight),
        BazarDimensionSpec("horizontalPadding", BazarDimensions.HorizontalPadding),
        BazarDimensionSpec("contentPadding", BazarDimensions.ContentPadding),
        BazarDimensionSpec("buttonHeight", BazarDimensions.ButtonHeight),
        BazarDimensionSpec("buttonRadius", BazarDimensions.ButtonRadius),
        BazarDimensionSpec("fieldHeight", BazarDimensions.FieldHeight),
        BazarDimensionSpec("fieldRadius", BazarDimensions.FieldRadius),
        BazarDimensionSpec("cardRadius", BazarDimensions.CardRadius),
        BazarDimensionSpec("bottomNavHeight", BazarDimensions.BottomNavHeight),
        BazarDimensionSpec("bookCoverWidth", BazarDimensions.BookCoverWidth),
        BazarDimensionSpec("bookCoverHeight", BazarDimensions.BookCoverHeight),
    )

    val sourceFrames = listOf(
        BazarDimensionSpec("designScreenWidth", BazarDimensions.MobileScreenWidth),
        BazarDimensionSpec("designScreenHeight", BazarDimensions.MobileScreenHeight),
        BazarDimensionSpec("styleguideFrameWidth", 1440.dp),
        BazarDimensionSpec("colorStyleguideFrameHeight", 1299.dp),
        BazarDimensionSpec("typographyStyleguideFrameHeight", 4565.dp),
        BazarDimensionSpec("styleguideHeaderHeight", 186.dp),
        BazarDimensionSpec("colorSwatchWidth", 172.dp),
        BazarDimensionSpec("colorSwatchHeight", 88.dp),
    )
}
