package manage_controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

import constructor.parkBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class manage_incomeDayChart_control implements Initializable{
	   @FXML
	    private Button btnClose;

	    @FXML
	    private Label lookMonth;

	    @FXML
	    private LineChart<String, Initializable> dayIncomeChart;

	    @FXML
	    private CategoryAxis day;

	    @FXML
	    private NumberAxis income;
	    
	    private ObservableList<String> dayNames = FXCollections.observableArrayList();
	    private String foundMonth;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
//		YearMonth findDay = YearMonth.from(LocalDate.parse(foundMonth,DateTimeFormatter.ofPattern("yyyyMMdd")));
//		int days = findDay.lengthOfMonth();
//		dayNames.addAll(String.valueOf(Arrays.asList(days)));
//		
//		day.setCategories(dayNames);
		
	}
	public void SetIncomeChart(String thisYear, String thisMonth, ObservableList<parkBook> dayIncomeList) { //월 받아오기
		lookMonth.setText(thisMonth);
		foundMonth = thisYear+"0"+thisMonth+"01";
		System.out.println(foundMonth);
		
		YearMonth findDay = YearMonth.from(LocalDate.parse(foundMonth,DateTimeFormatter.ofPattern("yyyyMMdd")));
		int days = findDay.lengthOfMonth();
		dayNames.addAll(String.valueOf(Arrays.asList(days)));
		
		day.setCategories(dayNames);

	}
	

}
