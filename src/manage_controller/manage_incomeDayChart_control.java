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
	public void SetIncomeChart(String thisYear, int thisMonth, ObservableList<parkBook> dayIncomeList) { //�� �޾ƿ���
		lookMonth.setText(String.format("%02d", thisMonth)); //���ڸ� ���缭 ����
		foundMonth = thisYear+String.format("%02d", thisMonth)+"01";
		
		//�޷� x�� �̸� ����
		YearMonth findDay = YearMonth.from(LocalDate.parse(foundMonth,DateTimeFormatter.ofPattern("yyyyMMdd")));
		for(int i =1; i<=findDay.lengthOfMonth(); i++) {
			dayNames.addAll(String.valueOf(i));
		}
		
		day.setCategories(dayNames);
		
		
		//�Ϻ� ��ü �����
		XYChart.Series<String, Integer> dot2 = new XYChart.Series<>();
		
		for(int i =0; i<dayIncomeList.size(); i++) {//�ϼ���ŭ ����
			dot2.getData().add(new XYChart.Data<>(dayNames.get(i), dayIncomeList.get(i).getPrice() ));
												//i�Ͽ� i�ϳ� �Ѽ��� �� �ֱ�
		}

        dayIncomeChart.getData().add(dot2);
        
	}
	
	
	public void CloseAction(ActionEvent e) {
		btnClose.getScene().getWindow().hide();
	}
	

}
