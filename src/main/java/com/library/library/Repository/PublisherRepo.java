package com.library.library.Repository;

import com.library.library.Entities.Genre;
import com.library.library.Entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PublisherRepo extends JpaRepository<Publisher, String> {
    Optional<Publisher> findBypublisherName(String publisherName);
}
