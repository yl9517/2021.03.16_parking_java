package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
		
		submit.setOnAction(e2 -> {
			DoSubmit(e2);
			StaffList();
		});
		
	}
	public void getbtn(Button btn) {
		beforebtn = btn;
	}
	
	public void DoSubmit(ActionEvent e) { //등록버튼 눌렀을 경우
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
		
		if(password.getText().equals("")) {
			
			elart.setText("비밀번호를 입력하세요");
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
			String sql = "INSERT INTO manage"+" VALUES(?,?,?,?,?,?,?)";
			try {
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, name.getText());
				pst.setString(2, password.getText());
				pst.setString(3, gender);
				pst.setString(4, birth.getText());
				pst.setString(5, phone.getText());
				pst.setString(6, email);
				pst.setString(7, power);
				
				pst.executeUpdate();
				
			}catch (Exception e4) {
				System.out.println("오류");
			}
				
		});
		if(elart.getText().equals("")) {
			pt.play();
			submit.getScene().getWindow().hide();
			beforebtn.getScene().getWindow().hide();
		}
		//다시 직원관리창 보여주기
	}
	public void StaffList() { //등록 성공시 직원리스트 새로 보여주기
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		pt.setOnFinished(e -> {
			try {

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
