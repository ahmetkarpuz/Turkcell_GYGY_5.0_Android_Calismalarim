// Kapsülleme (Encapsulation): Verilerin kontrolsüz değiştirilmesini önlemek
// ve verilerle işlemleri bir araya getirmektir

class BankaHesabi(baslangicBakiyesi: Int) {
    val islemGecmisi = mutableListOf<String>()
    
   var bakiye: Int = baslangicBakiyesi
        set(deger) {
            if (deger >= 0) {
                field = deger
            } else {
                println("Hata: Bakiye eksi olamaz!")
            }
        }

    fun paraYatir(miktar: Int) {
        bakiye += miktar
        islemGecmisi.add("$miktar TL yatırıldı.")
    }

    fun gecmisiGoster() {
        println("       --------------- İşlem Geçmişi ---------------")
        for (islem in islemGecmisi) {
            println(islem)
        }
    }
}

fun main() {
    val hesap = BankaHesabi(100)
    hesap.paraYatir(50)
    hesap.bakiye = 1200
    
    hesap.gecmisiGoster()
    println("Güncel Bakiye: ${hesap.bakiye} TL")
}
