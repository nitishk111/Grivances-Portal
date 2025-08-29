package io.github.nitishc.grievance.grievance_service.service;

import io.github.nitishc.grievance.grievance_service.dto.FileDto;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotFoundException;
import io.github.nitishc.grievance.grievance_service.exception.GrievanceNotSavedException;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import io.github.nitishc.grievance.grievance_service.model.GrievanceFile;
import io.github.nitishc.grievance.grievance_service.repository.FileRepository;
import io.github.nitishc.grievance.grievance_service.repository.GrievanceRepository;
import io.github.nitishc.grievance.grievance_service.util.FileMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
@Slf4j
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    GrievanceRepository grievanceRepository;

    @Transactional
    public String storeFile(MultipartFile file, long grievanceId, String userEmail) throws GrievanceNotFoundException, IOException {

        Grievance grievance= grievanceRepository.findById(grievanceId).get();
//        int a= 9;
//        long b= grievance.getGrievanceId();
//        if(grievance.getGrievanceId()!=grievanceId || grievance.getUserEmail()!=  userEmail){
//            log.error("No grievance registered by grievance id: {}", grievanceId);
//            throw new GrievanceNotFoundException("No grievance registered by grievance id: "+ grievanceId);
//        }

        GrievanceFile grievanceFile= new GrievanceFile();

        grievanceFile.setFileName(file.getName());
        grievanceFile.setFileType(file.getContentType());
        grievanceFile.setUserEmail(userEmail);
        grievanceFile.setUploadedAt(LocalDate.now());
        grievanceFile.setImage(file.getBytes());
        grievance.setGrievanceFile(grievanceFile);

        try{
            grievanceRepository.save(grievance);
            fileRepository.save(grievanceFile);
            log.info("File Saved");
            return "File saved";
        }catch (Exception e){
            log.error("File can not be saved", e.getMessage());
            throw e;
        }
    }

    public GrievanceFile getFile(long grievanceId) throws GrievanceNotFoundException {
        try{
            Grievance grievance= grievanceRepository.findById(grievanceId).get();
            return grievance.getGrievanceFile();
        }catch (Exception e){
            log.info("File not found", e.getMessage());
            throw new GrievanceNotFoundException(e.getMessage());
        }

    }
}
