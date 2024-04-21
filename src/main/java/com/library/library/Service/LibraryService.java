package com.library.library.Service;

import com.library.library.Entities.*;
import com.library.library.Exception.ErrorMessage;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Repository.*;
import com.library.library.Request.CreationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class LibraryService {
    @Autowired
    AuthorsRepo authorsRepo;
    @Autowired
    BooksRepo booksRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    MembersRepo membersRepo;
    @Autowired
    PublisherRepo publisherRepo;

    public CreationRequest create(CreationRequest creationRequest){
        Genre g = new Genre();
        Genre gg;
        Optional<Genre> op = genreRepo.findBygenreName(creationRequest.getGenre().getGenreName());
        if(op.isEmpty()){
            g.setGenreId(UUID.randomUUID().toString());
            g.setGenreName(creationRequest.getGenre().getGenreName());
            gg = genreRepo.save(g);
        }
        else{
            gg = op.get();
        }


        Publisher p = new Publisher();
        Publisher pp;
        Optional<Publisher> po = publisherRepo.findBypublisherName(creationRequest.getPublisher().getPublisherName());
        if(po.isEmpty()){
            p.setPublisherId(UUID.randomUUID().toString());
            p.setPublisherName(creationRequest.getPublisher().getPublisherName());
            pp = publisherRepo.save(p);
        }
        else{
            pp = po.get();
        }

        Authors a = new Authors();
        Authors aa = new Authors();
        Optional<Authors> ao = authorsRepo.findByauthorName(creationRequest.getAuthors().getAuthorName());
        if(ao.isPresent()){
            aa = ao.get();
        }

        Books b = new Books();
        Optional<Books> findBook = booksRepo.findByTitle(creationRequest.getTitle());
        if(findBook.isEmpty()) {
            if(ao.isEmpty()){
                a.setAuthorId(UUID.randomUUID().toString());
                a.setAuthorName(creationRequest.getAuthors().getAuthorName());
                aa = authorsRepo.save(a);
            }

            b.setBookId(UUID.randomUUID().toString());
            b.setTitle(creationRequest.getTitle());
            b.setIsbn(creationRequest.getIsbn());
            b.setPublishingYear(creationRequest.getPublicationYear());
            b.setQuantity(creationRequest.getQuantity());
            b.setGenre(gg);
            b.setPublisher(pp);
            b.getAuthorsSet().add(aa);
        }
        else if(ao.isEmpty()){
            b = findBook.get();
            a.setAuthorId(UUID.randomUUID().toString());
            a.setAuthorName(creationRequest.getAuthors().getAuthorName());
            aa = authorsRepo.save(a);
            b.getAuthorsSet().add(aa);
        }

        Books bb = booksRepo.save(b);

        creationRequest.setBookId(b.getBookId());
        creationRequest.setGenre(gg);
        creationRequest.setPublisher(pp);
        creationRequest.setAuthors(aa);

        return creationRequest;
    }




    public ResponseEntity<Object> createMem(Members members) {
        Members mm = new Members();
        mm.setName(members.getName());
        mm.setEmail(members.getEmail());
        mm.setAddress(members.getAddress());
        mm.setPhoneNumber(members.getPhoneNumber());

        Members savedMember = membersRepo.save(mm);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    //FIND ALL BOOKS
    public ResponseEntity<Object> getAllBooks(){
        List<Books> books = booksRepo.findAll();
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ErrorMessage("No books in library", "LCT400"));
        }
    }


    //GET BY GENREEiD
    public ResponseEntity<Object> getByGenre(String genreId) throws IdNotFoundException {
        try {
            Genre g = genreRepo.findById(genreId).orElseThrow(() -> new IdNotFoundException("Nah", "Id win"));
            List<Books> books = g.getBooks();
            return ResponseEntity.ok(books);
        }catch(IdNotFoundException e){
            String errorMsg = "Id Not found";
            String reasonCode = "LCT400";
            ErrorMessage er = new ErrorMessage();
            er.setErrorMsg(errorMsg);
            er.setReasonCode(reasonCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
    }

    //GET BY GENRE NAME
    public ResponseEntity<Object> getByGenreName(String genreName){
        Optional<Genre> g = genreRepo.findBygenreName(genreName);
        if(g.isPresent()) {
            Genre gg = g.get();
            List<Books> books = gg.getBooks();
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage
                    ("No books with provided genre","LCT400"));
        }
    }



    //GET ALL MEMBERS
    public ResponseEntity<Object> getAllMem() {
        List<Members> m = membersRepo.findAll();
        if (!m.isEmpty()) {
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage
                    ("No members present","LCT400"));
        }
    }
}
