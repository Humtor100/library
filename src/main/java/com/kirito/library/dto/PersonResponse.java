package com.kirito.library.dto;

import com.kirito.library.model.Book;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PersonResponse(UUID personId, String name, Integer birthday, List<Book> books)

{
}
