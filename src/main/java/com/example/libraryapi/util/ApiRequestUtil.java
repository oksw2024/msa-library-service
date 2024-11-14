package com.example.libraryapi.util;

import com.example.libraryapi.model.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class ApiRequestUtil {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Library> getLibrariesFromApi(String url) throws Exception {
        String response = restTemplate.getForObject(url, String.class);

        // JSON 응답 확인 - xml 방지
        if (response.startsWith("<")) {
            throw new RuntimeException("API responded with non-JSON format: " + response);
        }

        // JSON 응답 파싱
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Map<String, Object> responseContent = (Map<String, Object>) responseMap.get("response");
        List<Map<String, Object>> librariesList = (List<Map<String, Object>>) responseContent.get("libs");

        // 도서관 데이터 매핑
        return librariesList.stream()
                .map(libWrapper -> {
                    Map<String, String> libData = (Map<String, String>) libWrapper.get("lib");
                    Library library = new Library();
                    library.setLibCode(libData.get("libCode"));
                    library.setLibName(libData.get("libName"));
                    library.setAddress(libData.get("address"));
                    library.setTel(libData.get("tel"));
                    library.setFax(libData.get("fax"));
                    library.setLatitude(libData.get("latitude"));
                    library.setLongitude(libData.get("longitude"));
                    library.setHomepage(libData.get("homepage"));
                    library.setClosed(libData.get("closed"));
                    library.setOperatingTime(libData.get("operatingTime"));
                    return library;
                })
                .toList();
    }
}