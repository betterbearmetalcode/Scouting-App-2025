package pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import defaultBackground
import defaultOnBackground
import defaultOnPrimary
import defaultPrimaryVariant
import exportScoutData
import nodes.*
import setTeam
import java.lang.Integer.parseInt


@Composable
actual fun AutoTeleSelectorMenuTop(
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    selectPage: MutableState<String>,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>
) {
    setTeam(team, match, robotStartPosition.value)
    var pageName by remember { mutableStateOf("Auto") }
    var positionName by remember { mutableStateOf("") }


    when (robotStartPosition.value){
        0 -> {positionName = "R1"}
        1 -> {positionName = "R2"}
        2 -> {positionName = "R3"}
        3 -> {positionName = "B1"}
        4 -> {positionName = "B2"}
        5 -> {positionName = "B3"}
    }


    Column {
//        Divider(color = defaultPrimaryVariant, thickness = 4.dp)


        Row(Modifier.align(Alignment.CenterHorizontally).height(IntrinsicSize.Min)) {
            Text(
                text = positionName,
                modifier = Modifier.scale(1.2f).align(Alignment.CenterVertically).padding(horizontal = 25.dp),
                fontSize = 30.sp
            )

            Divider(
                color = defaultPrimaryVariant,
                modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(3.dp),
            )

            Text(
                text = "${team.value}",
                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 25.dp),
                fontSize = 33.sp,
                color = Color(red = 0.1f, green = Color.Cyan.green-0.4f, blue = Color.Cyan.blue-0.2f)
            )

            Divider(
                color = defaultPrimaryVariant,
                modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(3.dp),
            )

            Text(
                text = "Match",
                modifier = Modifier.align(Alignment.CenterVertically).padding(start = 25.dp),
                fontSize = 30.sp
            )

            TextField(
                value = match.value,
                onValueChange = {
                    value ->
                    if(match.value != "") {
                        teamDataArray[robotStartPosition.intValue]?.set(parseInt(match.value),
                            createOutput(team, robotStartPosition)
                        )
                        exportScoutData()
                    }
                    match.value = value.filter { it.isDigit() };
                    if(match.value != ""){
                        loadData(parseInt(value), team, robotStartPosition)
                    }

                },
                modifier = Modifier.fillMaxWidth(1f/4f),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = defaultBackground, textColor = defaultOnPrimary, cursorColor = defaultOnPrimary),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 30.sp)
            )
        }

        Divider(color = defaultPrimaryVariant, thickness = 3.dp)

    }
}

@Composable
actual fun AutoTeleSelectorMenuTop(
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    pageIndex: MutableIntState
) {
}

@Composable
actual fun AutoTeleSelectorMenuBottom(
    robotStartPosition: MutableIntState,
    team: MutableIntState,
    selectPage: MutableState<String>,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>,
    mainMenuDialog: MutableState<Boolean>
) {
}

@Composable
actual fun MatchMenuBottom(
    robotStartPosition: MutableIntState,
    team: MutableIntState,
    pageIndex: MutableIntState,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>,
    mainMenuDialog: MutableState<Boolean>
) {
}

@Composable
actual fun MatchMenuTop(
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
) {
}