package app.green.route.service.file;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageService {
  private final Storage storage;
  private final String bucketName;
  private final String serviceAccountKey;

  public FileStorageService(
      @Value("${bucket.name}") String bucketName, @Value("${gemini.api.key}") String key)
      throws IOException {
    this.bucketName = bucketName;
    serviceAccountKey = key;
    storage =
        StorageOptions.getDefaultInstance().toBuilder()
            .setCredentials(
                GoogleCredentials.fromStream(
                    new ByteArrayInputStream(serviceAccountKey.getBytes())))
            .build()
            .getService();
  }

  public String uploadFile(String fileId, byte[] file) throws IOException {
    BlobId id = BlobId.of(bucketName, fileId);
    BlobInfo fileInfo = BlobInfo.newBuilder(id).setContentType("image/*").build();
    Blob blob = storage.createFrom(fileInfo, new ByteArrayInputStream(file));
    return blob.asBlobInfo().getBlobId().getName();
  }

  public byte[] downloadFile(String fileId) {
    BlobId blobId = BlobId.of(bucketName, fileId);
    return storage.readAllBytes(blobId);
  }
}
