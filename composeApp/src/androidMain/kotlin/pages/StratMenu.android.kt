package pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.core.view.ViewCompat
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import defaultOnPrimary
import defaultSecondary
import getCurrentTheme
import nodes.*
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.lang.Integer.parseInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun StratMenu(
    backStack: BackStack<RootNode.NavTarget>,
    scoutName: MutableState<String>,
    comp: MutableState<String>,
    teams: List<Team>,
    isRedAlliance: Boolean
) {
    var mutableMatchNum by remember { mutableStateOf(stratMatch) }

    var first by remember { mutableStateOf(true) }

    if(first) {
        tempStratMatch = stratMatch
        println("ran! tempStratMatch = $tempStratMatch")

        saveStratData.value = false

        first = false
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(100.dp)
                    .padding(0.dp)
            ) {
                Row(Modifier.fillMaxWidth()) {
//                    Text(
//                        text = "Human Net",
//                        fontSize = 12.sp,
//                        color = Color.White,
//                        modifier = Modifier
//                            .rotate(-90f)
//                            .align(Alignment.CenterVertically)
//                    )
                    Column(modifier = Modifier.padding(0.dp)) {
                        OutlinedButton(
                            modifier = Modifier
                                .width(150.dp)
                                .fillMaxHeight(0.5f),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = defaultSecondary,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = { humanNetScored.value += 1; saveStratData.value = true }
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Scored",
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                                Text(
                                    text = humanNetScored.value.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .width(150.dp)
                                .fillMaxHeight(),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = defaultSecondary,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = { humanNetMissed.value += 1; saveStratData.value = true }
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Missed",
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                                Text(
                                    text = humanNetMissed.value.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(0.dp)) {
                        OutlinedButton(
                            modifier = Modifier
                                .width(50.dp)
                                .fillMaxHeight(0.5f),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = defaultSecondary,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = {
                                humanNetScored.value -= 1
                                if (humanNetScored.value < 0) humanNetScored.value = 0
                                saveStratData.value = true
                            }
                        ) {
                            Text(
                                text = "-",
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .width(50.dp)
                                .fillMaxHeight(),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = defaultSecondary,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = {
                                humanNetMissed.value -= 1
                                if (humanNetMissed.value < 0) humanNetMissed.value = 0
                                saveStratData.value = true
                            }
                        ) {
                            Text(
                                text = "-",
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        modifier = Modifier
                            .width(150.dp)
                            .fillMaxHeight(1f),
                        border = BorderStroke(2.dp, color = Color.Yellow),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = defaultSecondary,
                            contentColor = defaultOnPrimary
                        ),
                        onClick = { }
                    ) {
                        Column(modifier = Modifier.padding(0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Current Match",
                                fontSize = 12.sp
                            )
                            androidx.compose.material.TextField(
                                value = if (mutableMatchNum.toString() == "0") "" else mutableMatchNum.toString(),
                                onValueChange = {
                                    if(saveStratData.value) {
                                        stratTeamDataArray[TeamsAllianceKey(tempStratMatch, isRedAlliance)] = createStratOutput(tempStratMatch)
                                    }

                                    val newMatchNum = it.betterParseInt()
                                    mutableMatchNum = newMatchNum
                                    updateMatchNum(newMatchNum)
                                    loadStratData(newMatchNum, isRedAlliance)
                                    saveStratData.value = false

                                    tempStratMatch = stratMatch
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    backgroundColor = defaultSecondary,
                                    focusedIndicatorColor = Color.Yellow,
                                )
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(0.dp), horizontalAlignment = Alignment.End) {
                        OutlinedButton(
                            modifier = Modifier
                                .width(125.dp)
                                .fillMaxHeight(0.5f),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isRedAlliance) Color.Red else Color.Blue,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = { }
                        ) {
                            Text(
                                text = "${if (isRedAlliance) "Red" else "Blue"} Alliance",
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .width(125.dp)
                                .fillMaxHeight(),
                            border = BorderStroke(2.dp, color = Color.Yellow),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = defaultSecondary,
                                contentColor = defaultOnPrimary
                            ),
                            onClick = {
                                saveStratDataSit.value = true
                                stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] = createStratOutput(
                                    stratMatch); backStack.pop()
                            }
                        ) {
                            Text(
                                text = "Main",
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            teamList(
                label = "Team Strategy",
                teams = strategyOrder,
                onMove = { from, to ->
                    strategyOrder.apply {
                        add(to, removeAt(from))
                    }
                }
            )
            teamList(
                label = "Driving Skill",
                teams = drivingSkillOrder,
                onMove = { from, to ->
                    drivingSkillOrder.apply {
                        add(to, removeAt(from))
                    }
                }
            )
            teamList(
                label = "Mechanical Soundness",
                teams = mechanicalSoundnessOrder,
                onMove = { from, to ->
                    mechanicalSoundnessOrder.apply {
                        add(to, removeAt(from))
                    }
                }
            )
            OutlinedButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = defaultSecondary,
                    contentColor = defaultOnPrimary
                ),
                onClick = {
                    saveStratDataSit.value = false

                    saveStratDataPopup.value = true
                }
            ) {
                Text(
                    text = "Next Match",
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }

    if(saveStratDataPopup.value) {
        BasicAlertDialog(
            onDismissRequest = { saveStratDataPopup.value = false },
            modifier = Modifier
                .clip(
                    RoundedCornerShape(5.dp)
                )
                .border(
                    BorderStroke(3.dp, getCurrentTheme().primaryVariant),
                    RoundedCornerShape(5.dp)
                )
                .background(getCurrentTheme().secondary)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(8f / 10f)
                .padding(5.dp)
                .fillMaxHeight(1 / 8f)) {
                Text(text = "Do you want to save this data?",
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.TopCenter)
                )
                androidx.compose.material.OutlinedButton(
                    onClick = {
                        if(saveStratDataSit.value) {
                            stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] = createStratOutput(
                                stratMatch)
                            backStack.pop()
                        } else {
                            stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] = createStratOutput(
                                stratMatch)
                            nextMatch()
                            mutableMatchNum = stratMatch
                            loadStratData(stratMatch, isRedAlliance)
                        }

                        saveStratDataPopup.value = false
                    },
                    border = BorderStroke(2.dp, getCurrentTheme().secondaryVariant),
                    colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(
                        backgroundColor = getCurrentTheme().secondary,
                        contentColor = getCurrentTheme().onSecondary
                    ),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Text(text = "Yes", color = getCurrentTheme().error)
                }
                androidx.compose.material.OutlinedButton(
                    onClick = { saveStratDataPopup.value = false },
                    border = BorderStroke(2.dp, getCurrentTheme().secondaryVariant),
                    colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(
                        backgroundColor = getCurrentTheme().secondary,
                        contentColor = getCurrentTheme().onSecondary
                    ),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(text = "No", color = getCurrentTheme().error)
                }
            }
        }
    }

//    if(stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] != null) {
//        stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] = createStratOutput(stratMatch)
//        loadStratData(stratMatch, isRedAlliance)
////        if(first) {
////            loadData(parseInt(match.value), team, robotStartPosition)
////            first = false
////        } else {
////            teamDataArray[TeamMatchKey(parseInt(match.value), team.intValue, robotStartPosition.intValue)] = createOutput(team, robotStartPosition)
//////            loadData(parseInt(match.value), team, robotStartPosition)
////        }
//    } else {
//        if(saveStratData.value) {
//            stratTeamDataArray[TeamsAllianceKey(stratMatch, isRedAlliance)] = createStratOutput(stratMatch)
//        }
//    }

}

@Composable
fun teamList(
    label: String,
    teams: List<Team>,
    onMove: (from: Int, to: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, start = 12.dp)
                .align(Alignment.Start)
        )
        val view = LocalView.current
        val lazyListState = rememberLazyListState()
        val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
            onMove(from.index, to.index)
            ViewCompat.performHapticFeedback(
                view,
                HapticFeedbackConstantsCompat.SEGMENT_FREQUENT_TICK
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(teams, key = { _, team -> team.number }) { _, team ->
                ReorderableItem(reorderableLazyListState, key = team.number) { isDragging ->
                    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shadowElevation = elevation,
                        color = defaultSecondary,
                        contentColor = defaultOnPrimary,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Yellow)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Team ${team.number}, ${team.name}",
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            )
                            IconButton(
                                modifier = Modifier.draggableHandle(
                                    onDragStarted = {
                                        ViewCompat.performHapticFeedback(
                                            view,
                                            HapticFeedbackConstantsCompat.GESTURE_START
                                        )
                                        saveStratData.value = true
                                    },
                                    onDragStopped = {
                                        ViewCompat.performHapticFeedback(
                                            view,
                                            HapticFeedbackConstantsCompat.GESTURE_END
                                        )
                                    }
                                ),
                                onClick = { }
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = "Reorder")
                            }
                        }
                    }
                }
            }
        }
    }
}