package com.library.library.Repository;

import com.library.library.Entities.Publisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PublisherRepoTest {

    @Test
    void findBypublisherName() {
        PublisherRepo publisherRepo = Mockito.mock(PublisherRepo.class);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Sandra");
        publisher.setPublisherId("112");

        when(publisherRepo.findBypublisherName(publisher.getPublisherName()))
                .thenReturn(Optional.of(publisher));

        Optional<Publisher> optionalPublisher = publisherRepo.findBypublisherName
                (publisher.getPublisherName());

        assertEquals(publisher,optionalPublisher.get());
    }
}