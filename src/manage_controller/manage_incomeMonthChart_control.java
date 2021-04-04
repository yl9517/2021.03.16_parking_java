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
		
		//�޷� �����̸����� ��������
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(months));
		
		month.setCategories(monthNames);
	}

	
	public void SetIncome(String thisYear, ObservableList<parkBook> yearIncomeList) {
		lookYear.setText(thisYear);

		//�޺� ��ü �����
        XYChart.Series<String, Integer> dot = new XYChart.Series<>();
        
        for(int i = 0; i<yearIncomeList.size(); i++) {//�� ������ŭ ����
        									// ���� �����Ϳ� �޷� �̸���, �ش� �� ���� �ֱ�
        	dot.getData().add(new XYChart.Data<>(monthNames.get(i), yearIncomeList.get(i).getPrice()));
        																// ����Ʈ�� i���� ����
        }
        
        monthIncomeChart.getData().add(dot);
        
	}
	
	public void closeAction() {
		btnClose.getScene().getWindow().hide();
	}
	
	
	
	
}

