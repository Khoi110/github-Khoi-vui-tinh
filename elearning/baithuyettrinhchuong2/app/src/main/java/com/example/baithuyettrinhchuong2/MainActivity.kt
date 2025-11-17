package com.example.baithuyettrinhchuong2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.baithuyettrinhchuong2.ui.theme.Baithuyettrinhchuong2Theme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Baithuyettrinhchuong2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LifecycleTrackerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LifecycleTrackerScreen(modifier: Modifier = Modifier) {
    // State để lưu trữ trạng thái lifecycle hiện tại
    var currentState by remember { mutableStateOf("UNKNOWN") }
    // State để lưu danh sách lịch sử các sự kiện lifecycle đã xảy ra
    val eventHistory = remember { mutableStateListOf<String>() }
    val context = LocalContext.current

    // Hàm để định dạng thời gian
    val timeFormatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    fun addEvent(event: String) {
        val timestamp = timeFormatter.format(Date())
        eventHistory.add(0, "$timestamp: $event") // Thêm vào đầu danh sách
    }

    // Sử dụng LifecycleEventObserver để lắng nghe sự kiện lifecycle một cách hiện đại
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            currentState = event.name // Cập nhật trạng thái hiện tại
            addEvent(event.name)      // Thêm sự kiện vào lịch sử
        }

        // Lấy lifecycle của Activity và thêm observer vào
        val activity = context as ComponentActivity
        activity.lifecycle.addObserver(observer)

        // Dọn dẹp observer khi Composable bị hủy
        onDispose {
            activity.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Activity Lifecycle Tracker",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị trạng thái hiện tại
        Text(
            text = "Trạng thái hiện tại:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = currentState,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = getStateColor(currentState)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Nút để mở một Activity khác
        Button(onClick = {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Mở Activity Thứ Hai")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị lịch sử các sự kiện
        Text(
            text = "Lịch sử sự kiện:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray.copy(alpha = 0.2f))
        ) {
            items(eventHistory) { event ->
                Text(
                    text = event,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                Divider()
            }
        }
    }
}

// Hàm helper để chọn màu dựa trên trạng thái
@Composable
private fun getStateColor(state: String): Color {
    return when (state) {
        Lifecycle.Event.ON_CREATE.name -> Color(0xFF00C853) // Xanh lá
        Lifecycle.Event.ON_START.name -> Color(0xFF0091EA)   // Xanh dương
        Lifecycle.Event.ON_RESUME.name -> Color(0xFF2962FF)  // Xanh dương đậm
        Lifecycle.Event.ON_PAUSE.name -> Color(0xFFFFAB00)  // Vàng cam
        Lifecycle.Event.ON_STOP.name -> Color(0xFFD50000)   // Đỏ
        Lifecycle.Event.ON_DESTROY.name -> Color(0xFF212121) // Đen
        else -> Color.Gray
    }
}


@Preview(showBackground = true)
@Composable
fun LifecycleTrackerScreenPreview() {
    Baithuyettrinhchuong2Theme {
        LifecycleTrackerScreen()
    }
}

