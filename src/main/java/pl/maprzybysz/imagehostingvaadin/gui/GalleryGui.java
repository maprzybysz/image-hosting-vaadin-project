package pl.maprzybysz.imagehostingvaadin.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.maprzybysz.imagehostingvaadin.model.Image;
import pl.maprzybysz.imagehostingvaadin.repository.ImageRepository;
import pl.maprzybysz.imagehostingvaadin.service.ImageUploader;

import java.util.List;

@Route("galleryImage")
public class GalleryGui extends VerticalLayout {

    private ImageRepository imageRepository;


    @Autowired
    public GalleryGui(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;

        List<Image> all = imageRepository.findAll();

        all.stream().forEach(element -> {
            System.out.println(element);
            com.vaadin.flow.component.html.Image image =
                    new com.vaadin.flow.component.html.Image(element.getImageAddress(), "null");
            add(image);
        });
    }
}
