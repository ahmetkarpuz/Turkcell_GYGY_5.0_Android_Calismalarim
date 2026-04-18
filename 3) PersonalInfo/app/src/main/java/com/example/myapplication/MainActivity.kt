package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFC900)
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    // Tüm ekranın kaydırılabilir olmasını sağlayan temel yapı
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Kartlar arası standart boşluk
    ) {

        HeaderSection()

        //Deneyimler
        SectionTitle(title = "Deneyimler", icon = Icons.Default.Star)
        ExperienceItem(
            title = "T3 AI Lisans Araştırmacı Bursiyeri",
            company = "T3 Vakfı",
            date = "Ocak 2026 - Hâlen"
        )
        ExperienceItem(
            title = "Stajyer Yazılım Mühendisi (Part-Time)",
            company = "Teknoloji Transfer Ofisi - İGÜ",
            date = "Aralık 2024 - Hâlen"
        )
        ExperienceItem(
            title = "Bilgi Teknolojileri Stajyeri (Yaz Stajı)",
            company = "Koton Genel Müdürlüğü",
            date = "Ağustos - Eylül 2024"
        )

        //Ödüller ve Sertifikalar
        SectionTitle(title = "Ödüller ve Sertifikalar", icon = Icons.Default.Star)
        SimpleListItem("Teknofest Eylem Temelli BDM Geliştirme - Derece (2.'lik)", "2025")
        SimpleListItem("Teknofest Tarım Teknolojileri Yarışması - Finalist", "2025")
        SimpleListItem("YZTA - Hackathon Ürün Geliştirme Sertifikası", "2025")
        SimpleListItem("Teknofest Türkçe LLM - Finalist (2 Kez)", "2024-2025")
        SimpleListItem("Coursera - DeepLearning.AI & Stanford", "2024")

        //yabancı dil
        SectionTitle(title = "Yabancı Diller", icon = Icons.Default.Person)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "• İngilizce (B1)", fontWeight = FontWeight.Medium)
                Text(text = "• Rusça (A1)", fontWeight = FontWeight.Medium)
                Text(text = "• Arapça (A1)", fontWeight = FontWeight.Medium)
            }
        }
        //yerenekler
        SectionTitle(title = "Yetenekler", icon = Icons.Default.Build)
        val skills = listOf("Kotlin", "Java", "Python", "C++", "SQL", "Git", "Yapay Zeka", "Veri Analizi", "NLP", "UML")
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                skills.forEach { skill ->
                    Text(
                        text = "• $skill",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // En alta boşluk
    }
}

//ALT BİLEŞENLER(COMPOSABLES)
@Composable
fun HeaderSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0000FF)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profil",
                    tint = Color(0xFFFFC900),
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Ahmet Karpuz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Yazılım Mühendisliği",
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun SectionTitle(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF2C3E50))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun ExperienceItem(title: String, company: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = company, color = Color(0xFF34495E), fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = date, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SimpleListItem(text: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = text, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}