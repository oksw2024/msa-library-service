package com.example.libraryapi.dto;

import com.example.libraryapi.model.Library;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LibraryResponse {
    private String pageNo;
    private String pageSize;
    private int numFound;
    private int resultNum;
    private List<LibraryWrapper> libs;

    @Data
    @AllArgsConstructor
    public static class LibraryWrapper {
        private Library lib;
    }
}
