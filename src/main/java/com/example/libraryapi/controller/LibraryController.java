package com.example.libraryapi.controller;

import com.example.libraryapi.dto.LibraryResponse;
import com.example.libraryapi.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public LibraryResponse getLibrariesByIsbn(
            @RequestParam String isbn,
            @RequestParam(defaultValue = "11") String region, // 지역 기본값: 11
            @RequestParam(required = false) String dtlRegion, // 세부 지역은 선택적
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return libraryService.fetchLibrariesByIsbn(isbn, region, dtlRegion, pageNo, pageSize);
    }
}
