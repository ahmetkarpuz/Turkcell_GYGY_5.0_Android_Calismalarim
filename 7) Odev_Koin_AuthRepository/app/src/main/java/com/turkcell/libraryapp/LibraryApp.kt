package com.turkcell.libraryapp

import android.app.Application
import com.turkcell.libraryapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application sınıfı - Uygulama başladığında Koin'i başlatır.
 *
 * Bu sınıf AndroidManifest.xml'de tanımlanmalıdır:
 *   android:name=".LibraryApp"
 *
 * startKoin { } bloğunda:
 *   - androidContext → Android context'ini Koin'e veriyoruz
 *   - modules → Hangi modüllerin yükleneceğini belirtiyoruz
 */
class LibraryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Koin'i başlat
        startKoin {
            // Android context'ini Koin'e tanıt
            androidContext(this@LibraryApp)

            // Tanımladığımız modülleri yükle
            modules(appModule)
        }
    }
}
