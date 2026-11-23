
package com.freecut.app

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

private val Background = Color(0xFF080912)
private val Panel = Color(0xFF141626)
private val Purple = Color(0xFF9B5CFF)
private val Cyan = Color(0xFF00D9FF)
private val White = Color(0xFFF5F5FF)
private val Muted = Color(0xFF9295AB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Purple,
                    secondary = Cyan,
                    background = Background,
                    surface = Panel
                )
            ) {
                FreeCutApp()
            }
        }
    }
}

data class FreeCutFilter(
    val name: String,
    val color: Color,
    val description: String
)

@Composable
fun FreeCutApp() {
    var selectedTab by remember { mutableStateOf("Home") }
    var selectedFilter by remember { mutableStateOf("Original") }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val filters = listOf(
        FreeCutFilter("Original", Color.Transparent, "Natural look"),
        FreeCutFilter("Cinematic", Color(0xFFB77A45), "Film warmth"),
        FreeCutFilter("Teal Orange", Color(0xFF008C9E), "Movie contrast"),
        FreeCutFilter("Dark Action", Color(0xFF3B174F), "Dark dramatic look"),
        FreeCutFilter("Neon", Color(0xFF8B35FF), "Futuristic glow"),
        FreeCutFilter("Vintage", Color(0xFFB68A54), "Retro film look"),
        FreeCutFilter("Cold Blue", Color(0xFF1766B2), "Cool atmosphere")
    )

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            videoUri = uri
            selectedTab = "Editor"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopBar()

        when (selectedTab) {
            "Home" -> HomeScreen(
                onNewProject = { picker.launch("video/*") },
                onOpenEditor = { selectedTab = "Editor" }
            )

            "Editor" -> EditorScreen(
                videoUri = videoUri,
                filters = filters,
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it },
                onImport = { picker.launch("video/*") }
            )

            "Effects" -> StudioScreen(
                title = "EFFECTS STUDIO",
                subtitle = "Create your visual signature",
                items = listOf(
                    "Motion Blur", "Glow", "RGB Split",
                    "Shake", "Glitch", "Light Leak",
                    "Flash", "Film Grain"
                )
            )

            "Text" -> StudioScreen(
                title = "TEXT STUDIO",
                subtitle = "Make every word cinematic",
                items = listOf(
                    "Modern Bold", "Cinematic Title",
                    "Neon Text", "Minimal", "Subtitle",
                    "Retro Title", "Action Title"
                )
            )

            "Audio" -> StudioScreen(
                title = "AUDIO STUDIO",
                subtitle = "Sound creates the emotion",
                items = listOf(
                    "Import Music", "Sound Effects",
                    "Voiceover", "Volume", "Fade In",
                    "Fade Out", "Audio Sync"
                )
            )

            else -> StudioScreen(
                title = "FREECUT STUDIO",
                subtitle = "Your creative workspace",
                items = listOf(
                    "My Projects", "Settings",
                    "Export Settings", "About FreeCut"
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        BottomNavigation(
            selected = selectedTab,
            onSelect = { selectedTab = it }
        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 20.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(
                    Brush.linearGradient(
                        listOf(Purple, Cyan)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "F",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(Modifier.width(11.dp))

        Column {
            Text(
                "FREECUT",
                color = White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                "NEXT GENERATION EDITOR",
                color = Cyan,
                fontSize = 8.sp,
                letterSpacing = 2.sp
            )
        }

        Spacer(Modifier.weight(1f))

        Text("2035", color = Purple, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HomeScreen(
    onNewProject: () -> Unit,
    onOpenEditor: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        Text(
            "CREATE WITHOUT LIMITS.",
            color = White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            "Your imagination. Your timeline. Your movie.",
            color = Muted,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF30145B),
                            Color(0xFF111B3B),
                            Color(0xFF082C3C)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Text(
                    "FREECUT 2035",
                    color = Cyan,
                    fontSize = 12.sp,
                    letterSpacing = 3.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Turn your ideas\ninto cinema.",
                    color = White,
                    fontSize = 29.sp,
                    lineHeight = 35.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = onNewProject,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(
                        "+  NEW PROJECT",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(27.dp))

        SectionTitle("YOUR WORKSPACE", "TOOLS")

        Spacer(Modifier.height(14.dp))

        val tools = listOf(
            "✂" to "Video Editor",
            "◈" to "Effects Studio",
            "T" to "Text Studio",
            "♫" to "Audio Studio"
        )

        tools.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { item ->
                    ToolCard(
                        symbol = item.first,
                        title = item.second,
                        modifier = Modifier.weight(1f),
                        onClick = onOpenEditor
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(15.dp))

        SectionTitle("DESIGNED FOR CREATORS", "FREECUT")

        Spacer(Modifier.height(12.dp))

        FeatureRow("◉", "Cinematic filters", "Create your movie look")
        FeatureRow("✦", "Creative effects", "Give your videos energy")
        FeatureRow("♫", "Audio tools", "Build powerful sound")
        FeatureRow("▣", "Video workspace", "Edit in one place")

        Spacer(Modifier.height(25.dp))
    }
}

@Composable
fun EditorScreen(
    videoUri: Uri?,
    filters: List<FreeCutFilter>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onImport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "VIDEO EDITOR",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            "Your creative timeline starts here.",
            color = Muted,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            if (videoUri != null) {
                key(videoUri) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            VideoView(context).apply {
                                setVideoURI(videoUri)
                                setOnPreparedListener { player ->
                                    player.isLooping = true
                                    start()
                                }
                            }
                        }
                    )
                }

                val tint = filters.firstOrNull {
                    it.name == selectedFilter
                }?.color ?: Color.Transparent

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(tint.copy(alpha = 0.15f))
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("▶", color = Cyan, fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("NO VIDEO SELECTED", color = White)
                    Text(
                        "Import a video to begin editing",
                        color = Muted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(13.dp))

        Button(
            onClick = onImport,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("＋  IMPORT VIDEO")
        }

        Spacer(Modifier.height(23.dp))

        SectionTitle("CINEMATIC FILTERS", "LOOKS")

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filters.forEach { filter ->
                FilterCard(
                    filter = filter,
                    selected = selectedFilter == filter.name,
                    onClick = { onFilterSelected(filter.name) }
                )
            }
        }

        Spacer(Modifier.height(23.dp))

        SectionTitle("EDITING TOOLS", "TOOLS")

        Spacer(Modifier.height(12.dp))

        val editTools = listOf(
            "✂" to "Trim",
            "⚡" to "Split",
            "♫" to "Audio",
            "T" to "Text",
            "◈" to "Effects",
            "▣" to "Crop"
        )

        editTools.chunked(3).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { item ->
                    ToolCard(
                        symbol = item.first,
                        title = item.second,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        Text(
            "TIMELINE",
            color = Cyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Panel)
                .border(1.dp, Color(0xFF30334B), RoundedCornerShape(14.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (videoUri == null) "Import media to create your timeline"
                else "Video track loaded • Timeline engine coming next",
                color = Muted,
                fontSize = 11.sp
            )
        }

        Spacer(Modifier.height(25.dp))
    }
}

@Composable
fun FilterCard(
    filter: FreeCutFilter,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(105.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) Color(0xFF29203F) else Panel)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) Purple else Color(0xFF292C40),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(9.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = filter.color.takeUnless {
                    it == Color.Transparent
                } ?: Color(0xFF292D42)
            ),
            shape = RoundedCornerShape(9.dp)
        ) {
            Text(
                if (selected) "✓" else "◈",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(7.dp))

        Text(
            filter.name,
            color = White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            filter.description,
            color = Muted,
            fontSize = 9.sp
        )
    }
}

@Composable
fun StudioScreen(
    title: String,
    subtitle: String,
    items: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            title,
            color = White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(Modifier.height(7.dp))

        Text(subtitle, color = Muted, fontSize = 13.sp)

        Spacer(Modifier.height(25.dp))

        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 11.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Panel)
                    .border(
                        1.dp,
                        Color(0xFF292C40),
                        RoundedCornerShape(15.dp)
                    )
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("✦", color = Cyan, fontSize = 20.sp)
                Spacer(Modifier.width(14.dp))
                Text(
                    item,
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.weight(1f))
                Text("›", color = Purple, fontSize = 23.sp)
            }
        }
    }
}

@Composable
fun ToolCard(
    symbol: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(105.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Panel
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                symbol,
                color = Cyan,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FeatureRow(
    symbol: String,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF20203A)),
            contentAlignment = Alignment.Center
        ) {
            Text(symbol, color = Cyan, fontSize = 20.sp)
        }

        Spacer(Modifier.width(13.dp))

        Column {
            Text(title, color = White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            title,
            color = White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.weight(1f))
        Text(
            label,
            color = Purple,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomNavigation(
    selected: String,
    onSelect: (String) -> Unit
) {
    val tabs = listOf(
        "Home" to "⌂",
        "Editor" to "✂",
        "Effects" to "◈",
        "Text" to "T",
        "Audio" to "♫"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF101220))
            .navigationBarsPadding()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach { tab ->
            val active = selected == tab.first

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (active) Color(0xFF29203F)
                        else Color.Transparent
                    )
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = { onSelect(tab.first) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        tab.second,
                        color = if (active) Cyan else Muted,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    tab.first,
                    color = if (active) White else Muted,
                    fontSize = 9.sp
                )
            }
        }
    }
}
