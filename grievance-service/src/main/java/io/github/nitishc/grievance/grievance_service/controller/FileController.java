package io.github.nitishc.grievance.grievance_service.controller;

import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.model.GrievanceFile;
import io.github.nitishc.grievance.grievance_service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/{user-email}/{grievance-id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("user-email") String email, @PathVariable("grievance-id") long grievanceId) {
        try {
            String message = fileService.storeFile(file, grievanceId, email);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not upload file: " + e.getMessage());
        }
    }

    // Download
    @GetMapping("/{grievance-id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("grievance-id") long grievanceId) throws GrievanceNotFoundException {
        GrievanceFile file = fileService.getFile(grievanceId);

        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getImage());
    }
}
