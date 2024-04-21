package com.library.library.Controller;

import com.library.library.DTO.TransactionsDTO;
import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Request.TransactionRequest;
import com.library.library.Service.LibraryService;
import com.library.library.Request.CreationRequest;
import com.library.library.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CreationRequest creation(@RequestBody CreationRequest creationDto){
        return libraryService.create(creationDto);
    }


    //TO ADD MEMBERS INTO DB
    @PostMapping("/members")
    public ResponseEntity<Object> createMembers(@RequestBody Members members){
        return libraryService.createMem(members);
    }

    //GET ALL BOOKS
    @GetMapping("/getbooks")
    public ResponseEntity<Object> getAllBok(){
        return libraryService.getAllBooks();
    }



    @GetMapping("/gen/{genId}")
    public ResponseEntity<Object> getByGenre(@PathVariable("genId") String genreId) throws IdNotFoundException {
        return this.libraryService.getByGenre(genreId);
    }

    @GetMapping("/genname/{genname}")
    public ResponseEntity<Object> getByGenreName(@PathVariable("genname") String genreName){
        return libraryService.getByGenreName(genreName);
    }


    //TO GET ALL THE MEMBERS
    @GetMapping("/getmem")
    public ResponseEntity<Object> getAllMembers(){
        return libraryService.getAllMem();
    }


    //TO ADD TRANSACTIONS INTO DB
    @PostMapping("/transaction")
    public ResponseEntity<Object> borrowBook(@RequestBody TransactionRequest transactionRequest) throws IdNotFoundException {
        String type = transactionRequest.getTransactionType();
        if(type.equalsIgnoreCase("borrow")) {
            return transactionService.borrow(transactionRequest);
        }
        else if(type.equalsIgnoreCase("return")){
            return transactionService.returnBook(transactionRequest);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No method found for given type");
        }
    }



    // TO GET ALL TRANSACTIONS
    @GetMapping("/memberbooks")
    public ResponseEntity<Object> getTransactions(
            @RequestParam(required = false) String members,
            @RequestParam(required = false) String books
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

