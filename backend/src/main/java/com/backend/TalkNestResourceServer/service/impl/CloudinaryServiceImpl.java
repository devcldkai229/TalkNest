package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map<String, Object> uploadImage(MultipartFile file, String folder, String publicId, boolean overwrite) throws IOException {

        return (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder,
                        "public_id", "user_" + publicId,
                        "overwrite", overwrite
                ));
    }


}
