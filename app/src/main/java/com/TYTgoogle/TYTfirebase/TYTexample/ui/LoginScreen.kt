package com.TYTgoogle.TYTfirebase.TYTexample.ui // 패키지 선언 확인

import android.app.Activity
// import android.content.Intent // signInIntent 생성 시 사용되나, 직접적인 import는 AuthUI 내부에서 처리
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
// import androidx.activity.result.contract.ActivityResultContracts // FirebaseAuthUIActivityResultContract 사용
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.platform.LocalContext // 현재 코드에서 직접 사용 X
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.TYTgoogle.TYTfirebase.TYTexample.BuildConfig
import androidx.compose.ui.unit.dp

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
// import kotlinx.coroutines.launch // 현재 직접적인 launch는 없음 (스낵바는 콜백으로 처리)

@Composable
fun LoginScreen(
    auth: FirebaseAuth, // 직접 구현 로그인 및 FirebaseUI 결과 처리 시 사용 가능
    onLoginSuccess: (FirebaseUser) -> Unit, // 로그인 성공 시 (두 방식 모두)
    onNavigateToSignUp: () -> Unit, // 직접 구현 회원가입 화면으로 이동 (FirebaseUI 사용 시 대체 가능)
    showSnackBar: (String) -> Unit
) {
    // 직접 구현 이메일/비밀번호 로그인을 위한 상태
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoadingDirectLogin by remember { mutableStateOf(false) } // 직접 로그인 로딩 상태

    // FirebaseUI Auth 결과를 처리하기 위한 ActivityResultLauncher
    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
    ) { result ->
        // FirebaseUI 결과 처리 함수 호출
        onFirebaseUISignInResult(result, onLoginSuccess, showSnackBar)
    }

    // FirebaseUI에서 사용할 인증 공급자 목록
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    // FirebaseUI 로그인 인텐트 생성
    val firebaseUiSignInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        // --- 옵션 1: FirebaseUI를 통한 로그인 ---
        Button(
            onClick = {
                signInLauncher.launch(firebaseUiSignInIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login / Sign Up with Google, Email (FirebaseUI)")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Or sign in with your email and password:")
        Spacer(modifier = Modifier.height(16.dp))

        // --- 옵션 2: 직접 구현된 이메일/비밀번호 로그인 ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoadingDirectLogin) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isLoadingDirectLogin = true
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                isLoadingDirectLogin = false
                                if (task.isSuccessful) {
                                    val user = task.result?.user
                                    if (user != null) {
                                        Log.d("LoginScreen", "Direct Email SignIn: Success")
                                        showSnackBar("Login Successful! Welcome ${user.email}")
                                        onLoginSuccess(user) // 성공 콜백 호출
                                    } else {
                                        // 이론적으로 task.isSuccessful이면 user는 null이 아님
                                        Log.e("LoginScreen", "Direct Email SignIn: Success but user is null")
                                        showSnackBar("Login successful, but user data is missing.")
                                    }
                                } else {
                                    Log.w("LoginScreen", "Direct Email SignIn: Failure", task.exception)
                                    showSnackBar("Login Failed: ${task.exception?.message}")
                                }
                            }
                    } else {
                        showSnackBar("Please enter email and password.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login with Email/Password")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // "Go to Sign Up" 버튼은 여전히 유효할 수 있습니다.
        // FirebaseUI가 이메일 가입도 처리하지만, 만약 별도의 디자인/흐름을 가진
        // 자체 회원가입 화면(SignUpScreen.kt)을 사용하고 싶다면 이 버튼을 유지합니다.
        // 만약 FirebaseUI의 이메일 가입 흐름으로 충분하다면 이 버튼은 필요 없습니다.
        TextButton(
            onClick = onNavigateToSignUp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign Up")
        }
    }
}

// FirebaseUI Auth 결과 처리 함수 (이름 변경하여 명확화)
private fun onFirebaseUISignInResult(
    result: FirebaseAuthUIAuthenticationResult,
    onLoginSuccess: (FirebaseUser) -> Unit,
    showSnackBar: (String) -> Unit
) {
    val response = result.idpResponse
    Log.d("LoginScreen", "onFirebaseUISignInResult - resultCode: ${result.resultCode}")

    if (result.resultCode == Activity.RESULT_OK) {
        Log.d("LoginScreen", "FirebaseUI: RESULT_OK")
        val user = FirebaseAuth.getInstance().currentUser // FirebaseUI가 로그인 처리 후 currentUser 설정
        if (user != null) {
            Log.d("LoginScreen", "FirebaseUI: User authenticated - ${user.email}")
            showSnackBar("Sign-in Successful with FirebaseUI! Welcome ${user.displayName ?: user.email}")
            onLoginSuccess(user)
        } else {
            // 이 경우는 발생하기 매우 어렵지만, 방어적으로 처리
            Log.e("LoginScreen", "FirebaseUI: RESULT_OK, but currentUser is NULL!")
            showSnackBar("Sign-in Successful, but user data is unexpectedly null.")
        }
    } else {
        // 로그인 실패 또는 사용자가 취소
        Log.w("LoginScreen", "FirebaseUI: SignIn Failed or Cancelled. IdpResponse: $response")
        if (response == null) {
            // 사용자가 UI를 닫았거나 뒤로가기 버튼을 누른 경우 (취소)
            showSnackBar("Sign-in cancelled.")
        } else {
            // 실제 오류 발생
            showSnackBar("Sign-in failed: ${response.error?.message}")
            Log.e("LoginScreen", "FirebaseUI Error: ", response.error)
        }
    }
}


// SignUpScreen은 FirebaseUI의 EmailBuilder를 사용하면 이 화면은 필요 없을 수 있습니다.
// 하지만 직접 구현한 이메일/비밀번호 로그인 UI를 사용한다면,
// 그에 맞는 직접 구현 회원가입 화면도 필요할 수 있습니다.
// 여기서는 SignUpScreen을 그대로 유지한다고 가정합니다.
@Composable
fun SignUpScreen(
    auth: FirebaseAuth,
    onSignUpSuccess: () -> Unit, // 회원가입 성공 시 (보통 로그인 화면으로 다시 이동하거나 자동 로그인)
    onNavigateToLogin: () -> Unit, // 로그인 화면으로 돌아가기
    showSnackBar: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (min. 6 characters)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        if (password == confirmPassword) {
                            if (password.length >= 6) { // Firebase 비밀번호 최소 길이 조건
                                isLoading = true
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        isLoading = false
                                        if (task.isSuccessful) {
                                            Log.d("SignUpScreen", "Firebase CreateUser: Success")
                                            showSnackBar("Account created successfully! Please login.")
                                            onSignUpSuccess() // 성공 콜백 (예: 로그인 화면으로 이동)
                                        } else {
                                            Log.w("SignUpScreen", "Firebase CreateUser: Failure", task.exception)
                                            showSnackBar("Sign Up Failed: ${task.exception?.message}")
                                        }
                                    }
                            } else {
                                showSnackBar("Password must be at least 6 characters.")
                            }
                        } else {
                            showSnackBar("Passwords do not match.")
                        }
                    } else {
                        showSnackBar("Please fill in all fields.")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Create Account")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}