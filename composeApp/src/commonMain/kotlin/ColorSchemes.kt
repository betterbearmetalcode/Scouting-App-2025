import androidx.compose.ui.graphics.Color

//val defaultPrimary = Color(39, 42, 89)
//val defaultPrimaryVariant = Color(3, 23, 58)
//val defaultSecondary = Color(25, 26, 38)
//val defaultSecondaryVariant = Color(239, 234, 0)
//val defaultBackground = Color(45, 46, 64)
//val defaultSurface = Color(8, 47, 113)
//val defaultError = Color(191, 0, 26)
//val defaultOnPrimary = Color(242, 191, 94)
//val defaultOnSecondary = Color(115, 50, 50)
//val defaultOnBackground = Color(242, 191, 94)
//val defaultOnSurface = Color(149, 147, 0)
//val defaultOnError = Color(177, 173, 0)

val defaultPrimary = Color(6,9,13)
val defaultPrimaryVariant = Color.Yellow
val defaultSecondary = Color(15,31,47)
val defaultSecondaryVariant = Color.Yellow
val defaultBackground = Color(6,9,13)
val defaultBackgroundVariant = Color(32, 38, 44)
val defaultSurface = Color(6,9,13)
val defaultError = Color(255, 209, 220)
val defaultOnPrimary = Color.White
val defaultOnSecondary = Color.Yellow
val defaultOnBackground = Color.Yellow
val defaultOnSurface = Color(15,15,15)
val defaultOnError = Color(6,9,13)

val defaultGamePiece1 = Color(50, 45, 35)
val defaultGamePiece2 = Color(5, 0, 48)


operator fun Color.minus(color: Color): Color {
    return Color(this.red - color.red,this.green - color.green,this.blue - color.blue)
}
operator fun Color.plus(color: Color): Color {
    return Color(this.red + color.red,this.green + color.green,this.blue + color.blue)
}

val redAlliance = Color(60, 30, 30)
val blueAlliance = Color(30, 30, 60)