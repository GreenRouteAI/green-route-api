package app.green.route.endpoint.controller;

import static app.green.route.endpoint.mapper.RouteRestMapper.parseMediaTypeFromBytes;
import static java.util.UUID.randomUUID;

import app.green.route.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FileController {
  private final FileService service;

  @PostMapping("/users/{id}/raw")
  public String updatePhoto(@PathVariable("id") String userId, @RequestBody byte[] file) {
    var fileId = randomUUID().toString();
    return service.updateUserPhoto(fileId, file);
  }

  @PostMapping("/raw/{fileId}")
  public String uploadFile(@PathVariable("fileId") String fileId, @RequestBody byte[] file) {
    return service.uploadFile(fileId, file);
  }

  @GetMapping(value = "/raw/{fileId}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") String fileId) {
    var file = service.downloadFile(fileId);
    return ResponseEntity.ok().contentType(parseMediaTypeFromBytes(file)).body(file);
  }
}
