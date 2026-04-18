// Soyutlama (Abstraction): Bir uygulamanın içindeki karmaşık işleyişi gizleyip,
// sadece temel görevi ön plana çıkarmaktır. (abstract sınıflar kendiliğinden miras verebilir)

abstract class Calisan {
    // İşlerin saklandığı bir liste oluşturduk
    val yapilanIsler = mutableListOf<String>()

    // Soyut (Gövdesi olmayan) metod, her çalışan farklı şekillerde doldurmalı
    abstract fun isiniYap() 

    // Soyutlamanın parçası olan ve tüm çalışanlar için ortak kalacak metod
    fun isEkle(isAdi: String) {
        yapilanIsler.add(isAdi)
    }
}

class Yazilimci : Calisan() {
    override fun isiniYap() {
        println("Kod yazıyor ve mevcut yazılım test ediliyor...")
    }
}

class Tasarimci : Calisan() {
    override fun isiniYap() {
        println("Arayüz tasarımı yapılıyor ve sayfalar renklendiriliyor...")
    }
}

fun main() {
    val yazilimci = Yazilimci()
    val tasarimci = Tasarimci()
    
    yazilimci.isEkle("Veritabanı Güncellemesi")
    tasarimci.isEkle("Logo Düzenlemesi")
    
    val calisanlarListesi = listOf(yazilimci, tasarimci)
    
    for (calisan in calisanlarListesi) {
        calisan.isiniYap()
        println("Tamamlanan İşler: ${calisan.yapilanIsler}\n")
    }
}
