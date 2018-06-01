package com.rsvti.controller;

import java.util.Arrays;

import com.rsvti.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.TestQuestion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class AddTestQuestionController {
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TextArea questionArea;
	@FXML
	private TextArea firstAnswerArea;
	@FXML
	private TextArea secondAnswerArea;
	@FXML
	private TextArea thirdAnswerArea;
	
	@FXML
	private TableView<TestQuestion> testTable;
	@FXML
	private TableColumn<TestQuestion,String> questionColumn;
	@FXML
	private TableColumn<TestQuestion,String> questionTypeColumn;
	
	@FXML
	private ComboBox<String> questionTypeComboBox;
	
	@FXML
	private Button saveButton;
	@FXML
	private Button deleteButton;
	
	private boolean isUpdate = false;
	private TestQuestion questionToUpdate;

	@FXML
	private void initialize() {
		try {
			testTable.setItems(FXCollections.observableArrayList(DBServices.getAllTestQuestions()));
			testTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue != null) {
					deleteButton.setDisable(false);
				} else {
					deleteButton.setDisable(true);
				}
			});
			questionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuestion()));
			questionTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
			questionTypeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("stivutorist", "macaragist", "legător de sarcină", "manevrant", "fochist")));
			questionTypeComboBox.getSelectionModel().select(0);
			testTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showTestDetails(newValue));
			testTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			deleteButton.setDisable(true);
			saveButton.setDisable(true);
			
			ChangeListener<String> listener = (observable, oldValue, newValue) -> {
				if(Utils.allFieldsAreFilled(questionArea, firstAnswerArea, secondAnswerArea, thirdAnswerArea)) {
					saveButton.setDisable(false);
				} else {
					saveButton.setDisable(true);
				}
			};
			
			questionArea.textProperty().addListener(listener);
			firstAnswerArea.textProperty().addListener(listener);
			secondAnswerArea.textProperty().addListener(listener);
			thirdAnswerArea.textProperty().addListener(listener);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private void showTestDetails(TestQuestion question) {
		try {
			if(question != null) {
				questionArea.setText(question.getQuestion());
				firstAnswerArea.setText(question.getAnswers().get(0));
				secondAnswerArea.setText(question.getAnswers().get(1));
				thirdAnswerArea.setText(question.getAnswers().get(2));
				questionTypeComboBox.setValue(question.getType());
				isUpdate = true;
				questionToUpdate = question;
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleEmptyAreas() {
		try {
			testTable.getSelectionModel().clearSelection();
			questionArea.setText("");
			firstAnswerArea.setText("");
			secondAnswerArea.setText("");
			thirdAnswerArea.setText("");
			questionTypeComboBox.getSelectionModel().select(0);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleDeleteTestQuestion() {
		try {
			TestQuestion selectedQuestion = testTable.getSelectionModel().getSelectedItem();
			if(selectedQuestion != null) {
				DBServices.deleteEntry(selectedQuestion);
			}
			testTable.setItems(FXCollections.observableArrayList(DBServices.getAllTestQuestions()));
			handleEmptyAreas();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleSaveTestQuestion() {
		try {
			if(isUpdate) {
				DBServices.updateEntry(questionToUpdate, new TestQuestion(questionArea.getText(), 
						Arrays.asList(firstAnswerArea.getText(), secondAnswerArea.getText(), thirdAnswerArea.getText()),
						questionTypeComboBox.getValue()));
				isUpdate = false;
			} else {
				DBServices.saveEntry(new TestQuestion(questionArea.getText(), 
						Arrays.asList(firstAnswerArea.getText(), secondAnswerArea.getText(), thirdAnswerArea.getText()),
						questionTypeComboBox.getValue()));
			}
			testTable.setItems(FXCollections.observableArrayList(DBServices.getAllTestQuestions()));
			handleEmptyAreas();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
