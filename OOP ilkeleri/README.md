# Kotlin OOP Prensipleri

Bu proje, Nesne Yönelimli Programlama'nın (OOP) dört temel ilkesini Kotlin diliyle basit ve etkili bir şekilde açıklamaktadır. Her bir ilke için teorik bilgi, gerçek hayat benzetmesi ve projede bulunan kodlardan örnekler yer almaktadır.

## Soyutlama (Abstraction)

**Nedir?**
Soyutlama, bir yapının karmaşık iç işleyişini gizleyip sadece temel özelliklerini ve görevlerini ön plana çıkarmaktır. Nesnenin nasıl yaptığına değil, ne yaptığına odaklanırsınız.

**Neden Kullanırız?**
- Karmaşık sistemleri basitleştirmek için.
- Sınıflar için ortak bir sözleşme (kontrat) oluşturmak için.
- Tasarımı, uygulama detaylarından ayırmak için.

**Gerçek Hayat Örneği**
Bir şirketteki Çalışan (`Calisan`) kavramını düşünün. Her çalışanın "işini yapmasını" beklersiniz. Yazılımcının nasıl kod yazdığı veya tasarımcının nasıl çizim yaptığı teknik bir detaydır; önemli olan her ikisinin de birer çalışan olması ve işlerini yapmalarıdır.

**Kotlin Örneği**
*(Bkz: Soyutlama_Abstraction.kt)*

```kotlin
abstract class Calisan {
    val yapilanIsler = mutableListOf<String>()

    // Soyut metod: Her çalışan kendi işini farklı şekilde yapar
    abstract fun isiniYap() 

    fun isEkle(isAdi: String) {
        yapilanIsler.add(isAdi)
    }
}

class Yazilimci : Calisan() {
    override fun isiniYap() {
        println("Kod yazıyor ve mevcut yazılım test ediliyor...")
    }
}
```

**Kod Açıklaması**
- `abstract class Calisan` ortak bir yapı ve kurallar bütünü tanımlar.
- `abstract fun isiniYap()` her alt sınıfın (Yazılımcı, Tasarımcı) bu metodu kendine göre doldurması gerektiğini söyler.
- `isEkle` fonksiyonu tüm çalışanlar için ortak olan ve tekrar yazılmayan bir işlemdir.
- `main()` içerisinde çalışan tipi ne olursa olsun hepsine "işini yap" komutu verebiliriz.

---------------------------------------------------------------------------------------------------------------


## Kapsülleme (Encapsulation)

**Nedir?**
Kapsülleme, verileri (değişkenler) ve bu verilerle yapılan işlemleri (fonksiyonlar) tek bir sınıf içinde bir arada tutmak ve verilere dışarıdan doğrudan erişimi kontrol altına almaktır.

**Neden Kullanırız?**
- Verilerin hatalı değiştirilmesini önlemek için.
- Sınıfın iç detaylarını dış dünyadan gizlemek için.
- Kodun daha güvenli ve bakımı kolay olmasını sağlamak için.

**Gerçek Hayat Örneği**
Bir Banka Hesabı (`BankaHesabi`) düşünün. Bakiyeyi dışarıdan herhangi bir sayı yazarak değiştirememelisiniz. Bunun yerine `paraYatir` gibi kontrollü işlemler kullanarak kurallara uygun şekilde değişiklik yapmalısınız.

**Kotlin Örneği**
*(Bkz: Kapsulleme_Encapsulation.kt)*

```kotlin
class BankaHesabi(baslangicBakiyesi: Int) {
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
    }
}
```

**Kod Açıklaması**
- `var bakiye` üzerindeki özel setter (`set`) metodu, bakiyenin eksiye düşmesini engelleyen bir güvenlik kapısıdır.
- İşlem geçmişi gibi veriler sınıf içinde tutularak işlemlerin kaydı kontrol altında tutulur.
- `main()` fonksiyonunda bakiyeyi doğrudan değiştirmek yerine güvenli metotlar kullanılır.

---------------------------------------------------------------------------------------------------------------


## Kalıtım / Miras (Inheritance)

**Nedir?**
Kalıtım, bir sınıfın başka bir yapıdan (arayüz veya sınıf) özellikleri ve fonksiyonları devralmasıdır. Üst yapı ortak davranışları içerir, alt sınıflar ise bu davranışları genişletir.

**Neden Kullanırız?**
- Aynı kodları tekrar tekrar yazmaktan kaçınmak için.
- Sınıflar arasında hiyerarşik bir ilişki kurmak için.
- Programı yeni özellikler ekleyerek genişletmeyi kolaylaştırmak için.

**Gerçek Hayat Örneği**
Genel bir Canlı (`Canli`) kategorisi düşünün. Kedi ve Köpek birer canlıdır; her ikisinin de bir türü vardır ve her ikisi de ses çıkarır. Ortak özellikleri "Canlı" yapısından alırlar.

**Kotlin Örneği**
*(Bkz: Miras_Inheritance.kt)*

```kotlin
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
```

**Kod Açıklaması**
- `interface Canli` tüm canlıların sahip olması gereken temel özellikleri belirleyen bir taslaktır.
- `class Kedi : Canli` ifadesi, Kedinin Canlı arayüzündeki kurallara tabi olduğunu ve oradaki özellikleri devraldığını gösterir.
- `override` kelimesi ile üst yapıdan gelen özellikler o sınıfa özel hale getirilir.

---------------------------------------------------------------------------------------------------------------


## Polimorfizm (Çok Biçimlilik)

**Nedir?**
Polimorfizm, farklı nesnelerin aynı komuta (aynı fonksiyon ismine) kendilerine has şekilde farklı tepkiler verebilmesidir. "Çok biçimlilik" anlamına gelir.

**Neden Kullanırız?**
- Daha esnek ve tekrar kullanılabilir kod yazmak için.
- Farklı nesneleri ortak bir tipmiş gibi yönetebilmek için.
- Karmaşık if-else kontrol yapılarını azaltmak için.

**Gerçek Hayat Örneği**
Farklı Şekiller (`Sekil`) hayal edin. Bir Kare ve bir Dikdörtgen nesnesine "özelliklerini göster" dediğinizde, her ikisi de kendi alanını hesaplar; komut aynıdır ama sonuç (hesaplama yöntemi) farklıdır.

**Kotlin Örneği**
*(Bkz: Polimorfizm_Polymorphism.kt)*

```kotlin
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
```

**Kod Açıklaması**
- `Sekil` arayüzü tek bir `ozellikleriGoster` metodu tanımlar.
- `Kare` ve `Dikdortgen` bu metodu kendi alan hesaplama mantığına göre doldurur.
- `main()` içinde farklı nesneler `List<Sekil>` içinde tutulur. Döngü sırasında hepsi aynı metodu çağırır ama her biri kendi çıktısını üretir.