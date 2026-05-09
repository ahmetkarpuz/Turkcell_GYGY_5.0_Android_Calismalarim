package com.turkcell.libraryapp.di

import com.turkcell.libraryapp.data.repository.AuthRepository
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//Koin hakkında;
/**
 *Koin Modülü - Uygulamadaki tüm bağımlılıklar burada tanımlanır.
 
 *single { } -> Uygulama boyunca TEK bir instance oluşturur (Singleton).
                AuthRepository her yerde aynı nesne olarak kullanılır.
 
 *viewModel { } -> Android ViewModel lifecycle'ına uygun şekilde oluşturur.
              get() ifadesi, Koin'den AuthRepository instance'ını otomatik alır.
 */


val appModule = module {

    // AuthRepository -> Singleton olarak tanımlandı
    // Uygulama boyunca tek bir AuthRepository instance'ı kullanılacak
    single { AuthRepository() }

    //AuthViewModel -< Constructor'dan AuthRepository alıyor
    //get() -> Koin, yukarıda tanımlanan AuthRepository'yi otomatik enjekte eder
    viewModel { AuthViewModel(get()) }
}
