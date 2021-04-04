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
    private CategoryAxis time,time1; //������Ʈ,�� ��Ʈ �ð� ��
    @FXML
    private NumberAxis countCar,countCar1; //������Ʈ,�� ��Ʈ �� ���� ��
    @FXML
    private Button btnClose,btnClose1;
    @FXML
    private Label day,month;

    private ObservableList<String> timeNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    	 String[] times = {"00��","01��","02��","03��","04��","05��","06��","07��","08��","09��","10��","11��","12��","13��","14��","15��","16��","17��","18��","19��","20��","21��","22��","23��","24��"};
    	 timeNames.addAll(Arrays.asList(times));
    	 
    	 time.setCategories(timeNames);
    	
    	 //��¥
 		SimpleDateFormat format = new SimpleDateFormat ( "MM / dd");
 		Date time = new Date();
 		String thistime = format.format(time);
 		day.setText(thistime);
 		month.setText(thistime.substring(0,2));
    }
    
    public void SetCountCar(List<parkBook> daycars,List<parkBook> monthcars) {
      //������Ʈ
	    	//�����ð� ������
	    	int[] intimeCount = new int[24];
	    	
	    	for(parkBook p : daycars) {
	    		int intimeHH = Integer.parseInt(p.getInTime().substring(0,2)); // �����ð� �� ���ڸ�
	    		intimeCount[intimeHH]++; //intimeCount[3] = 03�ÿ� ������ ���� ��� �÷��� ++;
	    	}
	
		    	//�ð����� �����ð� ��ü �����
		    	XYChart.Series<String, Integer> inCar = new XYChart.Series<>();
		    	inCar.setName("����");
		    	for(int i = 0; i< intimeCount.length; i++) {
		    		inCar.getData().add(new XYChart.Data<>(timeNames.get(i), intimeCount[i]));
		    	}
		    	
		    	dayChart.getData().add(inCar);
	    	
		    	
	    	//���� �ð� ������
		    int[] outtimeCount = new int[24];
		    
	    	for(parkBook p : daycars) {
	    		int outtimeHH = Integer.parseInt(p.getOutTime().substring(0,2)); //�����ð�
	    		outtimeCount[outtimeHH]++;
	    	}
	
		    	//�ð����� �����ð� ��ü �����
		    	XYChart.Series<String, Integer> outCar = new XYChart.Series<>();
		    	outCar.setName("����");
		    	
		    	for(int i = 0; i< outtimeCount.length; i++) {
		    		outCar.getData().add(new XYChart.Data<>(timeNames.get(i), outtimeCount[i]));
		    	}
	    	
		    	dayChart.getData().add(outCar);
	    	
		    //�����Ʈ
		    	//�����ð� ������
		    	int[] intimeCountm = new int[24];
		    	
		    	for(parkBook p : monthcars) {
		    		int intimeHH = Integer.parseInt(p.getInTime().substring(0,2)); // �����ð� �� ���ڸ�
		    		intimeCountm[intimeHH]++;
		    	}
		
			    	//�ð����� �����ð� ��ü �����
			    	XYChart.Series<String, Integer> inCarm = new XYChart.Series<>();
			    	inCar.setName("����");
			    	for(int i = 0; i< intimeCountm.length; i++) {
			    		inCarm.getData().add(new XYChart.Data<>(timeNames.get(i), intimeCountm[i]));
			    	}
			    	
			    	monthChart.getData().add(inCarm);
		    	
			    	
		    	//���� �ð� ������
			    int[] outtimeCountm = new int[24];
			    
		    	for(parkBook p : monthcars) {
		    		int outtimeHH = Integer.parseInt(p.getOutTime().substring(0,2)); //�����ð�
		    		outtimeCountm[outtimeHH]++;
		    	}
		
			    	//�ð����� �����ð� ��ü �����
			    	XYChart.Series<String, Integer> outCarm = new XYChart.Series<>();
			    	outCarm.setName("����");
			    	
			    	for(int i = 0; i< outtimeCountm.length; i++) {
			    		outCarm.getData().add(new XYChart.Data<>(timeNames.get(i), outtimeCountm[i]));
			    	}
		    	
			    	monthChart.getData().add(outCarm);
    	
    }
    public void CloseAction(ActionEvent e) {
    	btnClose.getScene().getWindow().hide();
    	
    }
    
    
    
}
