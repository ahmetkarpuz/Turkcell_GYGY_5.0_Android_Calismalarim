package com.example.a4recomposition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

//Supabase bağlantı
val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_ANON_KEY
) {
    install(Postgrest)
}

// 2. VERİTABANI MODELİ (Tablodaki sütunların kod karşılığı)
@Serializable
data class Skill(val name: String)

// 3. ANA MOTOR
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    SkillManagerScreen()
                }
            }
        }
    }
}

// 4. YETENEK YÖNETİM EKRANI
@Composable
fun SkillManagerScreen() {
    // Artık listemiz başlangıçta boş, verileri internetten çekeceğiz
    val skillsList = remember { mutableStateListOf<String>() }
    var inputText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // EKRAN AÇILDIĞINDA ÇALIŞAN KOD (Veritabanından verileri okuma)
    LaunchedEffect(Unit) {
        try {
            // Supabase'den skills tablosundaki verileri getir
            val fetchedSkills = supabase.postgrest["skills"].select().decodeList<Skill>()
            // Gelen verileri ekrandaki listemize ekle
            skillsList.addAll(fetchedSkills.map { it.name })
        } catch (e: Exception) {
            println("Veri çekme hatası: ${e.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // TODO 2: GERÇEK SUPABASE EKLEME İŞLEMİ
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Yeni yetenek ekle") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                // 1. Veritabanına yaz
                                val newSkill = Skill(name = inputText)
                                supabase.postgrest["skills"].insert(newSkill)

                                // 2. Ekrandaki listeye ekle (Recomposition)
                                skillsList.add(inputText)
                                inputText = ""
                            } catch (e: Exception) {
                                println("Ekleme hatası: ${e.message}")
                            }
                        }
                    }
                }
            ) {
                Text("Ekle")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TODO 1: GERÇEK SUPABASE SİLME İŞLEMİ
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(skillsList, key = { it }) { skill ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = skill, fontSize = 18.sp)

                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    try {
                                        // 1. Veritabanından sil (İsmi skill olan satırı bul ve sil)
                                        supabase.postgrest["skills"].delete {
                                            filter { eq("name", skill) }
                                        }
                                        // 2. Ekrandaki listeden çıkar (Recomposition)
                                        skillsList.remove(skill)
                                    } catch (e: Exception) {
                                        println("Silme hatası: ${e.message}")
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}