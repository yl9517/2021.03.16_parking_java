package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import DBconnect.DBhandle;
import basic_controller.basic_park_control;
import constructor.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class manage_park_control implements Initializable{
	  @FXML
	    private Label time,unusePark;

	    @FXML
	    private Button main;

	    @FXML
	    private Button floorB1,floorB2,floorB3;

	    @FXML
	    private Label floor;

	    @FXML
	    private Button C1,C2,C3,C4,C5,A1,A2,A3,A4,B1,B2,B3,B4;

	    @FXML
	    private Label A1_carnum,A2_carnum,A3_carnum,A4_carnum,B1_carnum,B2_carnum,B3_carnum,B4_carnum, C1_carnum,C2_carnum,C3_carnum,C4_carnum,C5_carnum;

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
	 public void CarShow() {
		  Button[] buttons = {A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5};
		  
    	//모든 라벨 초기화
	    Label[] labels = {A1_carnum,A2_carnum,A3_carnum,A4_carnum,B1_carnum,B2_carnum,B3_carnum,B4_carnum, C1_carnum,C2_carnum,C3_carnum,C4_carnum,C5_carnum};
	    for(int i =0; i<labels.length; i++) {
	    	labels[i].setText("");
    	}
	    
	    try {
		    connection=handler.getConnnection();
		    String sql = "select * from parking WHERE floor=? AND carNum IS NOT NULL"; //해당 층에 주차되어있는자리 선택
	    
			pst = connection.prepareStatement(sql);
			pst.setString(1, floor.getText());
			ResultSet rs = pst.executeQuery();

			int use = 0; //사용중인 자리 초기화
			while(rs.next()) {
        		for(int i =0; i<labels.length; i++) {	//라벨숫자만큼 i 돌리고
    				if(labels[i].getId().substring(0,2).equals(rs.getString("seat"))) { //주차되어있으면 텍스트 표시
    					int index = i;
    					labels[i].setText(rs.getString("carNum"));
    					use++;
    					
    					//해당 버튼 클릭시 -> 해당 자리에 주차된 주차번호 정보 전달
    					buttons[i].setOnAction(e2 ->{
    						Click(labels[index].getText()); 
    					});
    				}
    			}
			}
			unusePark.setText(String.valueOf(13-use)); //주차 가능 수
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 public void Click(String carNumber) {
		 
		 connection = handler.getConnnection();
		 try { 
			 String sql ="select * from parkbook where carNum=?";
			 pst = connection.prepareStatement(sql);
			 
			 pst.setString(1, carNumber);
			 
			 ResultSet rs = pst.executeQuery();
			 
			 String inTime = "";
			 int price = 0;
			 
			 //결과는 하나
			 while(rs.next()) {
				 inTime =rs.getString("date").substring(5)+" "+rs.getString("inTime");
				 price = rs.getInt("price"); //가격 메소드 따로 정의해서 불러오기!!!!!!!!!!!!!!!!!!!!!!!
			 }
			 
			 
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL); //현재 창 고정???
				stage.initOwner(main.getScene().getWindow());
				
				FXMLLoader loader = new FXMLLoader();

				loader.setLocation(getClass().getResource("/FXML/manage_park_one.fxml"));

				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				
				//차량번호 넘겨주기
				manage_parkOne_control connect = loader.getController();
				connect.show(carNumber,inTime,price);				
				
				stage.setScene(scene);
				stage.setTitle("차량 정보확인");
				stage.setResizable(false);
				stage.show();
				
			}catch (Exception e) {
			}
		 
	 }
	 
	 
    public void mainAction(ActionEvent e1) {
    	main.getScene().getWindow().hide(); //현재창 닫기
    	Stage main = new Stage(); //스테이지 생성
    	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_main.fxml"));
			
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
