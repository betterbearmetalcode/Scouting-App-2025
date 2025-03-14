package pages

import nodes.RootNode
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.github.sarxos.webcam.Webcam
import composables.TriStateCheckBox
import composables.Profile
import composables.download
import defaultError
import defaultOnError
import defaultOnPrimary
import defaultOnSecondary
import defaultOnSurface
import defaultPrimaryVariant
import defaultSecondary
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.lang.Integer.parseInt

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun PitsScoutMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    pitsPerson: MutableState<String>,
    scoutName: MutableState<String>
) {
    @Composable
    fun View(modifier: Modifier) {
        val photoArray by remember { mutableStateOf(ArrayList<ImageBitmap>())}
        var pitsPersonDD by remember { mutableStateOf(false) }
        val numOfPitsPeople by remember { mutableStateOf(6) }
        var scoutedTeamName by remember { mutableStateOf("") }
        var scoutedTeamNumber by remember { mutableStateOf("") }
        var robotLength by remember{mutableStateOf("")}
        var robotWidth by remember{ mutableStateOf("")}
        var robotTypeDropDown by remember { mutableStateOf(false) }
        var robotType by remember { mutableStateOf("NoneSelected") }
        var hasImage by remember { mutableStateOf(false) }
        var collectPrefDD by remember{ mutableStateOf(false)}
        var collectPreference by remember { mutableStateOf("None Selected") }
        var concerns by remember { mutableStateOf("") }
        val webcam = Webcam.getDefault()
        var photoAmount by remember { mutableStateOf(0) }
        val scrollState = rememberScrollState(0)
        val isScrollEnabled by remember{ mutableStateOf(true)}
        var robotCard by remember {mutableStateOf(false)}
        var photoAlert by remember { mutableStateOf(false) }
       // val perimeterChecked by remember { mutableStateOf(if (robotLength!="" && robotWidth!=""&& parseInt(robotLength) + parseInt(robotWidth) > 60) "crossmark.png" else "checkmark.png"
        Column(modifier = Modifier.verticalScroll(state = scrollState, enabled = isScrollEnabled).padding(5.dp)) {
            Box( modifier = Modifier.offset(20.dp, 15.dp).fillMaxWidth()) {
                Text(
                    text = "Pits",
                    fontSize = 50.sp,
                    color = defaultOnPrimary,
                )
                TextButton(
                    onClick = { pitsPersonDD = true },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp)
                ) {
                    Text(
                        text = pitsPerson.value,
                        fontSize = 40.sp,
                        color = defaultOnPrimary,
                    )
                }
                Box(modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp).offset(0.dp,15.dp)) {
                    DropdownMenu(
                        expanded = pitsPersonDD,
                        onDismissRequest = { pitsPersonDD = false },
                        modifier = Modifier.background(color = defaultOnSurface).clip(RoundedCornerShape(7.5.dp))

                    ) {
                        for(x in 1..numOfPitsPeople){
                            DropdownMenuItem(
                                onClick = {
                                    pitsPersonDD = false
                                    pitsPerson.value = "P$x"
                                }
                            ) {
                                Text("P$x", color = defaultOnPrimary)
                            }
                        }
                    }
                }
            }
            Row(){
                Column{
                    Text(
                        text="Team",
                        fontSize = 15.sp,
                        color = defaultOnPrimary
                    )
                    Text(
                        text=" Name:",
                        fontSize = 15.sp,
                        color = defaultOnPrimary
                    )}
                OutlinedTextField(
                    value = scoutedTeamName,
                    onValueChange ={ scoutedTeamName = it},
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 19.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = defaultOnError, focusedBorderColor = defaultSecondary, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(1f/2f)
                )
                Column{
                    Text(
                        text="Team",
                        fontSize = 15.sp,
                        color = defaultOnPrimary
                    )
                    Text(
                        text=" Number:",
                        fontSize = 15.sp,
                        color = defaultOnPrimary
                    )}
                OutlinedTextField(
                    value = scoutedTeamNumber,
                    onValueChange ={ scoutedTeamNumber = it.filter {it.isDigit()}},
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 19.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = defaultOnError, focusedBorderColor = defaultSecondary, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(7f/8f).padding(5.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.5.dp))
            Divider(color = defaultOnSecondary, thickness = 2.dp, modifier = Modifier.clip(CircleShape))
            Spacer(modifier = Modifier.height(7.5.dp))
            Row {
                Text(
                    text = "Dimensions"
                )
                OutlinedTextField(
                    value = robotLength,
                    onValueChange = { text -> robotLength = text.filter { it.isDigit() }; val oldText = robotLength; if (robotLength.length > 8) robotLength = oldText},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(6, 9, 13),
                        focusedBorderColor = Color.Yellow,
                        textColor = defaultOnPrimary
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
                )
                Text(
                    text = "inches by ",
                    color = Color.Gray
                )
                OutlinedTextField(
                    value = robotWidth,
                    onValueChange = { text -> robotWidth = text.filter { it.isDigit() }; val oldText = robotWidth; if (robotWidth.length > 8) robotWidth = oldText},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(6, 9, 13),
                        focusedBorderColor = Color.Yellow,
                        textColor = defaultOnPrimary
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
                )
                Text(
                    text = "inches",
                    color = Color.Gray
                )
            }
            Row {
                Text(
                     text = "Perimeter Check?"
                )
                 if (robotLength!="" && robotWidth!=""&& parseInt(robotLength) + parseInt(robotWidth) < 60){
                    Image(
                        painter = org.jetbrains.compose.resources.painterResource("checkmark.png"),
                        contentDescription = "dimensions checked",
                        modifier = Modifier
                            .size(30.dp)
                            //.offset(x = (98.5).dp),
                    )
                 }else{
                    Image(
                        painter = org.jetbrains.compose.resources.painterResource("crossmark.png"),
                        contentDescription = "dimensions checked",
                        modifier = Modifier
                            .size(30.dp)
                           // .offset(x = (98.5).dp),
                    )
                }
            }
            Row {
                Text(
                    text = "Type"
                )
                OutlinedButton(
                    onClick = {
                        robotTypeDropDown = true
                    },
                    shape = CircleShape
                ) {
                    Text(
                        text = "Selected Robot Type: $robotType ",
                        color = defaultOnPrimary
                    )
                }
            }
            Box(modifier=Modifier.padding(15.dp,0.dp)/*.offset(0.dp,-25.dp)*/) {
                DropdownMenu(
                    expanded = robotTypeDropDown,
                    onDismissRequest = { robotTypeDropDown = false },
                    modifier = Modifier.background(color = Color(15, 15, 15)).clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Swerve"
                        }
                    ) {
                        Text("Swerve")
                    }
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Tank"
                        }
                    ) {
                        Text("Tank")
                    }
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Mecanum"
                        }
                    ) {
                        Text("Mecanum")
                    }
                }
            }
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if(photoAmount<3) {
                        if (webcam != null) {
                            webcam.open()
                            photoArray.add(webcam.image.toComposeImageBitmap())
                            webcam.close()
                            hasImage = true
                            photoAmount++
                        }
                    }else{
                        photoAlert = true
                    }
                }
            ) {
                Image(
                    painter = painterResource("KillCam.png"),
                    contentDescription = "Camera"
                )
                Column {
                    Text(
                        text ="Take Picture",
                        color= defaultOnPrimary
                    )
                    Text(
                        text ="*Ask Permission First",
                        color= Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
            if(photoAlert) {
                AlertDialog(title = {Text(text ="TOO MANY PHOTOS!!!")}, onDismissRequest = {photoAlert = false}, buttons = { Box(modifier = Modifier.fillMaxWidth()){Button(onClick = {photoAlert = false},modifier = Modifier.align(Alignment.Center)){Text(text="Dismiss",color = defaultError)}}}, modifier = Modifier.clip(
                    RoundedCornerShape(5.dp)).border(BorderStroke(3.dp,defaultPrimaryVariant),RoundedCornerShape(5.dp)))
            }
            Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                photoArray.forEach {
                    Image(
                        painter = BitmapPainter(it),
                        contentDescription = "Robot image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(RoundedCornerShape(7.5.dp))
                            .fillMaxSize()
                    )
                    TextButton(
                        onClick = {
                            photoArray.remove(it)
                            photoAmount--
                        },
                        ) {
                        Image(
                            painter = painterResource("trash.png"),
                            contentDescription = "Delete"
                        )
                    }
                }
            }
            if (photoAmount >= 1){
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = "Amount of Photos: $photoAmount",color = Color.Gray ,modifier = Modifier.align(Alignment.CenterEnd))
                }
            }
            Text(
                text = "Strengths:",
                fontSize = 30.sp
            )

            Divider(color = defaultPrimaryVariant, thickness = 2.dp, modifier = Modifier.clip(CircleShape))

            TriStateCheckBox("Amp:", ampStrength, modifier = Modifier.scale(1.25f))
            TriStateCheckBox("Speaker:", speakerStrength, modifier = Modifier.scale(1.25f))
            TriStateCheckBox("Climb", climbStrength, modifier = Modifier.scale(1.25f))
            TriStateCheckBox("Trap:", trapStrength, modifier = Modifier.scale(1.25f))


            OutlinedButton(
                onClick = {
                    collectPrefDD = true
                },
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape
            ) {
                Text(
                    text ="Collection Preference: $collectPreference",
                    fontSize = 15.sp,
                    color = defaultOnPrimary
                )
            }
            Box(modifier=Modifier.padding(15.dp,0.dp)) {
                DropdownMenu(
                    expanded = collectPrefDD,
                    onDismissRequest = { collectPrefDD = false },
                    modifier = Modifier.background(color = Color(15, 15, 15)).clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "OverTheBumper"
                        }
                    ) {
                        Text("Over The Bumper")
                    }
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "UnderTheBumper"
                        }
                    ) {
                        Text("Under The Bumper")
                    }
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "from the Feeder Station"
                        }
                    ) {
                        Text("Feeder Station")
                    }
                }
            }

            Text(
                text ="Concerns:",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = concerns,
                onValueChange ={ concerns = it},
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(6,9,13), focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally).height(90.dp)
            )
            var string = "I am $robotWidth by $robotLength, I like to intake using $collectPreference. I enjoy long, luxurious walks on the beach with my intense $robotType drive.\n As you'll find out I am very efficient in multiple ways;\n" +
                    " Amp: ${ampStrength.value} \n" +
                    " Speaker: ${speakerStrength.value} \n" +
                    " Climb: ${climbStrength.value} \n" +
                    " Trap: ${trapStrength.value} \n" +
                    " You should generally be concerned about my $concerns."
            Row{
                OutlinedButton(onClick = { if (photoArray.size >= 1) { robotCard = true }}) { Text(text = "Submit", color = defaultOnPrimary) }
                OutlinedButton(onClick = { robotCard = false }) { Text(text = "Close", color = defaultOnPrimary) }
                OutlinedButton(onClick = { download(photoArray,scoutedTeamNumber,string,photoAmount)}) { Text(text = "Download", color = defaultOnPrimary) }
                OutlinedButton(onClick = { backStack.push(RootNode.NavTarget.MainMenu) }) { Text(text = "Back", color = defaultOnPrimary)
                }
            }
            if(robotCard){
                Box(modifier = Modifier.padding(5.dp)) {
                    Profile(
                        photoArray, scoutedTeamName, scoutedTeamNumber, robotType, collectPreference,
                        robotLength, robotWidth, ampStrength.value, speakerStrength.value,
                        climbStrength.value, trapStrength.value, concerns,scoutName.value)
                }
//                Image(
//                    painter = painterResource(photoArray[0].toAwtImage()),
//                    contentDescription = ""
//                )
            }
        }
    }
}


@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
@Composable
actual fun PitsScoutMenu(
    backStack: BackStack<RootNode.NavTarget>,
    pitsPerson: MutableState<String>,
    scoutName: MutableState<String>,
    numOfPitsPeople: MutableIntState
) {
}