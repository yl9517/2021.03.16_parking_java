package manage_controller;

import basic_controller.basic_out_control;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import basic_controller.basic_in_control;
import basic_controller.basic_out_control;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class manage_parkOne_control implements Initializable {


    @FXML
    private Button close;

    @FXML
    private TextField carNum;

    @FXML
    private Label carIn;

    @FXML
    private Label price;

  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void show(String carNumber,String date, int money) {
		//정보 받아오기 (차번호, 날짜, 돈)
		carNum.setText(carNumber);
		carIn.setText(date); //mm-dd HH:hh
		
	   	SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
		Date today = new Date();
		String nowtime = format1.format(today);

		
		basic_out_control connect = new basic_out_control();
		//요금 가져오기
		String pay = connect.price(date.substring(0,5),date.substring(6),nowtime.substring(0,10),nowtime.substring(11));
		price.setText(pay);

//		System.out.println("요금 " + pay);
	//입차 일,시간 현재 일,시간 보내주기
		
	}
	public void closeAction(ActionEvent event) {
		close.getScene().getWindow().hide();
	 }
}
