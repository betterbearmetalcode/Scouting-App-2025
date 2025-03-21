package org.tahomarobotics.scouting


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import defaultBackground
import defaultGamePiece1
import defaultGamePiece2
import defaultOnBackground
import defaultOnPrimary
import defaultOnSecondary
import defaultOnSurface
import defaultPrimary
import defaultSecondary
import defaultSurface
import nodes.RootNode

@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        
        setContent {
            MaterialTheme(
                colorScheme = defaultScheme
            ) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    NodeHost(
                        lifecycle = AndroidLifecycle(androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycle),
                        integrationPoint = appyxV2IntegrationPoint,
                    ) {
                        RootNode(
                            buildContext = it,
                        )
                    }
                }
            }
        }
    }
}

val defaultScheme = darkColorScheme(
    primary = defaultPrimary,
    onPrimary = defaultOnPrimary,
    primaryContainer = defaultPrimary,
    onPrimaryContainer = defaultOnPrimary,
    inversePrimary = defaultOnPrimary,
    secondary = defaultSecondary,
    onSecondary = defaultOnSecondary,
    secondaryContainer = defaultSecondary,
    onSecondaryContainer = defaultOnSecondary,
    tertiary = Color(15,15,15),
    onTertiary = Color(0xfff4f3ef),
    tertiaryContainer = Color(15,15,15),
    onTertiaryContainer = Color(0xfff4f3ef),
    background = defaultBackground,
    onBackground = defaultOnBackground,
    surface = defaultSurface,
    onSurface = defaultOnSurface,
    outline = defaultSecondary,
)
var algaeColor = Color(0, 131, 52)
var coralColor = Color(60, 0, 255)


