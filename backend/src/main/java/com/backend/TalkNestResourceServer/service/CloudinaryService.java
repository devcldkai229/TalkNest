package com.backend.TalkNestResourceServer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map<?, ?> uploadImage(MultipartFile file, String folder, String publicId, boolean overwrite) throws IOException;

}
