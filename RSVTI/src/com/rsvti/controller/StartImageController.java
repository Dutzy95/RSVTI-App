package com.rsvti.controller;

import com.rsvti.model.database.DBServices;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartImageController {

	@FXML
	private ImageView imageView;
	
	@FXML
	private void initialize() {
		try {
			imageView.setImage(new Image(getClass().getResourceAsStream("/RSVTI_with_text.png")));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
