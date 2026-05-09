package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Profile
import com.turkcell.libraryapp.data.repository.AuthRepository
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Sistem bu 4 durumdan birinde olabilir.
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val role: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

/**
 * ✅ KOIN İLE GÜNCELLENMİŞ HALI
 *
 * ÖNCEKİ HAL (Manuel bağımlılık):
 *   class AuthViewModel : ViewModel() {
 *       private val repository = AuthRepository()  ← Kendisi oluşturuyordu
 *   }
 *
 * YENİ HAL (Koin ile DI):
 *   class AuthViewModel(private val repository: AuthRepository) : ViewModel()
 *       ↑ Repository dışarıdan (Koin tarafından) enjekte ediliyor
 *
 * Bu sayede:
 *   - AuthViewModel, AuthRepository'nin nasıl oluşturulduğunu bilmek zorunda değil
 *   - Test yazarken sahte (mock) bir AuthRepository verebiliriz
 *   - Bağımlılıklar tek bir merkezden (AppModule) yönetiliyor
 */
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    // ViewModel ilk oluşturulduğunda bu blok çalışır ve oturum kontrolünü tetikler
    init {
        checkUserSession()
    }

    // Kullanıcının daha önce giriş yapıp yapmadığını ve token süresini kontrol eder
    private fun checkUserSession() {
        viewModelScope.launch {
            val session = supabase.auth.currentSessionOrNull()

            if (session != null) {
                // Kullanıcı zaten giriş yapmış, direkt anasayfaya (Success) yönlendir
                _authState.value = AuthState.Success("student")
            } else {
                // Giriş yapmamış, Idle state'inde kalsın ve Login ekranını göstersin
                _authState.value = AuthState.Idle
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signIn(email, password)
                .onSuccess {
                    val userId = repository.getCurrentUserId() // userId Geliyo mu?
                    if(userId != null) {
                        val profile = repository.getProfile(userId) // profile geliyo mu?
                        _profile.value = profile // doğru set ediliyor mu?
                        _authState.value = AuthState.Success("student")
                    } else {
                        _authState.value = AuthState.Error("Profil bulunamadı.")
                    }
                }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "Giriş başarısız") }
        }
    }

    fun signUp(
        email: String,
        password: String,
        fullName: String,
        studentNo: String?
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signUp(email, password, fullName, studentNo)
                .onSuccess { _ -> _authState.value = AuthState.Success("student") }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "Kayıt başarısız") }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
