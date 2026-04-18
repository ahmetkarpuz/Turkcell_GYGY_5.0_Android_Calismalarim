// Polimorfizm (Çok Biçimlilik): Farklı sınıfların aynı komuta 
// kendilerine has bambaşka tepkiler verebilmesidir.

interface Sekil {
    fun ozellikleriGoster()
}

class Kare(val kenarUzunlugu: Int) : Sekil {
    override fun ozellikleriGoster() {
        println("Ben bir Kareyim. Alanım: ${kenarUzunlugu * kenarUzunlugu}")
    }
}

class Dikdortgen(val kisaKenar: Int, val uzunKenar: Int) : Sekil {
    override fun ozellikleriGoster() {
        println("Ben bir Dikdörtgenim. Alanım: ${kisaKenar * uzunKenar}")
    }
}

fun main() {
    
    val sekillerListesi = listOf(
        Kare(5),
        Dikdortgen(4, 5),
        Kare(10)
    )

    for (sekil in sekillerListesi) {
        sekil.ozellikleriGoster()
    }
}
