package pl.maprzybysz.imagehostingvaadin.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.maprzybysz.imagehostingvaadin.repository.ImageRepository;
import pl.maprzybysz.imagehostingvaadin.service.ImageUploader;

@Route("uploadImage")
public class UploadGui extends VerticalLayout {

    private ImageUploader imageUploader;


    @Autowired
    public UploadGui(ImageUploader imageUploader) {
        this.imageUploader = imageUploader;


        TextField textField = new TextField();
        Button button = new Button("upload");

        Label label = new Label();
        button.addClickListener(buttonClickEvent ->
        {
            String file = imageUploader.uploadFileAndSaveUrlToDatabase(textField.getValue());
            Image image = new Image(file, "null");
            label.setText("Upload successful");
            add(label);
            add(image);

        });

        add(textField, button);
    }
}
