package com.library.library.Repository;

import com.library.library.Entities.Genre;
import com.library.library.Entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepo extends JpaRepository<Publisher,Long> {
    Optional<Publisher> findBypublisherName(String publisherName);
}
