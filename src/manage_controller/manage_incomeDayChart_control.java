package manage_controller;

import java.net.URL;
import java.text.Format;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

import constructor.parkBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class manage_incomeDayChart_control implements Initializable{
	   @FXML
	    private Button btnClose;

	    @FXML
	    private Label lookMonth;

	    @FXML
	    private LineChart<String, Integer> dayIncomeChart;

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
	public void SetIncomeChart(String thisYear, int thisMonth, ObservableList<parkBook> dayIncomeList) { //월 받아오기
		lookMonth.setText(String.format("%02d", thisMonth)); //두자리 맞춰서 세팅
		foundMonth = thisYear+String.format("%02d", thisMonth)+"01";
		
		//달력 x축 이름 지정
		YearMonth findDay = YearMonth.from(LocalDate.parse(foundMonth,DateTimeFormatter.ofPattern("yyyyMMdd")));
		for(int i =1; i<=findDay.lengthOfMonth(); i++) {
			dayNames.addAll(String.valueOf(i));
		}
		
		day.setCategories(dayNames);
		
		
		//일별 객체 만들기
		XYChart.Series<String, Integer> dot2 = new XYChart.Series<>();
		
		for(int i =0; i<dayIncomeList.size(); i++) {//일수만큼 돌기
			dot2.getData().add(new XYChart.Data<>(dayNames.get(i), dayIncomeList.get(i).getPrice() ));
												//i일에 i일날 총수입 값 넣기
		}

        dayIncomeChart.getData().add(dot2);
        
	}
	
	
	public void CloseAction(ActionEvent e) {
		btnClose.getScene().getWindow().hide();
	}
	

}
