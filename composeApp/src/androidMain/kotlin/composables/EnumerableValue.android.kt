package composables

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.interactions.core.ui.property.impl.BackgroundColor
import compKey
import defaultOnPrimary
import defaultSecondary
import getCurrentTheme
import minus
import nodes.betterParseInt
import nodes.createOutput
import nodes.effects
import nodes.jsonObject
import nodes.match
import nodes.redoList
import nodes.undoList
import nodes.saveData
import nodes.startTimer
import nodes.teamDataArray

@Composable
actual fun EnumerableValue(label: String, value: MutableIntState, alignment: Alignment, flashColor: Color, backgroundColor: Color, miniMinus : Boolean, modifier: Modifier) {
    val interact = remember { MutableInteractionSource() }

    val pressed by interact.collectIsPressedAsState()

    OutlinedButton(
        border = BorderStroke(2.dp, color = getCurrentTheme().primaryVariant),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = if(pressed && effects.value) flashColor else backgroundColor),
        onClick = {
            undoList.push(arrayOf("number" ,value, value.value))
            value.value += 1
            redoList.push(arrayOf("number" ,value, value.value))
            saveData.value = true
            teamDataArray.get(compKey)?.get(match.value.betterParseInt())?.set(jsonObject.get("robotStartPosition").asInt, createOutput(mutableIntStateOf(jsonObject.get("team").asInt), mutableIntStateOf(
                jsonObject.get("robotStartPosition").asInt)))

            startTimer.value = true
        },
        interactionSource = interact,
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "$label \n\n ${value.value}",
                fontSize = 24.sp,
                color = getCurrentTheme().onPrimary,
                modifier = Modifier.align(Alignment.CenterStart).padding(5.dp)
            )
            if (miniMinus) {
                OutlinedButton(
                    border = BorderStroke(1.dp, color = getCurrentTheme().primaryVariant),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (pressed && effects.value) flashColor else (backgroundColor - Color(20,20,20))),
                    onClick = {
                        if(value.value != 0){
                            undoList.push(arrayOf("number", value, value.value))
                            value.value -= 1
                            redoList.push(arrayOf("number", value, value.value))
                        }
                        saveData.value = true
                        teamDataArray.get(compKey)?.get(match.value.betterParseInt())?.set(jsonObject.get("robotStartPosition").asInt, createOutput(mutableIntStateOf(jsonObject.get("team").asInt), mutableIntStateOf(
                            jsonObject.get("robotStartPosition").asInt)))

                        startTimer.value = true
                    },
                    interactionSource = interact,
                    modifier = Modifier.align(alignment).padding(0.dp).offset(0.dp,2.dp)
                ) {
                    Text(
                        text = "-",
                        fontSize = 18.sp,
                        color = getCurrentTheme().onPrimary,
                    )
                }
            }
        }
    }
}



//
//@Composable
//actual fun EnumerableValue(label: String, value: MutableIntState, flashColor: Color, alignment: Alignment, modifier: Modifier) {
//
//    val interact = remember { MutableInteractionSource() }
//
//    val pressed by interact.collectIsPressedAsState()
//
//    OutlinedButton(
//        border = BorderStroke(2.dp, color = getCurrentTheme().primaryVariant),
//        shape = RoundedCornerShape(0.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = if(pressed) flashColor else Color.Black),
//        onClick = {
//            value.value += 1
//            saveData.value = true
//        },
//        interactionSource = interact,
//        modifier = modifier
//    ) {
//        Box(modifier = Modifier.fillMaxSize()){
//            Text(
//                text = label,
//                fontSize = 18.sp,
//                color = getCurrentTheme().onPrimary,
//                modifier = Modifier.align(Alignment.CenterStart)
//            )
//            Text(
//                text = value.value.toString(),
//                fontSize = 14.sp,
//                color = getCurrentTheme().onPrimary,
//                modifier = Modifier.align(alignment)
//            )
//        }
//    }
//}
//
//
