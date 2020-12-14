package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfRepo extends JpaRepository<Bookshelf, Long> {
}
