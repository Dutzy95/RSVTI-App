package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		try {
			imageView.setImage(new Image(getClass().getResourceAsStream("/RSVTI_with_text.png")));
			barChart.setAnimated(false);
			drawBarChart();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void drawBarChart() {
		try {
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
						String tmp = "";
						try {
							if(object.doubleValue() == Double.valueOf(object.intValue())) {
								tmp = object.intValue() + "";
							}
						} catch (Exception e) {
							DBServices.saveErrorLogEntry(e);
						}
						return tmp;
			        }
					
					@Override
					public Number fromString(String string) {
						int tmp = 0;
						try {
							tmp = Integer.parseInt(string);
						} catch (Exception e) {
							DBServices.saveErrorLogEntry(e);
						}
						return tmp;
					}
				});
				
				XYChart.Series<String,Number> employees = new XYChart.Series<String,Number>();
				employees.setName("Angajați");
				
				XYChart.Series<String,Number> rigs = new XYChart.Series<String,Number>();
				rigs.setName("Utilaje");
				
				XYChart.Series<String,Number> valves = new XYChart.Series<String,Number>();
				valves.setName("Supape");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
				Calendar calendar = Calendar.getInstance();
				
				if(homeDateIntervalUnit == Calendar.DATE) {
					for(int i = 0; i < homeDateIntervalNumberOf; i++) {
						employees.getData().add(new XYChart.Data<String,Number>(calendar.get(homeDateIntervalUnit) + "-" + dateFormat.format(calendar.getTime()),
								DBServices.getEmployeesBetweenDateInterval(
										calendar.getTime(),
										calendar.getTime(),
										(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
						calendar.add(homeDateIntervalUnit, 1);
					}
					calendar = Calendar.getInstance();
					for(int i = 0; i < homeDateIntervalNumberOf; i++) {
						rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(homeDateIntervalUnit) + "-" + dateFormat.format(calendar.getTime()),
								DBServices.getRigsBetweenDateInterval(
										calendar.getTime(),
										calendar.getTime(),
										(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
						calendar.add(homeDateIntervalUnit, 1);
					}
					calendar = Calendar.getInstance();
					for(int i = 0; i < homeDateIntervalNumberOf; i++) {
						valves.getData().add(new XYChart.Data<String,Number>(calendar.get(homeDateIntervalUnit) + "-" + dateFormat.format(calendar.getTime()),
								DBServices.getValvesBetweenDateInterval(calendar.getTime(), calendar.getTime()).size()));
						calendar.add(homeDateIntervalUnit, 1);
					}
				} else if(homeDateIntervalUnit == Calendar.MONTH) {
					
					Calendar firstDayOfMonth = Calendar.getInstance();
					Calendar lastDayOfMonth = Calendar.getInstance();
					lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
					
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR) ,
							DBServices.getEmployeesBetweenDateInterval(
									calendar.getTime(),
									lastDayOfMonth.getTime(),
									(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
					
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getRigsBetweenDateInterval(
									calendar.getTime(),
									lastDayOfMonth.getTime(),
									(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
					
					valves.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getValvesBetweenDateInterval(calendar.getTime(), lastDayOfMonth.getTime()).size()));
					
					for(int i = 1; i < homeDateIntervalNumberOf; i++) {
						calendar.add(homeDateIntervalUnit, 1);
						firstDayOfMonth = (Calendar) calendar.clone();
						lastDayOfMonth = (Calendar) calendar.clone();
						firstDayOfMonth.set(Calendar.DATE, 1);
						lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
						
						employees.getData().add(new XYChart.Data<String,Number>(dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
								DBServices.getEmployeesBetweenDateInterval(
										firstDayOfMonth.getTime(),
										lastDayOfMonth.getTime(),
										(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
					}
					calendar = Calendar.getInstance();
					for(int i = 1; i < homeDateIntervalNumberOf; i++) {
						calendar.add(homeDateIntervalUnit, 1);
						firstDayOfMonth = (Calendar) calendar.clone();;
						lastDayOfMonth = (Calendar) calendar.clone();
						firstDayOfMonth.set(Calendar.DATE, 1);
						lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
						
						rigs.getData().add(new XYChart.Data<String,Number>(dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
								DBServices.getRigsBetweenDateInterval(
										firstDayOfMonth.getTime(),
										lastDayOfMonth.getTime(),
										(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
					}
					calendar = Calendar.getInstance();
					for(int i = 1; i < homeDateIntervalNumberOf; i++) {
						calendar.add(homeDateIntervalUnit, 1);
						firstDayOfMonth = (Calendar) calendar.clone();;
						lastDayOfMonth = (Calendar) calendar.clone();
						firstDayOfMonth.set(Calendar.DATE, 1);
						lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
						
						valves.getData().add(new XYChart.Data<String,Number>(dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
								DBServices.getValvesBetweenDateInterval(firstDayOfMonth.getTime(), lastDayOfMonth.getTime()).size()));
					}
					
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfMonth = (Calendar) calendar.clone();
					firstDayOfMonth.set(Calendar.DATE, 1);
					Calendar sameDayInMonth = (Calendar) calendar.clone();
					sameDayInMonth.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
					
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR) ,
							DBServices.getEmployeesBetweenDateInterval(
									firstDayOfMonth.getTime(),
									sameDayInMonth.getTime(),
									(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
					
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getRigsBetweenDateInterval(
									firstDayOfMonth.getTime(),
									sameDayInMonth.getTime(),
									(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
					
					valves.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getValvesBetweenDateInterval(firstDayOfMonth.getTime(), sameDayInMonth.getTime()).size()));
					
				} else if(homeDateIntervalUnit == Calendar.YEAR) {
					
					Calendar firstDayOfYear = Calendar.getInstance();
					Calendar lastDayOfYear = Calendar.getInstance();
					lastDayOfYear.set(Calendar.DATE, 31);
					lastDayOfYear.set(Calendar.MONTH, Calendar.DECEMBER);
					
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
							DBServices.getEmployeesBetweenDateInterval(
									calendar.getTime(),
									lastDayOfYear.getTime(),
									(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
					
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getRigsBetweenDateInterval(
									calendar.getTime(),
									lastDayOfYear.getTime(),
									(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
					
					valves.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getValvesBetweenDateInterval(calendar.getTime(), lastDayOfYear.getTime()).size()));
					
					for(int i = 1; i < homeDateIntervalNumberOf; i++) {
						calendar.add(homeDateIntervalUnit, 1);
						firstDayOfYear = (Calendar) calendar.clone();
						lastDayOfYear = (Calendar) calendar.clone();
						firstDayOfYear.set(Calendar.DATE, 1);
						firstDayOfYear.set(Calendar.MONTH, 0);
						lastDayOfYear.set(Calendar.DATE, 31);
						lastDayOfYear.set(Calendar.MONTH, Calendar.DECEMBER);
						
						employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.YEAR) + "",
								DBServices.getEmployeesBetweenDateInterval(
										firstDayOfYear.getTime(),
										lastDayOfYear.getTime(),
										(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
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
								DBServices.getRigsBetweenDateInterval(
										firstDayOfYear.getTime(),
										lastDayOfYear.getTime(),
										(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
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
						
						valves.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.YEAR) + "",
								DBServices.getValvesBetweenDateInterval(firstDayOfYear.getTime(), lastDayOfYear.getTime()).size()));
					}
					
					calendar.add(homeDateIntervalUnit, 1);
					firstDayOfYear = (Calendar) calendar.clone();
					firstDayOfYear.set(Calendar.DATE, 1);
					firstDayOfYear.set(Calendar.MONTH, 0);
					Calendar sameDayInYear = (Calendar) calendar.clone();
					sameDayInYear.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
					sameDayInYear.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
					
					employees.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR),
							DBServices.getEmployeesBetweenDateInterval(
									firstDayOfYear.getTime(),
									sameDayInYear.getTime(),
									(e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate())).size()));
					
					rigs.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getRigsBetweenDateInterval(
									firstDayOfYear.getTime(),
									sameDayInYear.getTime(),
									(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate())).size()));
					
					valves.getData().add(new XYChart.Data<String,Number>(calendar.get(Calendar.DATE) + "-" + dateFormat.format((calendar.getTime())) + "-" + calendar.get(Calendar.YEAR), 
							DBServices.getValvesBetweenDateInterval(firstDayOfYear.getTime(), sameDayInYear.getTime()).size()));
				}
				
				barChart.getData().addAll(employees, rigs, valves);
			} else {
				barChart.setTitle("Nu s-a setat nicio perioadă");
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void refresh() {
		if(barChart.getData().size() > 0) {
			barChart.getData().remove(0);
			barChart.getData().remove(0);
			barChart.getData().remove(0);
		}
		drawBarChart();
	}
}
