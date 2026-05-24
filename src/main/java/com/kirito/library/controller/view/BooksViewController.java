package com.kirito.library.controller.view;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.service.BookService;
import com.kirito.library.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksViewController {

    private final BookService bookService;
    private final PersonService personService;

    @GetMapping
    public String index(
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(name = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear,
            Model model
    ) {
        model.addAttribute("books", bookService.getAllBooks(page, booksPerPage, sortByYear));
        model.addAttribute("sortByYear", sortByYear);
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new BookRequest(null, null, null));
        return "books/new";
    }

    @PostMapping
    public String createBook(
            @Valid @ModelAttribute("book") BookRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookService.createBook(request);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String showBook(@PathVariable UUID bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        model.addAttribute("people", personService.getAllPersons());
        return "books/show";
    }

    @GetMapping("/{bookId}/edit")
    public String editBook(@PathVariable UUID bookId, Model model) {
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", bookService.getBookById(bookId));
        return "books/edit";
    }

    @PostMapping("/{bookId}/edit")
    public String updateBook(
            @PathVariable UUID bookId,
            @Valid @ModelAttribute("book") BookRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookId", bookId);
            return "books/edit";
        }

        bookService.updateBook(bookId, request);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable UUID bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }

    @PostMapping("/{bookId}/assign")
    public String assignBook(
            @PathVariable UUID bookId,
            @RequestParam UUID personId
    ) {
        bookService.assignBookToPerson(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{bookId}/release")
    public String releaseBook(@PathVariable UUID bookId) {
        bookService.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute("query", query);

        if (query != null && !query.isBlank()) {
            model.addAttribute("books", bookService.searchBooks(query));
            model.addAttribute("searchPerformed", true);
        } else {
            model.addAttribute("searchPerformed", false);
        }

        return "books/search";
    }
}