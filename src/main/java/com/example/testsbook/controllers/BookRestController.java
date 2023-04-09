package com.example.testsbook.controllers;

import com.example.testsbook.DTO.BookDTO;
import com.example.testsbook.exceptionBook.*;
import com.example.testsbook.exceptionPerson.PeopleNotFoundException;
import com.example.testsbook.models.Book;
import com.example.testsbook.service.BookService;
import com.example.testsbook.service.RegistrationService;
import com.example.testsbook.util.Response;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookService bookService;
    private final BookDTO bookDTO;
    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;

    @Autowired
    public BookRestController(BookService bookService, BookDTO bookDTO, ModelMapper modelMapper, RegistrationService registrationService) {
        this.bookService = bookService;
        this.bookDTO = bookDTO;
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
    }


    @GetMapping()
    public List<BookDTO> findBooks() {
        List<BookDTO> books = bookService.findBooks().stream().map(this::convertToBookDTO).collect(Collectors.toList());
        if(books == null || books.size() == 0){
            throw new PeopleNotFoundException();
        }
        return books;
    }

    @GetMapping("/{id}")
    public BookDTO findBook(@PathVariable("id") Long id) {
        BookDTO bookDTO = null;
        Book book  = bookService.findBook(id);
        if(book != null) {
            bookDTO = convertToBookDTO(book);
        }else{
            throw new BookNotFoundException();
        }
        return bookDTO;
    }

    @PostMapping()
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid Book book,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder bld = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                bld.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            });
            throw new BookNotRegisteredException(bld.toString());
        }
        Book newBook = bookService.saveCreatedBook(book);
        return ResponseEntity.accepted().body(convertToBookDTO(newBook));
    }

    @PostMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook( @RequestBody @Valid Book updatedBook,
                                               @PathVariable Long id,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder bld = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                bld.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            });
            throw new BookNotUpdatedException(bld.toString());
        }
        Book book = bookService.findBook(id);
        if(book == null){
            throw new BookNotFoundException();
        }
        Book newBook = bookService.updateBook(id,updatedBook);
        return ResponseEntity.accepted().body(convertToBookDTO(newBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable("id") Long id){
        Book book = bookService.findBook(id);
        if(book == null){
            throw new BookNotDeletedException();
        }
        bookService.delete(id);
        return ResponseEntity.accepted().body(new Response("Удалено", new Date()));
    }

    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }


    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException e) {
        BookErrorResponse response = new BookErrorResponse(
                "Книга не найдена",
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BooksNotFoundException e) {
        BookErrorResponse response = new BookErrorResponse(
                "Список пуст",
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotRegisteredException e) {
        BookErrorResponse response = new BookErrorResponse(
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotUpdatedException e) {
        BookErrorResponse response = new BookErrorResponse(
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotDeletedException e) {
        BookErrorResponse response = new BookErrorResponse(
                "Удаляемой книги нет в списке",
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
