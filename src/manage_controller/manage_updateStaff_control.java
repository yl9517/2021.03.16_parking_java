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
			StaffList(); //다시 직원관리창 보여주기
		});
		
	}
	public void getDate(String pass, Button btn) {
		isPass = pass; //선택한사람 비밀번호
		beforebtn = btn;
	}
	public void ShowInfo() { 
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(0.1));
		pt.setOnFinished(e -> {
		//해당 비밀번호에 해당하는 정보 끌어오기
		connection = handler.getConnnection();
		String sql = "select * from manage where password=?";

		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, isPass);//선택한 정보(전달받은 정보)
			ResultSet rs = pst.executeQuery();
			
			//정보가져오기
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
			

			if(gender.equals("여")) {
				woman.setSelected(true);
				man.setSelected(false);
			}
			else {
				woman.setSelected(false);
				man.setSelected(true);
			}
			
		}catch (Exception e4) {
			System.out.println("오류");
		}
		});
		pt.play();
	}
	
	public void DoSubmit(ActionEvent e) { //수정버튼 눌렀을 경우
		elart.setText(""); //초기값
	
		email = email1.getText()+"@"+email2.getValue();
		
		//성별
		if( woman.isSelected() == true) {
			gender = "여";
		}
		else {
			gender ="남";
		}
		
		//권한
		if(staff.isSelected()==true) {
			power="Staff";
		}
		else {
			power="All";
		}
		
		//빈칸 있으면 오류 알림
		if(name.getText().equals("")) {
			elart.setText("이름을 입력하세요");
		}

		//생일
		if(!birth.getText().equals("") ) {
			try {
				int year = Integer.parseInt(birth.getText().substring(0,4));
				int month = Integer.parseInt(birth.getText().substring(4,6));
				int day = Integer.parseInt(birth.getText().substring(6));
				
				if(month>12 || day>31 || birth.getLength()!=8) {
					elart.setText("잘못된 생년월일입니다.");
				}
			}catch (Exception e3) {
				elart.setText("잘못된 생년월일입니다.");
			}
		}
		else {
			elart.setText("생년월일을 입력하세요.");
		}
		
		if(phone.getText().equals("")) {
			elart.setText("연락처를 입력하세요.");
		}
		if(email1.getText().equals("") || email2.getValue()==null) {
			elart.setText("이메일을 입력하세요.");
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
				System.out.println("오류");
			}
				
		});
		if(elart.getText().equals("")) {
			pt.play();
		}
	}
	
	public void StaffList() { // 직원리스트 새로 보여주기
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
				stage.setTitle("직원관리");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e4) {
				e4.printStackTrace();
			}
		});
		pt.play();
	}
	public void cancelAction(ActionEvent e3) { //취소버튼
		cancel.getScene().getWindow().hide();
		
	}
	
}
