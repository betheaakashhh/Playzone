package com.playzone.api.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController @RequestMapping("/api/uploads")
public class UploadController {
  private final Path root = Paths.get("uploads").toAbsolutePath().normalize();
  @PostMapping(value="/evidence", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public java.util.Map<String,String> evidence(@RequestParam("file") MultipartFile file) throws IOException {
    if(file.isEmpty()) throw new IllegalArgumentException("File is empty");
    if(file.getSize()>5_000_000) throw new IllegalArgumentException("Maximum file size is 5 MB");
    String type=file.getContentType()==null?"":file.getContentType();
    if(!type.equals("image/png")&&!type.equals("image/jpeg")&&!type.equals("image/webp")) throw new IllegalArgumentException("Only PNG, JPEG or WebP images are allowed");
    Files.createDirectories(root);
    String ext=type.equals("image/png")?".png":type.equals("image/webp")?".webp":".jpg";
    String name=UUID.randomUUID()+ext; Path target=root.resolve(name).normalize();
    if(!target.getParent().equals(root)) throw new IllegalArgumentException("Invalid filename");
    Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
    return java.util.Map.of("url","/uploads/"+name,"filename",name);
  }
}
