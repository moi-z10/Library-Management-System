package com.library.library.Service;


import com.library.library.DTO.TransactionsDTO;
import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import com.library.library.Repository.BooksRepo;
import com.library.library.Repository.MembersRepo;
import com.library.library.Repository.TransactionsRepo;
import com.library.library.Request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String borrow(TransactionRequest transactionRequest){
        long bookId = transactionRequest.getBookId();
        long memberId = transactionRequest.getMemberId();
        String type = transactionRequest.getTransactionType();

        Members mmm = new Members();
        mmm.setMemberId(memberId);

        Books bbb = new Books();
        bbb.setBookId(bookId);

        Optional<Transactions> ttt = transactionsRepo.findByMembersAndBooks(mmm, bbb);
        if(ttt.isPresent()){
            return "you have taken the same book already";
        }
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to today's date
        calendar.setTime(new Date());

        // Add 2 weeks to the current date
        calendar.add(Calendar.WEEK_OF_YEAR, 2);

        // Get the dueDate as a Date object
        Date dueDate = calendar.getTime();

        Transactions trans = new Transactions();
        if(type.equalsIgnoreCase("borrow")){
            Optional<Books> bookPresent = booksRepo.findById(bookId);
            if(bookPresent.isPresent()) {
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
                        return "MEMBER NOT PRESENT";
                    }
                } else {
                    return "BOOK NOT AVAILABLE";
                }
            }
            else {
                return "BOOK ID NOT PRESENT";
            }
        }
        return "SUCCESSFULL";
    }
    public String returnBook(TransactionRequest transactionRequest){
        long bookId= transactionRequest.getBookId();
        long memberId= transactionRequest.getMemberId();
        String type = transactionRequest.getTransactionType();

        Calendar cal = Calendar.getInstance();
        Date returnDate = cal.getTime();


        Members mmm = new Members();
        mmm.setMemberId(memberId);

        Books bbb = new Books();
        bbb.setBookId(bookId);

        Optional<Transactions> ret = transactionsRepo.findByMembersAndBooks(mmm,bbb);
        if(ret.isEmpty()){
            return "invalid book return";
        }


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
        return "BOOK SUCCESSFULLY RETURNED";
    }

    public List<TransactionsDTO> getAllTransactions(){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findAll();
        for(Transactions tr : trans){
            TransactionsDTO tt = new TransactionsDTO();
            tt.setTransactionId(tr.getTransactionId());
            tt.setTransactionType(tr.getTransactionType());
            tt.setReturnDate(tr.getReturnDate());
            tt.setDueDate(tr.getDueDate());
            tt.setBookId(tr.getBooks().getBookId());
            tt.setMemberId(tr.getMembers().getMemberId());
            l1.add(tt);
        }
        return l1;
    }
    public List<TransactionsDTO> getByBookId(Books bookid){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findByBooks(bookid);
        for(Transactions tr : trans){
            TransactionsDTO tt = new TransactionsDTO();
            tt.setTransactionId(tr.getTransactionId());
            tt.setTransactionType(tr.getTransactionType());
            tt.setReturnDate(tr.getReturnDate());
            tt.setDueDate(tr.getDueDate());
            tt.setBookId(tr.getBooks().getBookId());
            tt.setMemberId(tr.getMembers().getMemberId());
            l1.add(tt);
        }
        return l1;
    }

    public List<TransactionsDTO> getByMemberId(Members memberId){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findByMembers(memberId);
        for(Transactions tr : trans){
            TransactionsDTO tt = new TransactionsDTO();
            tt.setTransactionId(tr.getTransactionId());
            tt.setTransactionType(tr.getTransactionType());
            tt.setReturnDate(tr.getReturnDate());
            tt.setDueDate(tr.getDueDate());
            tt.setBookId(tr.getBooks().getBookId());
            tt.setMemberId(tr.getMembers().getMemberId());
            l1.add(tt);
        }
        return l1;
    }

    public List<TransactionsDTO> getByMemberAndBooks(Members memberId, Books bookId){
        List<TransactionsDTO> l1 = new ArrayList<>();
        List<Transactions> trans = transactionsRepo.findListByMembersAndBooks(memberId,bookId);
        for(Transactions tr : trans){
            TransactionsDTO tt = new TransactionsDTO();
            tt.setTransactionId(tr.getTransactionId());
            tt.setTransactionType(tr.getTransactionType());
            tt.setReturnDate(tr.getReturnDate());
            tt.setDueDate(tr.getDueDate());
            tt.setBookId(tr.getBooks().getBookId());
            tt.setMemberId(tr.getMembers().getMemberId());
            l1.add(tt);
        }
        return l1;
    }
}
