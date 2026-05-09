package com.turkcell.libraryapp.data.supabase

import com.turkcell.libraryapp.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage //Bunu import etmeyi unutma

val supabase = createSupabaseClient(
    supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
    supabaseUrl = BuildConfig.SUPABASE_URL
) {
    install(Postgrest)
    install(Auth)
    install(Storage) //Storage modülünü aktif ettik
}