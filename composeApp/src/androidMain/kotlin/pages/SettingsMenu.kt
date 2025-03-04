package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import getCurrentTheme
import nodes.RootNode
import nodes.miniMinus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme
import themeDefault

@Composable
actual fun SettingsMenu(
    mainMenuBackStack: BackStack<RootNode.NavTarget>,
){
    var themeExpanded by remember { mutableStateOf(false) }
    var effectsChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.verticalScroll(ScrollState(0)).padding(20.dp)) {
        Box(modifier = Modifier.fillMaxWidth()){
            OutlinedButton(
                onClick = {
                    mainMenuBackStack.pop()
                },
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = getCurrentTheme().secondary,
                    contentColor = getCurrentTheme().onPrimary
                ),
                modifier = Modifier
                    .scale(0.75f)
                    .align(Alignment.CenterStart)
            ) {
                Text(text = "Back", color = getCurrentTheme().onPrimary)
            }
            Text(
                text = "Settings",
                fontSize = 50.sp,
                color = getCurrentTheme().onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)
            Text(
                text = "Current Rank:",
                fontSize = 35.sp,
                color = getCurrentTheme().onPrimary,
            )
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)

        Text(
            text = "TODO put ranked photo for the scout right here and an xp bar",
            fontSize = 18.sp,
            color = getCurrentTheme().onPrimary,
            modifier = Modifier,
        )

        Spacer(modifier = Modifier.padding(20.dp))
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)
    Text(
        text = "Appearance",
        fontSize = 35.sp,
        color = getCurrentTheme().onPrimary,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
    )
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)
    Row {
        DropdownMenu(
            expanded = themeExpanded,
            onDismissRequest = { themeExpanded = false },
        ){
        }

        OutlinedButton(
            onClick = {
                themeExpanded = true
            },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = getCurrentTheme().secondary,
                contentColor = getCurrentTheme().onPrimary
            ),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = "Theme",
                color = getCurrentTheme().onPrimary
            )
        }
    }

    Row {

        Switch(
            checked = effectsChecked,
            onCheckedChange = { effectsChecked = !effectsChecked },
            colors = SwitchDefaults.colors(
                checkedThumbColor = getCurrentTheme().primaryVariant,
                checkedTrackColor = getCurrentTheme().secondaryVariant,
                uncheckedThumbColor = getCurrentTheme().error,
                uncheckedTrackColor = getCurrentTheme().onPrimary,
            ),
            modifier = Modifier
                .scale(2f)
                .padding(25.dp)

        )
        Text(
            text = "Effects",
            fontSize = 25.sp,
            color = getCurrentTheme().onPrimary,
            modifier = Modifier
                .padding(28.dp)
        )
    }

    HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)

    Text(
        text = "Accessibility",
        fontSize = 35.sp,
        color = getCurrentTheme().onPrimary,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
    )
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)
        
        Text(
            text = "TODO Put algae and coral column move-ability to here",
            fontSize = 18.sp,
            color = getCurrentTheme().onPrimary,
            modifier = Modifier,
        )
        Row{
            Switch(
                checked = miniMinus.value,
                onCheckedChange = { miniMinus.value = !miniMinus.value },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = getCurrentTheme().primaryVariant,
                    checkedTrackColor = getCurrentTheme().secondaryVariant,
                    uncheckedThumbColor = getCurrentTheme().error,
                    uncheckedTrackColor = getCurrentTheme().onPrimary,
                ),
                modifier = Modifier
                    .scale(2f)
                    .padding(25.dp)

            )
            Text(
                text = "Mini-Minus Buttons",
                fontSize = 25.sp,
                color = getCurrentTheme().onPrimary,
                modifier = Modifier
                    .padding(28.dp)
            )
        }
        HorizontalDivider(thickness = 2.dp, color = getCurrentTheme().primaryVariant)
    OutlinedButton(
        onClick = {
            effectsChecked = false
            theme = themeDefault()
        },
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = getCurrentTheme().secondary,
            contentColor = getCurrentTheme().onPrimary
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
    ){
        Text(
            text = "Reset to Default",
            color = getCurrentTheme().onPrimary,
            fontSize = 35.sp,
        )
    }

    }
}
