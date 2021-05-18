package basic_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import DBconnect.DBhandle;
import constructor.Car;
import constructor.staff;
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
    private String thisCarNum="";
    
	private Connection connection;	//db연결객체
	private DBhandle handler;
	private PreparedStatement pst;
	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	handler = new DBhandle();
    	
    	SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
		Date date = new Date();
		String nowtime = format1.format(date);
		time.setText(nowtime);
		
    	floor.setText("B1"); //기본 층
    	CarShow();

    	floorB1.setOnAction( e1 -> {
    		floor.setText("B1");
			floorB1.setStyle("-fx-background-color:silver;");
			floorB2.setStyle("-fx-background-color:");
			floorB3.setStyle("-fx-background-color:");

	    	CarShow();
    	});
    	floorB2.setOnAction( e2 -> {
    		floor.setText("B2");
			floorB2.setStyle("-fx-background-color:silver;");
			floorB1.setStyle("-fx-background-color:");
			floorB3.setStyle("-fx-background-color:");
	    	CarShow();
    	});
    	floorB3.setOnAction( e3 -> {
    		floor.setText("B3");
			floorB3.setStyle("-fx-background-color:silver;");
			floorB2.setStyle("-fx-background-color:");
			floorB1.setStyle("-fx-background-color:");
	    	CarShow();
    	});
    	

    }
    
    //입차 시 데이터받기
    public void GetCarNum(String carNum) { //차번호 받기
    	thisCarNum = carNum;
    }
    
    public void CarShow() {
    	//모든 버튼의 기본 = 활성화
	    Button[] buttons = {A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5};
	    for(int i =0; i<buttons.length; i++) {
    		buttons[i].setDisable(false);
    	}
	    
    	Car connect = Car.getInstance();
	  
		
		try { //주차되어있는 자리의 버튼은 비활성화
			connection = handler.getConnnection();
			String sql = "SELECT seat FROM parking WHERE floor=? AND carNum IS NOT NULL";//carnum이 null이 아닌자리를 선택(주차되어있는 자리)
			pst = connection.prepareStatement(sql);
			
			pst.setString(1, floor.getText());
			ResultSet rs = pst.executeQuery();
			
			int use = 0; //사용중인 자리 초기화
			
    		while(rs.next()) {
        		for(int i =0; i<buttons.length; i++) {	//버튼숫자만큼 i 돌리고
    				if(buttons[i].getId().equals(rs.getString("seat"))) { //주차되어있으면 비활성화
    					buttons[i].setDisable(true);
    					use++;
    				}
    			}
			}
    		unusePark.setText(String.valueOf(13-use)); //주차 가능 수
    		
		} catch (SQLException e) {
		}
		
		
		 if(connect.isCar==true) { //입차된 차가
		    //자리버튼 눌렀을 경우
			for(int i =0; i<buttons.length; i++) {	//버튼숫자만큼 i 돌리고
				int index=i;
				buttons[i].setOnAction( e3 -> { //람다식은 참조에 의한 호출이므로 a=i라고 지정해줘야함
					
					//자리 정보 전달
					String seat = buttons[index].getId(); //자리
					Click(seat); 
					
				});
			}
		  }
    }
    
    public void Click(String s) {	//주차자리버튼 눌렀을 때
       	
		try {
			//입차시간 저장
			SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
			Date date = new Date();
			String inDate = format1.format(date);
			
		
	    	Stage stage = new Stage(); //스테이지 생성
	    	
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("/FXML/basic_in.fxml"));
			Parent root = (Parent) loader.load();
	    	Scene scene = new Scene(root);
	    	
	    	
	    	//정보넘기기
	    	basic_in_control data = loader.getController();
	    	data.info(thisCarNum, floor.getText(), s, inDate,main); //차번호,층,자리,입차시간,버튼
	    	
	    	stage.setScene(scene);
	    	stage.setResizable(false);
	    	stage.setTitle("주차 확인");
	    	stage.show();
	    	
		} catch (IOException e) {
			e.printStackTrace();
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
