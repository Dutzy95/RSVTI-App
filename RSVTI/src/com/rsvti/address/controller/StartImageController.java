package com.rsvti.address.controller;

import java.io.File;

import com.rsvti.common.Utils;
import com.rsvti.database.services.DBServices;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartImageController {

	@FXML
	private ImageView imageView;
	
	@FXML
	private void initialize() {
		try {
			imageView.setImage(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_with_text.png").toURI().toString()));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
