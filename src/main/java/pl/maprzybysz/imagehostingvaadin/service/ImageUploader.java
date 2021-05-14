package pl.maprzybysz.imagehostingvaadin.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.maprzybysz.imagehostingvaadin.model.Image;
import pl.maprzybysz.imagehostingvaadin.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploader {

    private Cloudinary cloudinary;
    private ImageRepository imageRepository;

    @Autowired
    public ImageUploader(ImageRepository imageRepository, @Value("${cloudName}") String cloudName,
                         @Value("${apiKey}") String apiKey, @Value("${apiSecret}") String apiSecret ){
        this.imageRepository = imageRepository;
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
    public String uploadFileAndSaveUrlToDatabase(String path){
        File file = new File(path);
        Map uploadResult = null;
        try {
           uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = uploadResult.get("url").toString();
        imageRepository.save(new Image(url));
        return url;
    }

}
