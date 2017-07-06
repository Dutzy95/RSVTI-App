package com.rsvti.address.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rsvti.common.Utils;
import com.rsvti.database.services.DBServices;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

public class HomeController {

	@FXML
	private ImageView imageView;
	
	@FXML
	private BarChart<String,Number> barChart;
	
	private String homeDateIntervalUnitString = "";
	private int homeDateIntervalUnit = 0;
	private int homeDateIntervalNumberOf = 0;
	
	@FXML
	private void initialize() {
		imageView.setImage(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_with_text.png").toURI().toString()));
		
		barChart.setAnimated(false);
		
		drawBarChart();
	}
	
	@SuppressWarnings("unchecked")
	private void drawBarChart() {
		if(!DBServices.getHomeDateDisplayInterval().split(" ")[0].equals("")) {
			homeDateIntervalNumberOf = Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[0]);
			homeDateIntervalUnit = Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[1]);
			switch(homeDateIntervalUnit) {
			case Calendar.DATE : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnitString = "zile";
				} else {
					homeDateIntervalUnitString = "zi";
				}
				break;
			}
			case Calendar.MONTH : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnitString = "luni";
				} else {
					homeDateIntervalUnitString = "lună";
				}
				break;
			}
			case Calendar.YEAR : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnitString = "ani";
				} else {
					homeDateIntervalUnitString = "an";
				}
				break;
			}
			}
		}
		
		if(!DBServices.getHomeDateDisplayInterval().equals("")) {
			barChart.setTitle("Datele scadente pe perioada de " + DBServices.getHomeDateDisplayInterval().split(" ")[0] + " " + homeDateIntervalUnitString);
			barChart.getXAxis().setLabel(homeDateIntervalUnitString);
			((ValueAxis<Number>) barChart.getYAxis()).setTickLabelFormatter(new StringConverter<Number>() {
				@Override
		        public String toString(Number object) {
					if(object.doubleValue() == Double.valueOf(object.intValue())) {
						return object.intValue() + "";
					} else {
						return "";
					}
		        }
				
				@Override
				public Number fromString(String string) {
					return Integer.parseInt(string);
				}
			});
			
			XYChart.Series<String,Number> employees = new XYChart.Series<String,Number>();
			employees.setName("Angajați");
			
			XYChart.Series<String,Number> rigs = new XYChart.Series<String,Number>();
			rigs.setName("Utilaje");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
			Calendar calendar = Calendar.getInstance();
			
			if(homeDateIntervalUnit == Calendar.DATE) {
				for(int i = 0; i < homeDateIntervalNumberOf; i++) {
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(homeDateIntervalUnit) + "-" + dateFormat.format(calendar.getTime()),
							DBServices.getEmployeesBetweenDateInterval(calendar.getTime(), calendar.getTime()).size()));
					calendar.add(homeDateIntervalUnit, 1);
				}
				calendar = Calendar.getInstance();
				for(int i = 0; i < homeDateIntervalNumberOf; i++) {
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(homeDateIntervalUnit) + "-" + dateFormat.format(calendar.getTime()),
							DBServices.getRigsBetweenDateInterval(calendar.getTime(), calendar.getTime()).size()));
					calendar.add(homeDateIntervalUnit, 1);
				}
			} else if(homeDateIntervalUnit == Calendar.MONTH) {
				
				Calendar firstDayOfMonth = Calendar.getInstance();
				Calendar lastDayOfMonth = Calendar.getInstance();
				lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
				
				employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR) ,
						DBServices.getEmployeesBetweenDateInterval(calendar.getTime(), lastDayOfMonth.getTime()).size()));
				
				rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
						DBServices.getRigsBetweenDateInterval(calendar.getTime(), lastDayOfMonth.getTime()).size()));
				
				for(int i = 1; i < homeDateIntervalNumberOf; i++) {
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfMonth = (Calendar) calendar.clone();
					lastDayOfMonth = (Calendar) calendar.clone();
					firstDayOfMonth.set(Calendar.DATE, 1);
					lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
					
					employees.getData().add(new XYChart.Data<String,Number>(dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
							DBServices.getEmployeesBetweenDateInterval(firstDayOfMonth.getTime(), lastDayOfMonth.getTime()).size()));
				}
				calendar = Calendar.getInstance();
				for(int i = 1; i < homeDateIntervalNumberOf; i++) {
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfMonth = (Calendar) calendar.clone();;
					lastDayOfMonth = (Calendar) calendar.clone();
					firstDayOfMonth.set(Calendar.DATE, 1);
					lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
					
					rigs.getData().add(new XYChart.Data<String,Number>(dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
							DBServices.getRigsBetweenDateInterval(firstDayOfMonth.getTime(), lastDayOfMonth.getTime()).size()));
				}
				
				calendar.add(homeDateIntervalUnit, 1);
				firstDayOfMonth = (Calendar) calendar.clone();
				firstDayOfMonth.set(Calendar.DATE, 1);
				Calendar sameDayInMonth = (Calendar) calendar.clone();
				sameDayInMonth.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
				
				employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR) ,
						DBServices.getEmployeesBetweenDateInterval(firstDayOfMonth.getTime(), sameDayInMonth.getTime()).size()));
				
				rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
						DBServices.getRigsBetweenDateInterval(firstDayOfMonth.getTime(), sameDayInMonth.getTime()).size()));
				
			} else if(homeDateIntervalUnit == Calendar.YEAR) {
				
				Calendar firstDayOfYear = Calendar.getInstance();
				Calendar lastDayOfYear = Calendar.getInstance();
				lastDayOfYear.set(Calendar.DATE, 31);
				lastDayOfYear.set(Calendar.MONTH, Calendar.DECEMBER);
				
				employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
						DBServices.getEmployeesBetweenDateInterval(calendar.getTime(), lastDayOfYear.getTime()).size()));
				
				rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
						DBServices.getRigsBetweenDateInterval(calendar.getTime(), lastDayOfYear.getTime()).size()));
				
				for(int i = 1; i < homeDateIntervalNumberOf; i++) {
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfYear = (Calendar) calendar.clone();
					lastDayOfYear = (Calendar) calendar.clone();
					firstDayOfYear.set(Calendar.DATE, 1);
					firstDayOfYear.set(Calendar.MONTH, 0);
					lastDayOfYear.set(Calendar.DATE, 31);
					lastDayOfYear.set(Calendar.MONTH, Calendar.DECEMBER);
					
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.YEAR) + "",
							DBServices.getEmployeesBetweenDateInterval(firstDayOfYear.getTime(), lastDayOfYear.getTime()).size()));
				}
				calendar = Calendar.getInstance();
				for(int i = 1; i < homeDateIntervalNumberOf; i++) {
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfYear = (Calendar) calendar.clone();;
					lastDayOfYear = (Calendar) calendar.clone();
					firstDayOfYear.set(Calendar.DATE, 1);
					firstDayOfYear.set(Calendar.MONTH, 0);
					lastDayOfYear.set(Calendar.DATE, 31);
					lastDayOfYear.set(Calendar.MONTH, Calendar.DECEMBER);
					
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.YEAR) + "",
							DBServices.getRigsBetweenDateInterval(firstDayOfYear.getTime(), lastDayOfYear.getTime()).size()));
				}
				
				calendar.add(homeDateIntervalUnit, 1);
				firstDayOfYear = (Calendar) calendar.clone();
				firstDayOfYear.set(Calendar.DATE, 1);
				firstDayOfYear.set(Calendar.MONTH, 0);
				Calendar sameDayInYear = (Calendar) calendar.clone();
				sameDayInYear.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
				sameDayInYear.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
				
				employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
						DBServices.getEmployeesBetweenDateInterval(firstDayOfYear.getTime(), sameDayInYear.getTime()).size()));
				
				rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
						DBServices.getRigsBetweenDateInterval(firstDayOfYear.getTime(), sameDayInYear.getTime()).size()));
			}
			
			barChart.getData().addAll(employees, rigs);
		} else {
			barChart.setTitle("Nu s-a setat nicio perioadă");
		}
	}
	
	public void refresh() {
		if(barChart.getData().size() > 0) {
			barChart.getData().remove(0);
			barChart.getData().remove(0);
		}
		drawBarChart();
	}
}
