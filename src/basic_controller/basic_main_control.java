package basic_controller;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import DBconnect.DBhandle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manage_controller.manage_pass_control;

public class basic_main_control implements Initializable {
    @FXML
    private Label nowTime;

    @FXML
    private MenuButton user;

    @FXML
    private MenuItem basic;

    @FXML
    private MenuItem manage;

    @FXML
    private Button carIn,car,carOut;

    private Connection connection;	//db연결객체
	private DBhandle handler;
	private PreparedStatement pst;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
	}
	
	public void CarAction(ActionEvent e) { //주차 현황 클릭시
		
		try {
			car.getScene().getWindow().hide(); //현재창 닫기
			
			Stage stage = new Stage(); //스테이지 생성
			stage.initModality(Modality.WINDOW_MODAL); //현재 창 고정
			
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_park.fxml"));
		
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("B1 주차현황");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void CarInAction(ActionEvent e1) { //입차선택시

		try {
			Stage carIN = new Stage();
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_in.fxml"));
			
			Scene scene = new Scene(root);
			
			carIN.setScene(scene);
			carIN.setTitle("입차 차량번호 입력");
			carIN.setResizable(false);
			carIN.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void CarOutAction(ActionEvent e2) { //출차선택시

		try {
			Stage carIN = new Stage();
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_outNum.fxml"));
			
			Scene scene = new Scene(root);
			
			carIN.setScene(scene);
			carIN.setTitle("출차 차량번호 입력");
			carIN.setResizable(false);
			carIN.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void ManageAction(ActionEvent e2) { //사용자-관리자 선택시 비밀번호 창 뜨기
		
		try {
			
			Stage pass = new Stage(); //스테이지 생성
			pass.initModality(Modality.WINDOW_MODAL); //현재 창 고정
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/manage_password.fxml"));
			Parent root = (Parent) loader.load();
		
			Scene scene = new Scene(root);
			
			//스테이지 창 데이터 전송하기
			manage_pass_control data = loader.getController();
			data.Getbtn(carOut);
			
			pass.setScene(scene);
			pass.setTitle("관리자 비밀번호");
			pass.setResizable(false);
			pass.show();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}	
	}

	private Parent loder(URL resource) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
