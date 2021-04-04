package manage_controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import constructor.parkBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class manage_parkingBookChart_control implements Initializable{
	
	
    @FXML
    private javafx.scene.chart.BarChart<String, Integer> dayChart,monthChart;
    @FXML
    private CategoryAxis time,time1; //당일차트,월 차트 시간 행
    @FXML
    private NumberAxis countCar,countCar1; //당일차트,월 차트 차 갯수 열
    @FXML
    private Button btnClose,btnClose1;
    @FXML
    private Label day,month;

    private ObservableList<String> timeNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    	 String[] times = {"00시","01시","02시","03시","04시","05시","06시","07시","08시","09시","10시","11시","12시","13시","14시","15시","16시","17시","18시","19시","20시","21시","22시","23시","24시"};
    	 timeNames.addAll(Arrays.asList(times));
    	 
    	 time.setCategories(timeNames);
    	
    	 //날짜
 		SimpleDateFormat format = new SimpleDateFormat ( "MM / dd");
 		Date time = new Date();
 		String thistime = format.format(time);
 		day.setText(thistime);
 		month.setText(thistime.substring(0,2));
    }
    
    public void SetCountCar(List<parkBook> daycars,List<parkBook> monthcars) {
      //당일차트
	    	//입차시간 뺴오기
	    	int[] intimeCount = new int[24];
	    	
	    	for(parkBook p : daycars) {
	    		int intimeHH = Integer.parseInt(p.getInTime().substring(0,2)); // 입차시간 앞 두자리
	    		intimeCount[intimeHH]++; //intimeCount[3] = 03시에 입차한 차량 계속 플러스 ++;
	    	}
	
		    	//시간별로 입차시간 객체 만들기
		    	XYChart.Series<String, Integer> inCar = new XYChart.Series<>();
		    	inCar.setName("입차");
		    	for(int i = 0; i< intimeCount.length; i++) {
		    		inCar.getData().add(new XYChart.Data<>(timeNames.get(i), intimeCount[i]));
		    	}
		    	
		    	dayChart.getData().add(inCar);
	    	
		    	
	    	//출차 시간 빼오기
		    int[] outtimeCount = new int[24];
		    
	    	for(parkBook p : daycars) {
	    		int outtimeHH = Integer.parseInt(p.getOutTime().substring(0,2)); //출차시간
	    		outtimeCount[outtimeHH]++;
	    	}
	
		    	//시간별로 출차시간 객체 만들기
		    	XYChart.Series<String, Integer> outCar = new XYChart.Series<>();
		    	outCar.setName("출차");
		    	
		    	for(int i = 0; i< outtimeCount.length; i++) {
		    		outCar.getData().add(new XYChart.Data<>(timeNames.get(i), outtimeCount[i]));
		    	}
	    	
		    	dayChart.getData().add(outCar);
	    	
		    //당월차트
		    	//입차시간 뺴오기
		    	int[] intimeCountm = new int[24];
		    	
		    	for(parkBook p : monthcars) {
		    		int intimeHH = Integer.parseInt(p.getInTime().substring(0,2)); // 입차시간 앞 두자리
		    		intimeCountm[intimeHH]++;
		    	}
		
			    	//시간별로 입차시간 객체 만들기
			    	XYChart.Series<String, Integer> inCarm = new XYChart.Series<>();
			    	inCar.setName("입차");
			    	for(int i = 0; i< intimeCountm.length; i++) {
			    		inCarm.getData().add(new XYChart.Data<>(timeNames.get(i), intimeCountm[i]));
			    	}
			    	
			    	monthChart.getData().add(inCarm);
		    	
			    	
		    	//출차 시간 빼오기
			    int[] outtimeCountm = new int[24];
			    
		    	for(parkBook p : monthcars) {
		    		int outtimeHH = Integer.parseInt(p.getOutTime().substring(0,2)); //출차시간
		    		outtimeCountm[outtimeHH]++;
		    	}
		
			    	//시간별로 출차시간 객체 만들기
			    	XYChart.Series<String, Integer> outCarm = new XYChart.Series<>();
			    	outCarm.setName("출차");
			    	
			    	for(int i = 0; i< outtimeCountm.length; i++) {
			    		outCarm.getData().add(new XYChart.Data<>(timeNames.get(i), outtimeCountm[i]));
			    	}
		    	
			    	monthChart.getData().add(outCarm);
    	
    }
    public void CloseAction(ActionEvent e) {
    	btnClose.getScene().getWindow().hide();
    	
    }
    
    
    
}
