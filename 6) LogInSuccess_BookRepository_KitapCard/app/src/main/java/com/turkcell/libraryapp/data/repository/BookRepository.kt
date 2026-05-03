package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest

import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Örnek bir resim yükleme fonksiyonu
suspend fun uploadImageToSupabase(fileName: String, imageBytes: ByteArray): Result<String> = runCatching {
    withContext(Dispatchers.IO) {
        // "book_covers" yerine Supabase'de oluşturduğun bucket'ın adını yaz
        val bucket = supabase.storage.from("book_covers")

        // Resmi Supabase'e yüklüyoruz
        bucket.upload(fileName, imageBytes) {
            upsert = true
        }

        // Yüklenen resmin internette gösterilebilmesi için public URL'sini alıyoruz
        val publicUrl = bucket.publicUrl(fileName)
        return@withContext publicUrl
    }
}
class BookRepository {
    
    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
                .select()
                .decodeList<Book>()
    }

    suspend fun getBookById(id:String): Result<Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id",id) } }
            .decodeSingle<Book>()
    }

    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].insert(book)
    }


    //update
    suspend fun updateBook(id: String, updatedBook: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].update(updatedBook) {
            filter { eq("id", id) } // Güncellenecek kitabı ID'sine göre bulur
        }
    }

    //kitap sil
    suspend fun deleteBook(id: String): Result<Unit> = runCatching {
        supabase.postgrest["books"].delete {
            filter { eq("id", id) } 
        }
    }

    //ktiap arama
    suspend fun searchBooksByTitle(searchQuery: String): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select { 
                filter { ilike("title", "%$searchQuery%") } 
            }
            .decodeList<Book>()
    }
}
    // ÖDEV 2: BookRepository Güncelleme, silme, arama fonksiyonlarını tanımla.

