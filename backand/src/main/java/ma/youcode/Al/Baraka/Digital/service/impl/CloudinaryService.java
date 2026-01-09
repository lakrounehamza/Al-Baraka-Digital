package ma.youcode.Al.Baraka.Digital.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ma.youcode.Al.Baraka.Digital.dto.response.CloudinaryResponse;
import ma.youcode.Al.Baraka.Digital.util.FileUploadUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file) {

        //  Autoriser   les   document
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PDF_PATTERN);

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils
                    .getExtension(originalFilename)
                    .toLowerCase();

            String fileName = FileUploadUtil.getFileName(originalFilename);

            boolean isPdf = "pdf".equals(extension);

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", fileName,
                            "folder", isPdf ? "documents" : "images",
                            "resource_type", isPdf ? "raw" : "image",
                            "type", "upload",          //  important
                            "access_mode", "public"    //  solution 401   pour pdf
                    )
            );
            return  new CloudinaryResponse(uploadResult.get("public_id").toString()
                    ,uploadResult.get("secure_url").toString(), fileName,extension,isPdf ? "raw" : "image");

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
}
