package com.library.library.Controller;

import com.library.library.DTO.TransactionsDTO;
import com.library.library.Entities.Authors;
import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import com.library.library.Request.TransactionRequest;
import com.library.library.Service.LibraryService;
import com.library.library.Request.CreationRequest;
import com.library.library.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    LibraryService libraryService;
    @Autowired
    TransactionService transactionService;


    //TO ADD BOOKS,AUTHORS,PUBLISHERS,GENRE INTO DB
    @PostMapping("/create")
    public CreationRequest.CreationDto creation(@RequestBody CreationRequest.CreationDto creationDto){
        return libraryService.create(creationDto);
    }


    //TO ADD MEMBERS INTO DB
    @PostMapping("/members")
    public Members createMembers(@RequestBody Members members){
        return libraryService.createMem(members);
    }


    @GetMapping("/book/{bookName}")
    public Authors getByBookName(@PathVariable("bookName") String title){
        return libraryService.findByBook(title);
    }

    //GET AUTHOR
    @GetMapping("/authors")
    public Authors getAuthor(@RequestParam long bookId){
        return libraryService.getAuthor(bookId);
    }

    //GET BY GENRES
//    @GetMapping("/genre")
//    public List<Books> getByGenreId(
//            @RequestParam(value = "genreId", required = false) Long genreId,
//            @RequestParam(value = "GenreName", required = false) String genreName
//    ){
//        if(genreId != null) {
//            return this.libraryService.getByGenre(genreId);
//        }
//        else if(genreName!=null){
//            return libraryService.getByGenreName(genreName);
//        }
//        else{
//            return null;
//        }
//    }

    @GetMapping("/gen/{genId}")
    public List<Books> getByGenre(@PathVariable("genId") long genreId){
        return this.libraryService.getByGenre(genreId);
    }

    @GetMapping("/genname/{genname}")
    public List<Books> getByGenreName(@PathVariable("genname") String genreName){
        return libraryService.getByGenreName(genreName);
    }
    //TO GET ALL THE MEMBERS
    @GetMapping("/getmem")
    public List<Members> getAllMembers(){
        return libraryService.getAllMem();
    }


    //TO ADD TRANSACTIONS INTO DB
    @PostMapping("/transaction")
    public String borrowBook(@RequestBody TransactionRequest transactionRequest){
        String type = transactionRequest.getTransactionType();
        if(type.equalsIgnoreCase("borrow")) {
            return transactionService.borrow(transactionRequest);
        }
        else if(type.equalsIgnoreCase("return")){
            return transactionService.returnBook(transactionRequest);
        }
        else{
            return "invalid type";
        }
    }


//    @GetMapping("/all")
//    public List<TransactionsDTO> getAll(){
//        return transactionService.getAllTransactions();
//    }
//
//    @GetMapping("/books/{bookId}")
//    public List<TransactionsDTO> getByBook(@PathVariable("bookId") Books books){
//        return transactionService.getByBookId(books);
//    }
//
//    @GetMapping("/members/{memberId}")
//    public List<TransactionsDTO> getByMember(@PathVariable("memberId") Members members){
//        return transactionService.getByMemberId(members);
//    }
//
//    @GetMapping("/membook/{memid}/{bookid}")
//    public  List<TransactionsDTO> getByMemBook(@PathVariable("memid") Members members, @PathVariable("bookid") Books books){
//        return transactionService.getByMemberAndBooks(members,books);
//    }

    // TO GET ALL TRANSACTIONS
    @GetMapping("/memberbooks")
    public List<TransactionsDTO> getTransactions(
            @RequestParam(required = false) Members members,
            @RequestParam(required = false) Books books
    ){
        if(members!=null && books!=null){
            return transactionService.getByMemberAndBooks(members,books);
        }
        else if(members != null){
            return transactionService.getByMemberId(members);
        }
        else if(books!=null){
            return transactionService.getByBookId(books);
        }
        else{
            return transactionService.getAllTransactions();
        }
    }
}

