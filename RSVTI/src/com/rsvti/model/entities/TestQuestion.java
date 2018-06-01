package com.rsvti.model.entities;

import java.util.List;

public class TestQuestion {

	private String question;
	private List<String> answers;
	private String type;
	
	public TestQuestion(String question, List<String> answers, String type) {
		this.setQuestion(question);
		this.setAnswers(answers);
		this.setType(type);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof TestQuestion &&
				((TestQuestion) o).getQuestion().equals(question) &&
				((TestQuestion) o).getAnswers().equals(answers);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
