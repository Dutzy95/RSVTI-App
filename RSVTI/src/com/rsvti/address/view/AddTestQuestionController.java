package com.rsvti.address.view;

import java.util.Arrays;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class AddTestQuestionController {
	
	@FXML
	public JavaFxMain javaFxMain;
	
	@FXML
	public TextArea questionArea;
	@FXML
	public TextArea firstAnswerArea;
	@FXML
	public TextArea secondAnswerArea;
	@FXML
	public TextArea thirdAnswerArea;
	
	@FXML
	public TableView<TestQuestion> testTable;
	@FXML
	public TableColumn<TestQuestion,String> questionColumn;
	@FXML
	public TableColumn<TestQuestion,String> questionTypeColumn;
	
	@FXML
	public ComboBox<String> questionTypeComboBox;
	
	private boolean isUpdate = false;
	private TestQuestion questionToUpdate;

	public AddTestQuestionController() {
	}
	
	@FXML
	private void initialize() {
		testTable.setItems(FXCollections.observableArrayList(DBServices.getAllTestQuestions()));
		questionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuestion()));
		questionTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		questionTypeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("stivutorist", "macaragist", "legător de sarcină", "manevrant", "fochist")));
		questionTypeComboBox.getSelectionModel().select(0);
		testTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showTestDetails(newValue));
	}
	
	private void showTestDetails(TestQuestion question) {
		if(question != null) {
			questionArea.setText(question.getQuestion());
			firstAnswerArea.setText(question.getAnswers().get(0));
			secondAnswerArea.setText(question.getAnswers().get(1));
			thirdAnswerArea.setText(question.getAnswers().get(2));
			questionTypeComboBox.setValue(question.getType());
			isUpdate = true;
			questionToUpdate = question;
		}
	}
	
	public void handleEmptyAreas() {
		testTable.getSelectionModel().clearSelection();
		questionArea.setText("");
		firstAnswerArea.setText("");
		secondAnswerArea.setText("");
		thirdAnswerArea.setText("");
		questionTypeComboBox.getSelectionModel().select(0);
	}
	
	public void handleDeleteTestQuestion() {
		TestQuestion selectedQuestion = testTable.getSelectionModel().getSelectedItem();
		if(selectedQuestion != null) {
			DBServices.deleteEntry(selectedQuestion);
		}
		testTable.setItems(FXCollections.observableArrayList(DBServices.getAllTestQuestions()));
		handleEmptyAreas();
	}
	
	public void handleSaveTestQuestion() {
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
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
