package com.library.library.Service;

import com.library.library.Controller.RandomIds;
import com.library.library.Entities.*;
import com.library.library.Repository.*;
import com.library.library.Request.CreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    @Autowired
    AuthorsRepo authorsRepo;
    @Autowired
    BooksRepo booksRepo;
    @Autowired
    BookAuthorsRepo bookAuthorsRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    MembersRepo membersRepo;
    @Autowired
    PublisherRepo publisherRepo;

    public CreationRequest.CreationDto create(CreationRequest.CreationDto creationDto){
        RandomIds rd = new RandomIds();
        Genre g = new Genre();
        Genre gg;
        Optional<Genre> op = genreRepo.findBygenreName(creationDto.getGenreName());
        if(op.isEmpty()){
            long id = rd.getRandom();
            g.setGenreId(id);
            g.setGenreName(creationDto.getGenreName());
            gg = genreRepo.save(g);
        }
        else{
            gg = op.get();
        }


        Publisher p = new Publisher();
        Publisher pp;
        Optional<Publisher> po = publisherRepo.findBypublisherName(creationDto.getPublisherName());
        if(po.isEmpty()){
            long id = rd.getRandom();
            p.setPublisherId(id);
            p.setPublisherName(creationDto.getPublisherName());
            pp = publisherRepo.save(p);
        }
        else{
            pp = po.get();
        }

        Authors a = new Authors();
        Authors aa;
        Optional<Authors> ao = authorsRepo.findByauthorName(creationDto.getAuthorName());
        if(ao.isEmpty()){
            long id = rd.getRandom();
            a.setAuthorId(id);
            a.setAuthorName(creationDto.getAuthorName());
            aa = authorsRepo.save(a);
        }
        else{
            aa = ao.get();
        }


        Books b = new Books();
        long bookId = rd.getRandom();
        b.setBookId(bookId);
        b.setTitle(creationDto.getTitle());
        b.setAuthor(creationDto.getAuthorName());
        b.setIsbn(creationDto.getIsbn());
        b.setPublishingYear(creationDto.getPublicationYear());
        b.setQuantity(creationDto.getQuantity());
        b.setGenre(gg);
        b.setPublisher(pp);
        Books bb = booksRepo.save(b);

        BookAuthors ba = new BookAuthors();
        ba.setBooks(bb);
        ba.setAuthors(aa);
        bookAuthorsRepo.save(ba);

        creationDto.setBookId(bb.getBookId());
        creationDto.setGenreId(gg.getGenreId());
        creationDto.setPublisherId(pp.getPublisherId());
        creationDto.setAuthorId(aa.getAuthorId());

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
    //GET AUTHORS FROM BOOKiD
    public Authors getAuthor(long bookId){
        Books getBook = booksRepo.findById(bookId).get();
        BookAuthors getAuthor = getBook.getBookAuthors();
        return getAuthor.getAuthors();

    }
    //GET BY GENREEiD
    public List<Books> getByGenre(long genreId){
        Genre g = genreRepo.findById(genreId).get();
        return g.getBooks();
    }

    //GET BY GENRE NAME
    public List<Books> getByGenreName(String genreName){
        Optional<Genre> g = genreRepo.findBygenreName(genreName);
        Genre gg = g.get();
        return gg.getBooks();
    }

    public Authors findByBook(String title){
        Books book = booksRepo.findBytitle(title);
        BookAuthors getBook = book.getBookAuthors();
        return getBook.getAuthors();
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
