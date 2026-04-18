// Bank.kt dosyası oluşturalım.
// Bir bankacılık uygulaması (OOP yok)
// Min 5. adet bankacılık uygulamasında olması gerektiğini düşündüğünüz fonksiyonu tanımlayalım.
// Main fonksiyonunda da bu fonksiyonları çağırıp test edelim.

//Ahmet KARPUZ
//KUllanıcıdan veri alabilmek için readline fonksiyonunu kullandım. Scanner metodu da varmış ama oop olduğundan dolayı onu kullanmadım


var bakiye=0.0
var hesapSahibiAdi="Ahmet Karpuz"
var hesapNo="TR00 1234 5678 90"

fun karsilamaMesaji() {
    println("\n\n                   --------------------GNCYTNK Bankası--------------------")
    println("\n                     Hoşgeldiniz, $hesapSahibiAdi")
}

fun bakiyeGoster() {
    println("Bakiyeniz: $bakiye TL")
}

fun paraYatir(miktar: Double) {
    if (miktar > 0) 
    {
        bakiye += miktar
        println("$miktar TL yatırıldı.")
        bakiyeGoster()
    } 
    else 
    {
        println("YALNIZCA POZİTİF SAYILAR YATIRILABİLİR!!!")
    }
}

fun paraCek(miktar: Double) {
    if (miktar > 0 && miktar <= bakiye) 
    {
        bakiye -= miktar
        println("$miktar TL çekildi.")
        bakiyeGoster()
    } 
    else 
    {
        println("Bakiyeden fazla bir miktar girdiniz!!!")
    }
}

fun hesapDetaylari() {
    println("Sahip: $hesapSahibiAdi")
    println("IBAN : $hesapNo")
    println("Bakiye: $bakiye TL")
}

fun paraTransferi(miktar: Double) {
    if (miktar > 0 && miktar <= bakiye) 
    {
        bakiye -= miktar
        println("$miktar TL gönderildi.")
        bakiyeGoster()
    } 
    else 
    {
        println("Bakiyeden fazla bir miktar girdiniz!!!")
    }
}

fun main() {
    karsilamaMesaji()

    while (true) 
    {
        println("\nYapmak istediğiniz işlemi seçin: ")
        println("1)Bakiye Göster")
        println("2)Para Yatır")
        println("3)Para Çek")
        println("4)Hesap Detayları")
        println("5)Para Transferi")
        println("0)Çıkış")
        print("Seçiminiz: ")
        
        val secim= readLine()?.toIntOrNull() //toIntOrNull kullanmadığımız durumda kullanıcı harf girdiğinde program hata alıp kapanıyor
        if (secim == 1) 
        {
            bakiyeGoster()
        } 
        else if (secim == 2) 
        {
            print("Yatırılacak tutar: ")
            // ?: 0.0 kısmı, eğer kullanıcı geçersiz bir şey yazarsa tutarı 0 kabul et demek
            val miktar = readLine()?.toDoubleOrNull() ?: 0.0
            paraYatir(miktar)
        } 
        else if (secim == 3) 
        {
            print("Çekilecek tutar: ")
            val miktar = readLine()?.toDoubleOrNull() ?: 0.0
            paraCek(miktar)
        } 
        else if (secim == 4) 
        {
            hesapDetaylari()
        }
        else if (secim == 5) 
        {
            print("Gönderilecek tutar: ")
            val miktar = readLine()?.toDoubleOrNull() ?: 0.0
            paraTransferi(miktar)
        } 
        else if (secim == 0) 
        {
            println("Çıkış yapılıyor...")
            break
        }
        else 
        {
            println("Yalnızca 0-5 arasında bir sayı girebilrizsiniz!!!")
        }
    }
}
