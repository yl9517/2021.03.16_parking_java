package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Label time;

    @FXML
    private Button getoff;

    @FXML
    private MenuButton user;

    @FXML
    private MenuItem basic,manage;

    @FXML
    private Button car,carBook,income,staff;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	public void BasicAction(ActionEvent e) { //일반사용자 클릭시 이벤트
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
	public void getoffAction(ActionEvent e) {
		
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
