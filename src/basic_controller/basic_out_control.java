package basic_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.PreparableStatement;

import DBconnect.DBhandle;
import DBconnect.config;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class basic_out_control implements Initializable{
  
    @FXML
    private TextField carNum;

    @FXML
    private Label car_floor,car_seat;

    @FXML
    private Label car_in,car_out,car_price,cancel;

    @FXML
    private Button pay;

    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
    public String outTime = "";
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		show();
		
		//취소누르면 창닫기
		cancel.setOnMouseClicked(event -> {
		cancel.getScene().getWindow().hide();}); 
		
	}
	public void initData(String num,String floor, String seat) { //정보받아오기
		carNum.setText(num);
		car_floor.setText(floor);
		car_seat.setText(seat);
		//현재시간 받아서 outTime에 담기
		
	}
	public void show() {
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e1 ->{
			connection = handler.getConnnection();
			String sql = "select * from parkbook where finish=0 and carNum=?";
			try {
				pst = connection.prepareStatement(sql);
				pst.setString(1, carNum.getText());
				
				ResultSet rs = pst.executeQuery();
				
				if(rs.next()) {
					String inTime = rs.getString("inTime");
					String price = rs.getString("price");
					
					car_in.setText(inTime);
					car_out.setText(outTime);
					car_price.setText(price);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		pt.play();
	}
	
	@FXML
    void payAction(ActionEvent event) {

    }
}
