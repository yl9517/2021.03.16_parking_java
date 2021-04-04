package manage_controller;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import constructor.parkBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class manage_incomeMonthChart_control implements Initializable {
    @FXML
    private Button btnClose;

    @FXML
    private Label lookYear;

    @FXML
    private LineChart<String, Integer> monthIncomeChart;

    @FXML
    private CategoryAxis month;

    @FXML
    private NumberAxis income;
	
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//달력 영어이름으로 가져오기
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(months));
		
		month.setCategories(monthNames);
	}

	
	public void SetIncome(String thisYear, ObservableList<parkBook> yearIncomeList) {
		lookYear.setText(thisYear);

		//달별 객체 만들기
        XYChart.Series<String, Integer> dot = new XYChart.Series<>();
        
        for(int i = 0; i<yearIncomeList.size(); i++) {//달 갯수만큼 돌기
        									// 수입 데이터에 달력 이름과, 해당 달 수입 넣기
        	dot.getData().add(new XYChart.Data<>(monthNames.get(i), yearIncomeList.get(i).getPrice()));
        																// 리스트의 i월의 수입
        }
        
        monthIncomeChart.getData().add(dot);
        
	}
	
	public void closeAction() {
		btnClose.getScene().getWindow().hide();
	}
	
	
	
	
}

