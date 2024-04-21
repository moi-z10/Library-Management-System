package com.library.library.Service;


import com.library.library.DTO.TransactionsDTO;
import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import com.library.library.Exception.ErrorMessage;
import com.library.library.Exception.IdAlreadyExists;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Repository.BooksRepo;
import com.library.library.Repository.MembersRepo;
import com.library.library.Repository.TransactionsRepo;
import com.library.library.Request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    BooksRepo booksRepo;
    @Autowired
    MembersRepo membersRepo;
    @Autowired
    TransactionsRepo transactionsRepo;

    public ResponseEntity<Object> borrow(TransactionRequest transactionRequest) throws IdNotFoundException {
        String bookId = transactionRequest.getBookId();
        String memberId = transactionRequest.getMemberId();
        String type = transactionRequest.getTransactionType();

        Members mmm = new Members();
        mmm.setMemberId(memberId);

        Books bbb = new Books();
        bbb.setBookId(bookId);
        try {
            if (type.equalsIgnoreCase("borrow")) {
                Optional<Transactions> ttt = transactionsRepo.findByMembersAndBooks(mmm, bbb);
                if (ttt.isPresent()) {
                    throw new IdAlreadyExists("hehe", "hoho");
                }
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(new Date());

                calendar.add(Calendar.WEEK_OF_YEAR, 2);

                Date dueDate = calendar.getTime();

                Transactions trans = new Transactions();
                Optional<Books> bookPresent = booksRepo.findById(bookId);
                if (bookPresent.isPresent()) {
                    Books b = bookPresent.get();
                    int quant = b.getQuantity();
                    if (quant > 0) {
                        Optional<Members> memPresent = membersRepo.findById(memberId);
                        if (memPresent.isPresent()) {
                            Members mem = memPresent.get();
                            b.setQuantity(quant - 1);
                            trans.setTransactionType(type);
                            trans.setDueDate(dueDate);
                            Books bb = booksRepo.save(b);
                            Members mm = membersRepo.save(mem);
                            trans.setBooks(bb);
                            trans.setMembers(mm);
                            transactionsRepo.save(trans);
                        } else {
                            throw new IdNotFoundException("member not present", "LCT400");
                        }
                    } else {
                        throw new IdNotFoundException("Book not there", "LCT400");
                    }
                } else {
                    throw new IdNotFoundException("BookId invalid", "LCT400");
                }
            }
        } catch (IdNotFoundException e) {
             String errorMsg = "Id Not found";
             String reasonCode = "LCT400";
             ErrorMessage er = new ErrorMessage();
             er.setErrorMsg(errorMsg);
             er.setReasonCode(reasonCode);
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
         } catch (IdAlreadyExists f) {
            String errorMsg = "You have taken the same book already";
            String reasonCode = "LCT400";
            ErrorMessage er = new ErrorMessage();
            er.setErrorMsg(errorMsg);
            er.setReasonCode(reasonCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
        return ResponseEntity.ok("SUCCESSFUL TRANSACTION");
    }


    public ResponseEntity<Object> returnBook(TransactionRequest transactionRequest){
        String bookId= transactionRequest.getBookId();
        String memberId= transactionRequest.getMemberId();
        String type = transactionRequest.getTransactionType();

        Calendar cal = Calendar.getInstance();
        Date returnDate = cal.getTime();


        Members mmm = new Members();
        mmm.setMemberId(memberId);

        Books bbb = new Books();
        bbb.setBookId(bookId);

        Optional<Transactions> ret = transactionsRepo.findByMembersAndBooks(mmm,bbb);
        if(ret.isPresent()){
            Transactions rt = ret.get();
            Transactions newTrans = new Transactions();
            Books br = rt.getBooks();

            int quant = br.getQuantity();
            br.setQuantity(quant+1);
            Books savingBooks = booksRepo.save(br);
            newTrans.setTransactionType(type);
            newTrans.setDueDate(rt.getDueDate());
            newTrans.setBooks(savingBooks);
            newTrans.setMembers(rt.getMembers());
            newTrans.setReturnDate(returnDate);
            transactionsRepo.save(newTrans);
            return ResponseEntity.ok("SUCCESSFULLY RETURNED THE BOOK");
        }
        else{
            String errorMsg = "Id Not found";
            String reasonCode = "LCT400";
            ErrorMessage er = new ErrorMessage();
            er.setErrorMsg(errorMsg);
            er.setReasonCode(reasonCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
    }

    public ResponseEntity<Object> getAllTransactions(){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findAll();
        if(!trans.isEmpty()) {
            for (Transactions tr : trans) {
                TransactionsDTO tt = new TransactionsDTO();
                tt.setTransactionId(tr.getTransactionId());
                tt.setTransactionType(tr.getTransactionType());
                tt.setReturnDate(tr.getReturnDate());
                tt.setDueDate(tr.getDueDate());
                tt.setBookId(tr.getBooks().getBookId());
                tt.setMemberId(tr.getMembers().getMemberId());
                l1.add(tt);
            }
            return ResponseEntity.ok(l1);
        }
        else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorMessage(
                    "No Transaction to show","LCT400"));
        }
    }
    public ResponseEntity<Object> getByBookId(String bookid){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findByBooksBookId(bookid);
        if(!trans.isEmpty()) {
            for (Transactions tr : trans) {
                TransactionsDTO tt = new TransactionsDTO();
                tt.setTransactionId(tr.getTransactionId());
                tt.setTransactionType(tr.getTransactionType());
                tt.setReturnDate(tr.getReturnDate());
                tt.setDueDate(tr.getDueDate());
                tt.setBookId(tr.getBooks().getBookId());
                tt.setMemberId(tr.getMembers().getMemberId());
                l1.add(tt);
            }
            return ResponseEntity.ok(l1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("No Transaction to show","LCT400"));
        }
    }

    public ResponseEntity<Object> getByMemberId(String memberId){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findByMembersMemberId(memberId);
        if(!trans.isEmpty()) {
            for (Transactions tr : trans) {
                TransactionsDTO tt = new TransactionsDTO();
                tt.setTransactionId(tr.getTransactionId());
                tt.setTransactionType(tr.getTransactionType());
                tt.setReturnDate(tr.getReturnDate());
                tt.setDueDate(tr.getDueDate());
                tt.setBookId(tr.getBooks().getBookId());
                tt.setMemberId(tr.getMembers().getMemberId());
                l1.add(tt);
            }
            return ResponseEntity.ok(l1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("No Transaction to show","LCT400"));
        }
    }

    public ResponseEntity<Object> getByMemberAndBooks(String memberId, String bookId){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findByMembers_MemberIdAndBooks_BookId(memberId,bookId);
        if(!trans.isEmpty()) {
            for (Transactions tr : trans) {
                TransactionsDTO tt = new TransactionsDTO();
                tt.setTransactionId(tr.getTransactionId());
                tt.setTransactionType(tr.getTransactionType());
                tt.setReturnDate(tr.getReturnDate());
                tt.setDueDate(tr.getDueDate());
                tt.setBookId(tr.getBooks().getBookId());
                tt.setMemberId(tr.getMembers().getMemberId());
                l1.add(tt);
            }
            return ResponseEntity.ok(l1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("No Transaction to show","LCT400"));
        }
    }
}
