package com.example.tuan5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Import thư viện Coil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    private val tag = "MainActivityCompose"

    // Thay đổi State để lưu trữ FirebaseUser (cho phép null)
    private val _firebaseUser = mutableStateOf<FirebaseUser?>(null)
    private val firebaseUser: State<FirebaseUser?> = _firebaseUser

    // State để lưu thông báo lỗi
    private val _errorText = mutableStateOf<String?>(null)
    private val errorText: State<String?> = _errorText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo Firebase Auth
        auth = Firebase.auth
        // Gán người dùng hiện tại khi khởi động
        _firebaseUser.value = auth.currentUser

        // Cấu hình Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Đăng ký ActivityResultLauncher
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d(tag, "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account)
                    } catch (e: ApiException) {
                        Log.w(tag, "Google sign in failed", e)
                        _errorText.value = "Đăng nhập Google thất bại."
                    }
                }
            }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gọi màn hình chính, truyền vào các giá trị state
                    AuthScreen(
                        firebaseUser = firebaseUser.value,
                        errorText = errorText.value,
                        onSignInClick = { signIn() },
                        onSignOutClick = { signOut() }
                    )
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // Thêm hàm đăng xuất
    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            _firebaseUser.value = null // Cập nhật state
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _firebaseUser.value = user // Cập nhật state
                    _errorText.value = null // Xóa lỗi
                    Log.d(tag, "signInWithCredential:success")
                } else {
                    _firebaseUser.value = null
                    _errorText.value = "Xác thực Firebase thất bại."
                    Log.w(tag, "signInWithCredential:failure", task.exception)
                }
            }
    }
}

// Composable chính, quyết định hiển thị màn hình Login hay Profile
@Composable
fun AuthScreen(
    firebaseUser: FirebaseUser?,
    errorText: String?,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (firebaseUser != null) {
            // Nếu đã đăng nhập, hiển thị ProfileScreen
            ProfileScreen(user = firebaseUser, onSignOutClick = onSignOutClick)
        } else {
            // Nếu chưa đăng nhập, hiển thị LoginButton
            LoginButton(onSignInClick = onSignInClick)
        }

        // Hiển thị lỗi nếu có
        errorText?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Composable hiển thị thông tin Profile
@Composable
fun ProfileScreen(user: FirebaseUser, onSignOutClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Avatar
        AsyncImage(
            model = user.photoUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape), // Bo tròn ảnh
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tên
        Text(
            text = user.displayName ?: "Không có tên",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
            text = user.email ?: "Không có email",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Nút Đăng xuất
        Button(onClick = onSignOutClick) {
            Text("Đăng xuất")
        }
    }
}

// Composable cho nút Đăng nhập
@Composable
fun LoginButton(onSignInClick: () -> Unit) {
    Button(onClick = onSignInClick) {
        Text("Đăng nhập với Google")
    }
}

// Cập nhật Preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        // Bạn có thể chọn preview 1 trong 2 trạng thái

        // Preview khi chưa đăng nhập
        AuthScreen(
            firebaseUser = null,
            errorText = null,
            onSignInClick = {},
            onSignOutClick = {}
        )

        // // Preview khi đã đăng nhập (cần tạo 1 user giả)
        // val fakeUser = object : FirebaseUser() {
        //     override fun getDisplayName(): String = "Test User"
        //     override fun getEmail(): String = "test@example.com"
        //     override fun getPhotoUrl(): Uri? = Uri.parse("https://placehold.co/100x100")
        //     // ... (cần implement nhiều phương thức khác)
        // }
        // AuthScreen(firebaseUser = fakeUser, errorText = null, onSignInClick = {}, onSignOutClick = {})
    }
}

