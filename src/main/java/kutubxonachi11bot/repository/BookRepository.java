package kutubxonachi11bot.repository;

import kutubxonachi11bot.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


//    List<Book> findBooks(@Param("bookNameOrAuthor") String bookNameOrAuthor);

    @Query(value = "SELECT * FROM book WHERE book.name ILIKE :bookNameOrAuthor OR book.author ILIKE :bookNameOrAuthor",
            countQuery = "SELECT count(*) FROM book WHERE book.name ILIKE :bookNameOrAuthor OR book.author ILIKE :bookNameOrAuthor",
            nativeQuery = true)
    Page<Book> findBooksToPage(@Param("bookNameOrAuthor") String bookNameOrAuthor, Pageable pageable);

    @Query(value = "SELECT count(*) FROM book WHERE book.name ILIKE :bookNameOrAuthor OR book.author ILIKE :bookNameOrAuthor",
            nativeQuery = true)
    Integer countBooks(@Param("bookNameOrAuthor") String bookNameOrAuthor);
}
