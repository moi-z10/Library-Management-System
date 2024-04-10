package com.library.library.Service;

import com.library.library.Controller.RandomIds;
import com.library.library.Entities.*;
import com.library.library.Repository.*;
import com.library.library.Request.CreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibraryService {
    @Autowired
    AuthorsRepo authorsRepo;
    @Autowired
    BooksRepo booksRepo;
//    @Autowired
//    BookAuthorsRepo bookAuthorsRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    MembersRepo membersRepo;
    @Autowired
    PublisherRepo publisherRepo;

    public CreationRequest.CreationDto create(CreationRequest.CreationDto creationDto){
        Genre g = new Genre();
        Genre gg;
        Optional<Genre> op = genreRepo.findBygenreName(creationDto.getGenre().getGenreName());
        if(op.isEmpty()){
            g.setGenreId(UUID.randomUUID().toString());
            g.setGenreName(creationDto.getGenre().getGenreName());
            gg = genreRepo.save(g);
        }
        else{
            gg = op.get();
        }


        Publisher p = new Publisher();
        Publisher pp;
        Optional<Publisher> po = publisherRepo.findBypublisherName(creationDto.getPublisher().getPublisherName());
        if(po.isEmpty()){
            p.setPublisherId(UUID.randomUUID().toString());
            p.setPublisherName(creationDto.getPublisher().getPublisherName());
            pp = publisherRepo.save(p);
        }
        else{
            pp = po.get();
        }

        Authors a = new Authors();
        Authors aa = new Authors();
        Optional<Authors> ao = authorsRepo.findByauthorName(creationDto.getAuthors().getAuthorName());
        if(ao.isPresent()){
            aa = ao.get();
        }

        Books b = new Books();
        Optional<Books> findBook = booksRepo.findBytitle(creationDto.getTitle());
        if(findBook.isEmpty()) {
            if(ao.isEmpty()){

                a.setAuthorId(UUID.randomUUID().toString());
                a.setAuthorName(creationDto.getAuthors().getAuthorName());
                aa = authorsRepo.save(a);
            }

            b.setBookId(UUID.randomUUID().toString());
            b.setTitle(creationDto.getTitle());
            b.setIsbn(creationDto.getIsbn());
            b.setPublishingYear(creationDto.getPublicationYear());
            b.setQuantity(creationDto.getQuantity());
            b.setGenre(gg);
            b.setPublisher(pp);
            b.getAuthorsSet().add(aa);
        }
        else if(ao.isEmpty()){
            b = findBook.get();
            a.setAuthorId(UUID.randomUUID().toString());
            a.setAuthorName(creationDto.getAuthors().getAuthorName());
            aa = authorsRepo.save(a);
            b.getAuthorsSet().add(aa);
        }


        Books bb = booksRepo.save(b);



        creationDto.setBookId(bb.getBookId());
        creationDto.setGenre(gg);
        creationDto.setPublisher(pp);
        creationDto.setAuthors(aa);

        return creationDto;
    }




    public Members createMem(Members members) {
        Members mm = new Members();
        mm.setName(members.getName());
        mm.setEmail(members.getEmail());
        mm.setAddress(members.getAddress());
        mm.setPhoneNumber(members.getPhoneNumber());
        return membersRepo.save(mm);
    }

    //FIND ALL BOOKS
    public List<Books> getAllBooks(){
        List<Books> b = booksRepo.findAll();
        List<Books> addBooks = new ArrayList<>();
        for(Books book:b){
            Books bb = new Books();
            bb.setBookId(book.getBookId());
            bb.setTitle(book.getTitle());
            bb.setIsbn(book.getIsbn());
            bb.setPublishingYear(book.getPublishingYear());
            bb.setQuantity(book.getQuantity());
            addBooks.add(bb);
        }
        return addBooks;
    }


    //GET AUTHORS FROM BOOKiD
//    public Set<Authors> getAuthor(UUID bookId){
//        Books getBook = booksRepo.findById(bookId).get();
//        Set<Authors> authors = new HashSet<>();
//        authors.addAll(getBook.getAuthorsSet());
//        return  authors;
//    }
    //GET BY GENREEiD
//    public List<Books> getByGenre(UUID genreId){
//        Genre g = genreRepo.findById(genreId).get();
//        return g.getBooks();
//    }

    //GET BY GENRE NAME
    public List<Books> getByGenreName(String genreName){
        Optional<Genre> g = genreRepo.findBygenreName(genreName);
        Genre gg = g.get();
        return gg.getBooks();
    }

    public Set<Authors> findByBook(String title){
        Books book = booksRepo.findBytitle(title).get();
        Set<Authors> authors = book.getAuthorsSet();
        return authors;
    }

    //GET ALL MEMBERS
    public List<Members> getAllMem(){
        List<Members> l1 = new ArrayList<>();
        List<Members> m = membersRepo.findAll();
        for(Members mm :m){
            Members add = new Members();
            add.setMemberId(mm.getMemberId());
            add.setName(mm.getName());
            add.setEmail(mm.getEmail());
            add.setPhoneNumber(mm.getPhoneNumber());
            add.setAddress(mm.getAddress());
            l1.add(add);
        }
        return l1;
    }


}
