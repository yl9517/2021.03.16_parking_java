package basic_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import DBconnect.DBhandle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class basic_park_control implements Initializable{

    @FXML
    private Label time,unusePark;

    @FXML
    private Button main;

    @FXML
    private Button floorB1,floorB2,floorB3;

    @FXML
    private Button A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5;

    @FXML
    private Label floor;
    private String thisCarNum = "";
    
	private Connection connection;	//db연결객체
	private DBhandle handler;
	private PreparedStatement pst;
	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	handler = new DBhandle();
    	
    	//기본으로 주차자리 비활성화
    	Button[] buttons = {A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5};
    	for(int i =0; i<buttons.length; i++) {
    		buttons[i].setDisable(true);
    	}
    	
    	//BtnUse();
    }
    
    //입차 시 데이터받기
    public void GetCarNum(String carNum) { //차번호 받기
    	thisCarNum = carNum;
    }
    
    public void BtnUse() {
    	
		try { //주차 안되어있는 자리만 버튼 활성화
			connection = handler.getConnnection();
			String sql = "SELECT * FROM parking WHERE carNum=null";
			ResultSet rs = pst.executeQuery();
				
			while(rs.next()) {
				System.out.println(rs.getString("seat"));
			}
				
		} catch (SQLException e) {
			System.out.println("오류");
		}
    	
    	
    }
    
    public void mainAction(ActionEvent e1) {
    	main.getScene().getWindow().hide(); //현재창 닫기
    	Stage main = new Stage(); //스테이지 생성
    	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_main.fxml"));
			
	    	Scene scene = new Scene(root);
	    	
	    	main.setScene(scene);
	    	main.setResizable(false);
	    	main.setTitle("일반 메인");
	    	main.show();
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	
    }
    
    
    
    
}
