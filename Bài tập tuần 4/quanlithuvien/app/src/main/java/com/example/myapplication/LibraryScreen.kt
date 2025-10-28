package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Lớp dữ liệu đại diện cho một cuốn sách
data class Book(val id: Int, val title: String, val author: String)

// Danh sách mẫu các cuốn sách (để mô phỏng dữ liệu)
val sampleBooks = listOf(
    Book(1, "Lão Hạc", "Nam Cao"),
    Book(2, "Số Đỏ", "Vũ Trọng Phụng"),
    Book(3, "Dế Mèn Phiêu Lưu Ký", "Tô Hoài")
)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                LibraryScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    // State để lưu trữ danh sách các cuốn sách
    var books by remember { mutableStateOf(sampleBooks) }
    var newBookTitle by remember { mutableStateOf("") }
    var newBookAuthor by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản Lý Thư Viện") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Phần thêm sách mới
            AddBookSection(
                title = newBookTitle,
                author = newBookAuthor,
                onTitleChange = { newBookTitle = it },
                onAuthorChange = { newBookAuthor = it },
                onAddBook = {
                    if (newBookTitle.isNotBlank() && newBookAuthor.isNotBlank()) {
                        val newId = (books.maxOfOrNull { it.id } ?: 0) + 1
                        books = books + Book(newId, newBookTitle, newBookAuthor)
                        // Xóa nội dung trong các ô nhập liệu
                        newBookTitle = ""
                        newBookAuthor = ""
                    }
                }
            )

            // Danh sách các cuốn sách
            BookList(books = books, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun AddBookSection(
    title: String,
    author: String,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onAddBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Thêm sách mới", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Tên sách") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = author,
            onValueChange = onAuthorChange,
            label = { Text("Tác giả") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onAddBook,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Thêm")
        }
    }
}

@Composable
fun BookList(books: List<Book>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
           BookItem(book = book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Tác giả: ${book.author}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    MyApplicationTheme {
        LibraryScreen()
    }
}