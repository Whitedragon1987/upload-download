package nl.novi.uploaddownload.repositories;

import nl.novi.uploaddownload.models.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileUploadResponse, String> {
}
