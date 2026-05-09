package com.turkcell.libraryapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.halit.ui.screen.auth.RegisterScreen
import com.turkcell.libraryapp.ui.screen.HomeScreen
import com.turkcell.libraryapp.ui.screen.LoginScreen
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * ✅ KOIN İLE GÜNCELLENMİŞ HALI
 *
 * ÖNCEKİ HAL:
 *   val authViewModel: AuthViewModel = viewModel()
 *   ↑ Compose'un kendi viewModel() fonksiyonu ile oluşturuluyordu.
 *     Ama AuthViewModel artık constructor'dan parametre aldığı için
 *     viewModel() tek başına yetmez, Koin'in koinViewModel()'ini kullanmamız gerekir.
 *
 * YENİ HAL:
 *   val authViewModel: AuthViewModel = koinViewModel()
 *   ↑ Koin, AppModule'deki tanıma bakarak AuthRepository'yi enjekte eder
 *     ve AuthViewModel'i oluşturur.
 */
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    // ✅ Koin ile ViewModel alma - AuthRepository otomatik enjekte edilir
    val authViewModel: AuthViewModel = koinViewModel()

    // BookViewModel şimdilik eski yöntemle (parametresiz constructor)
    val bookViewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route)
    {
        composable(Screen.Login.route) { LoginScreen(
            onNavigateToRegister = { navController.navigate(Screen.Register.route) },
            onLoginSuccess = {role ->
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.Login.route) {inclusive=true}
                    // Yığın yalnızca verilen URL ile kalacaktı (false)
                }
            },
            authViewModel
        ) }
        // ÖDEV 1: Kayıt ol'a success yapısı kurulacak.
        composable(Screen.Register.route) { RegisterScreen(
            onNavigateToLogin = { navController.navigate(Screen.Login.route) },
            onRegisterSuccess = { role ->                 //anasayfya yönlendirme 
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
            authViewModel = authViewModel
        ) }
        composable(Screen.Homepage.route) {
            HomeScreen(authViewModel, bookViewModel)
        }
    }
}
