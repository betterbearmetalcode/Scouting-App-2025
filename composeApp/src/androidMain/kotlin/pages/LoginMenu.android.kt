package pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import compKey
import createScoutMatchDataFolder
import createScoutPitsDataFolder
import createScoutStratDataFolder
import defaultError
import defaultOnPrimary
import defaultPrimaryVariant
import deleteFile
import deleteScoutMatchData
import deleteScoutPitsData
import deleteScoutStratData
import getCurrentTheme
import loadMatchDataFiles
import loadPitsDataFiles
import loadStratDataFiles
import nodes.RootNode
import nodes.TeamMatchStartKey
import nodes.betterParseInt
import nodes.createOutput
import nodes.match
import nodes.permPhotosList
import nodes.pitsReset
import nodes.teamDataArray
import nodes.reset
import nodes.saveData
import nodes.saveDataPopup
import nodes.stratReset
import java.io.File
import java.lang.Integer.parseInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun LoginMenu(
    backStack: BackStack<RootNode.NavTarget>,
    scoutName: MutableState<String>,
    comp: MutableState<String>,
    numOfPitsPeople: MutableIntState
) {
    val logo = File("Logo.png")
    var compDD by remember { mutableStateOf(false) }
    var deleteData by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val tbaMatches = listOf(
        "2024wabon",
        "2024wasam",
        "2024orsal",
        "2024pncmp",
        "2024hop"

    )

    var first by remember { mutableStateOf(true) }

    if(first) {
        createScoutMatchDataFolder(context)
        loadMatchDataFiles(context)

        createScoutPitsDataFolder(context)
        loadPitsDataFiles(context)

        createScoutStratDataFolder(context)
        loadStratDataFiles(context)

        first = false
    }

    Column {
//        AsyncImage(
//            model = logo,//turn into bitmap
//            contentDescription = "Logo"
//        )
        Text(
            text = "Login",
            fontSize = 45.sp,
            color = getCurrentTheme().onPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            color = defaultPrimaryVariant,
            thickness = 3.dp
        )
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Name", color = defaultOnPrimary)
            OutlinedTextField(
                value = scoutName.value,
                onValueChange = {scoutName.value = it},
                placeholder = { Text("First, Last Name") },
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = getCurrentTheme().background,
                    unfocusedTextColor = getCurrentTheme().onPrimary,
                    focusedContainerColor = getCurrentTheme().background,
                    focusedTextColor = getCurrentTheme().onPrimary,
                    cursorColor = getCurrentTheme().onSecondary,
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = getCurrentTheme().secondary
                )
            )
        }
        Box(modifier = Modifier.padding(15.dp).fillMaxWidth()) {
            OutlinedButton(
                onClick = { compDD = true },
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(3.dp, color = defaultPrimaryVariant),
                colors = ButtonDefaults.buttonColors(containerColor = getCurrentTheme().primary)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Competition: ${comp.value}",
                        color = getCurrentTheme().onPrimary,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "V",
                        color = getCurrentTheme().onPrimary,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
            DropdownMenu(expanded = compDD, onDismissRequest = { compDD = false; },modifier= Modifier.background(color = getCurrentTheme().onSurface)) {
                DropdownMenuItem(
                    onClick = { comp.value = "Bonney Lake"; compDD = false; compKey = tbaMatches[0]},
                    text = { Text(text = "Bonney Lake", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                )
                DropdownMenuItem(
                    onClick = { comp.value = "Lake Sammamish"; compDD = false; compKey = tbaMatches[1]},
                    text = { Text(text = "Lake Sammamish", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                )
                DropdownMenuItem(
                    onClick = { comp.value = "Salem"; compDD = false; compKey = tbaMatches[2]},
                    text ={ Text(text = "Salem", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                )
                DropdownMenuItem(
                    onClick = { comp.value = "Portland"; compDD = false; compKey = tbaMatches[3] },
                    text = { Text(text = "DCMP", color = getCurrentTheme().onPrimary, modifier = Modifier.background(color = getCurrentTheme().onSurface)) }
                )
                DropdownMenuItem(
                    onClick = { comp.value = "Houston"; compDD = false; compKey = tbaMatches[4]},
                    text ={ Text(text = "Houston", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                )
                OutlinedTextField(
                    value = comp.value,
                    onValueChange = { comp.value = it
                        compKey = it},
                    placeholder = { Text("Custom Competition Key") },
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = getCurrentTheme().background,
                        unfocusedTextColor = getCurrentTheme().onPrimary,
                        focusedContainerColor = getCurrentTheme().background,
                        focusedTextColor = getCurrentTheme().onPrimary,
                        cursorColor = getCurrentTheme().onSecondary,
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = getCurrentTheme().secondary
                    )
                )
            }

        }
        HorizontalDivider(
            color = defaultPrimaryVariant,
        )

        Box(modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally)) {
            OutlinedButton(
                onClick = {
                    if (comp.value != "" && scoutName.value != "")
                        backStack.push(RootNode.NavTarget.MainMenu)
                },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = getCurrentTheme().primary),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Submit",
                    color = getCurrentTheme().onPrimary
                )
            }
            OutlinedButton(
                onClick = { deleteData = true },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = getCurrentTheme().primary),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Delete Data",
                    color = getCurrentTheme().onPrimary
                )
            }
            if (deleteData) {
                BasicAlertDialog(
                    onDismissRequest = { deleteData = false },
                    modifier = Modifier.clip(
                        RoundedCornerShape(5.dp)
                    ).border(BorderStroke(3.dp, getCurrentTheme().primaryVariant), RoundedCornerShape(5.dp))
                        .background(getCurrentTheme().secondary)
                ) {
                    Box(modifier = Modifier.fillMaxWidth(8f / 10f).padding(5.dp).fillMaxHeight(1/8f)) {
                        Text(text = "Are you sure you want to delete all data?",
                            modifier = Modifier.padding(5.dp).align(Alignment.TopCenter)
                        )
                        androidx.compose.material.OutlinedButton(
                            onClick = {
                                permPhotosList.clear()
                                reset()
                                stratReset()
                                pitsReset()
                                deleteFile(context)
                                deleteScoutMatchData()
                                deleteScoutStratData()
                                deleteScoutPitsData()
                                deleteData = false
                            },
                            border = BorderStroke(2.dp, getCurrentTheme().secondaryVariant),
                            colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(backgroundColor = getCurrentTheme().secondary, contentColor = getCurrentTheme().onSecondary),
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Text(text = "Yes", color = getCurrentTheme().error)
                        }
                        androidx.compose.material.OutlinedButton(
                            onClick = { deleteData = false },
                            border = BorderStroke(2.dp, getCurrentTheme().secondaryVariant),
                            colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(backgroundColor = getCurrentTheme().secondary, contentColor = getCurrentTheme().onSecondary),
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(text = "No", color = getCurrentTheme().error)
                        }
                    }
                }
            }
        }
    }
}