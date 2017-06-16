package com.rsvti.address.view;

import java.io.File;

import com.rsvti.main.Utils;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartImageController {

	@FXML
	private ImageView imageView;
	
	public StartImageController() {
	}
	
	@FXML
	private void initialize() {
		imageView.setImage(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_with_text.png").toURI().toString()));
	}
}
