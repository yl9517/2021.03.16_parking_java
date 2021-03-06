package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import DBconnect.DBhandle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class manage_updateStaff_control implements Initializable{


    @FXML
    private TextField name,phone,birth;

    @FXML
    private PasswordField password;
    
    @FXML
    private ToggleGroup group;
    private String gender;
    @FXML
    private RadioButton woman,man;
    

    @FXML
    private TextField email1;
    @FXML
    private ChoiceBox<String> email2;
    private String email="";
    
    ObservableList<String> emailList;


    @FXML
    private ToggleGroup group2;
    private String power;
    @FXML
    private RadioButton staff, all;


    
    @FXML
    private Button submit,cancel;
    private Button beforebtn;
    private String isPass;

    @FXML
    private Label elart;
    
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		emailList = FXCollections.observableArrayList("naver.com","daum.net","gmail.com","nate.com");
		 email2.setItems(emailList);
		
		handler = new DBhandle();
		ShowInfo();
		
		submit.setOnAction(e2 -> {
			DoSubmit(e2);
			StaffList(); //???? ?????????? ????????
		});
		
	}
	public void getDate(String pass, Button btn) {
		isPass = pass; //?????????? ????????
		beforebtn = btn;
	}
	public void ShowInfo() { 
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(0.1));
		pt.setOnFinished(e -> {
		//???? ?????????? ???????? ???? ????????
		connection = handler.getConnnection();
		String sql = "select * from manage where password=?";

		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, isPass);//?????? ????(???????? ????)
			ResultSet rs = pst.executeQuery();
			
			//????????????
			while(rs.next()) {
				name.setText(rs.getString("name"));
				password.setText(rs.getString("password"));
				birth.setText(rs.getString("birth"));
				phone.setText(rs.getString("phone"));
				gender = rs.getString("gender");
				email1.setText(rs.getString("email").split("@")[0]);
				email2.setValue(rs.getString("email").split("@")[1]);
			}
			password.setDisable(true);
			

			if(gender.equals("??")) {
				woman.setSelected(true);
				man.setSelected(false);
			}
			else {
				woman.setSelected(false);
				man.setSelected(true);
			}
			
		}catch (Exception e4) {
			System.out.println("????");
		}
		});
		pt.play();
	}
	
	public void DoSubmit(ActionEvent e) { //???????? ?????? ????
		elart.setText(""); //??????
	
		email = email1.getText()+"@"+email2.getValue();
		
		//????
		if( woman.isSelected() == true) {
			gender = "??";
		}
		else {
			gender ="??";
		}
		
		//????
		if(staff.isSelected()==true) {
			power="Staff";
		}
		else {
			power="All";
		}
		
		//???? ?????? ???? ????
		if(name.getText().equals("")) {
			elart.setText("?????? ??????????");
		}

		//????
		if(!birth.getText().equals("") ) {
			try {
				int year = Integer.parseInt(birth.getText().substring(0,4));
				int month = Integer.parseInt(birth.getText().substring(4,6));
				int day = Integer.parseInt(birth.getText().substring(6));
				
				if(month>12 || day>31 || birth.getLength()!=8) {
					elart.setText("?????? ??????????????.");
				}
			}catch (Exception e3) {
				elart.setText("?????? ??????????????.");
			}
		}
		else {
			elart.setText("?????????? ??????????.");
		}
		
		if(phone.getText().equals("")) {
			elart.setText("???????? ??????????.");
		}
		if(email1.getText().equals("") || email2.getValue()==null) {
			elart.setText("???????? ??????????.");
		}
		
		
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e3 -> {
			connection = handler.getConnnection();
			String sql = "update manage set name=?,birth=?,phone=?,email=?, power=? where password=?";
	
			try {
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, name.getText());
				pst.setString(2, birth.getText());
				pst.setString(3, phone.getText());
				pst.setString(4, email);
				pst.setString(5, power);
				pst.setString(6, isPass);
				
				pst.executeUpdate();
				
				
			}catch (Exception e4) {
				System.out.println("????");
			}
				
		});
		if(elart.getText().equals("")) {
			pt.play();
		}
	}
	
	public void StaffList() { // ?????????? ???? ????????
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		pt.setOnFinished(e -> {
			try {
				
				submit.getScene().getWindow().hide();
				beforebtn.getScene().getWindow().hide();
				
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_staff2.fxml"));
				
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setTitle("????????");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e4) {
				e4.printStackTrace();
			}
		});
		pt.play();
	}
	public void cancelAction(ActionEvent e3) { //????????
		cancel.getScene().getWindow().hide();
		
	}
	
}
