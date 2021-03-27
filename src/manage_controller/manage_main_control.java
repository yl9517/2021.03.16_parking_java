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
import constructor.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class manage_main_control implements Initializable {


    @FXML
    private Label nowTime,worker;

    @FXML
    private Button getoff;

    @FXML
    private MenuButton user;

    @FXML
    private MenuItem basic,manage;

    @FXML
    private Button car,carBook,income,staff;
    
    private String pw;
    
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    private int lastNum;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		Time();
		//사용자-일반 눌렀을때
		basic.setOnAction(e -> GoMain(e)); //메인
		
		//퇴근버튼 눌렀을때
		getoff.setOnAction(e2 -> {
			Getoff(); //퇴근처리
			GoMain(e2); //메인
		});
	}
	public void Time() {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
		Date time = new Date();
		String thistime = format.format(time);
		
		nowTime.setText(thistime);
		
	}
	
	public void Work(String name) { //정보받아오기
		worker.setText(name);
	}
	
	public void Getoff() { //퇴근처리
		try {
			//마지막 행 근태정보 선택
			connection = handler.getConnnection();
			String sql = "SELECT * FROM work ORDER BY num DESC LIMIT 1"; //select 정보 from 테이블 order by 기준컬럼명 오름/내림차순 limit (0번째부터) 1개
			pst = connection.prepareStatement(sql);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				lastNum = rs.getInt("num");
			}
			
			//시간 불러오기
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat ( "HH:mm:ss");
			Date time1 = new Date();
			Date time2 = new Date();
			String endDay = format1.format(time1);
			String endTime = format2.format(time2);
			
			//퇴근시간 근태DB 저장
			sql = "UPDATE work SET endDay=?,endTime=? where num=?";
			
			pst= connection.prepareStatement(sql);
			pst.setString(1,endDay);
			pst.setString(2,endTime);
			pst.setInt(3,lastNum);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//로그아웃으로 변경
		Login login = Login.getInstance();
		login.isLogin=false;
		
	}
	
	public void GoMain(ActionEvent e) { //메인페이지 이동
		getoff.getScene().getWindow().hide();
		Stage stage = new Stage();
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_main.fxml"));
		
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("메인_일반");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void CarAction(ActionEvent e) { //주차현황 클릭시 이벤트
		car.getScene().getWindow().hide();
		Stage stage = new Stage();
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_park.fxml"));
		
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("주차현황_관리자ver");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void CarBookAction(ActionEvent e1) { //주차장부 클릭시 이벤트
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_parkingBook.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("주차장부");
			stage.setResizable(false);
			stage.show();
		
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	public void IncomeAction(ActionEvent e2) { //수입 클릭시 이벤트
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_income.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("수입");

			stage.show();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}
	public void StaffAction(ActionEvent e3) { //직원관리 클릭시 이벤트
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_staff2.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("직원관리");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
	}
	
}
