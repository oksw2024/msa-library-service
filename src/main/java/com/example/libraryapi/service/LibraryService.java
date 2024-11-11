package com.example.libraryapi.service;

import com.example.libraryapi.dto.LibraryResponse;
import com.example.libraryapi.model.Library;
import com.example.libraryapi.util.ApiRequestUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private static final String API_URL = "https://data4library.kr/api/libSrchByBook";
    private static final String API_KEY = "246bc9a1a2ea4ba78b5ada1b16a0ba7e43537ef40b0427f80013629f7b593a86";

    public LibraryResponse fetchLibrariesByIsbn(String isbn, String region, String dtlRegion, int pageNo, int pageSize) {
        // 기본 URL 생성
        StringBuilder urlBuilder = new StringBuilder(String.format(
                "%s?isbn=%s&authKey=%s&region=%s&format=json&pageNo=%d&pageSize=%d",
                API_URL, isbn, API_KEY, region, pageNo, pageSize
        ));

        // 세부 지역 값이 존재하면 추가
        if (dtlRegion != null && !dtlRegion.isEmpty()) {
            urlBuilder.append("&dtl_region=").append(dtlRegion);
        }

        try {
            // OpenAPI 호출 및 데이터 가져오기
            List<Library> libraries = ApiRequestUtil.getLibrariesFromApi(urlBuilder.toString());

            // JSON 형식에 맞게 가공
            List<LibraryResponse.LibraryWrapper> wrappedLibraries = libraries.stream()
                    .map(lib -> new LibraryResponse.LibraryWrapper(lib))
                    .collect(Collectors.toList());

            return new LibraryResponse(
                    String.valueOf(pageNo),             // 페이지 번호
                    String.valueOf(pageSize),           // 페이지 크기
                    libraries.size(),                   // 총 검색 결과 수
                    wrappedLibraries.size(),            // 현재 페이지 결과 수
                    wrappedLibraries                    // 도서관 목록
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch libraries", e);
        }
    }
}