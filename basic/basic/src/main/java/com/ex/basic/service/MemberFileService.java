package com.ex.basic.service;

import com.ex.basic.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberFileService {
    private static final String DIR = "uploads/";
    public String saveFile(MultipartFile multipartFile){
        String fileName = null;
        if(multipartFile == null || multipartFile.isEmpty())
            fileName="nan";
        else {
            // 파일명 중복 -> 유일한 값 랜덤하게 뽑아주는 기능
            // fileName : abcd123er34-파일명.jpg >> 이런 식으로 저장하겠다
            fileName = UUID.randomUUID().toString() + "-"
                    + multipartFile.getOriginalFilename();
            Path path = Paths.get(DIR + fileName);
            try {
                Files.createDirectories(path.getParent()); // 폴더 생성
                multipartFile.transferTo(path); // 파일을 경로에 저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return fileName;
    }

    public byte[] getImage(String fileName) {
        Path filePath = Paths.get(DIR +fileName);
        if (!Files.exists(filePath))
            throw new MemberNotFoundException("파일이 존재하지 않습니다"); // FileNotFoundException으로 바꿔주기
        byte[] imageBytes = {0};
        try {
            imageBytes = Files.readAllBytes(filePath);
        } catch(IOException e) {
            throw new RuntimeException();
        }
        return imageBytes;
    }

    public void deleteFile(String fileName) {
        Path path = Paths.get(DIR + fileName);
        try {
            boolean result = Files.deleteIfExists(path);
            System.out.println(fileName + "삭제" + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String modifyFile(String oldFileName, MultipartFile multipartFile) {
        String newFileName = null;

        if(multipartFile == null || multipartFile.isEmpty())
            newFileName="nan";
        else {
            // 파일명 중복 -> 유일한 값 랜덤하게 뽑아주는 기능
            // fileName : abcd123er34-파일명.jpg >> 이런 식으로 저장하겠다
            newFileName = UUID.randomUUID().toString() + "-"
                    + multipartFile.getOriginalFilename();
            Path path = Paths.get(DIR + newFileName);

            try {
                deleteFile(oldFileName);

                Files.createDirectories(path.getParent()); // 폴더 생성
                multipartFile.transferTo(path); // 파일을 경로에 저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return newFileName;
    }
}
