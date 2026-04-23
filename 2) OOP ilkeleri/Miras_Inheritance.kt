// Kalıtım / Miras (Inheritance): Ebeveyn bir yapının (arayüz, soyut sınıf vb.) 
// özelliklerinin ve yeteneklerinin alt sınıflara aktarılmasıdır.

// 'open' kelimesini kullanmamak için 'interface' (arayüz) yapısını temel alıyoruz.
// Böylece buradan doğrudan miras alınabilir.
interface Canli {
    val tur: String
    fun sesCikar()
}

class Kedi(isim: String) : Canli {
    override val tur: String = "Kedi ($isim)"
    
    override fun sesCikar() {
        println("$tur miyavladı.")
    }
}

class Kopek(isim: String) : Canli {
    override val tur: String = "Köpek ($isim)"
    
    override fun sesCikar() {
        println("$tur havladı.")
    }
}

fun main() {

    val hayvanListesi = listOf(
        Kedi("Pamuk"), 
        Kopek("Karabaş"), 
        Kedi("Tekir")
    )

    for (hayvan in hayvanListesi) {
        hayvan.sesCikar()
    }
}
