package com.dk.board_book.common;

import com.dk.board_book.domain.file.FileRequest;
import com.dk.board_book.domain.file.FileResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class FileUtils {

//    private final String uploadPath = "/home/ec2-user/upload-files"; // EC2 서버의 실제 업로드 경로로 수정
    private final String uploadPath = Paths.get("E:", "develop", "upload-files").toString();
    private final String uploadPathtoLinux =  Paths.get("/", "home", "ubuntu", "uploads").toString();
    String os = System.getProperty("os.name").toLowerCase();

    public List<FileRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<FileRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                files.add(uploadFile(multipartFile));
            }
        }
        return files;
    }

    public FileRequest uploadFile(final MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadDir = getUploadPath(today);
        String filePath = uploadDir + File.separator + saveName;
        System.out.println("파일이 업로드되는 물리적인 경로 : "+ filePath);

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + saveName, e);
        }

        return FileRequest.builder()
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .size(multipartFile.getSize())
                .build();
    }

    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath(final String addPath) { // 1

        if (os.contains("win")) {
            return makeDirectories(uploadPath + File.separator + addPath);
        } else {
            return makeDirectories(uploadPathtoLinux + File.separator + addPath);
        }
    }

    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath(); // 절대 경로로 반환
    }

    public void deleteFiles(final List<FileResponse> files) {

        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        for (FileResponse file : files) {
            String uploadedDate = file.getCreatedDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));

            deleteFile(uploadedDate, file.getSaveName());
        }
    }

    private void deleteFile(final String addPath, final String filename) { // 2

        String filePath = "";

        if (os.contains("win")) {
            filePath = Paths.get(uploadPath, addPath, filename).toString();
        } else {
            filePath = Paths.get(uploadPathtoLinux, addPath, filename).toString();
        }

        deleteFile(filePath);
    }

    private void deleteFile(final String filePath) {

        File file = new File(filePath);

        if (file.exists()) {

            file.delete();
        }
    }

    public Resource readFileAsResource(final FileResponse file) { // 3

        String uploadedDate = file.getCreatedDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));

        String filename = file.getSaveName();
         Path filePath = Paths.get("");

        if (os.contains("win")) {
             System.out.println("파일다운로드. 윈도우. "+ os);
             filePath = Paths.get(uploadPath, uploadedDate, filename);
        } else {
             System.out.println("파일다운로드. 윈도우 외의 운영체제"+os);
             filePath = Paths.get(uploadPathtoLinux, uploadedDate, filename);
        }


        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isFile()) {
                throw new RuntimeException("File not found: " + filePath);
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }

    public void resizeImage(String inputPath, String outputPath) {

        try {
            Thumbnails.of(inputPath)
                      .height(200)
                      .keepAspectRatio(true)
                      .toFile(outputPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to resize image", e);
        }
    }
}




