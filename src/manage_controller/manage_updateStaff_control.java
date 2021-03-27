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
	
	public void DoSubmit(ActionEvent e) { //��Ϲ�ư ������ ���
		elart.setText(""); //�ʱⰪ
		email = email1.getText()+"@"+email2.getValue();
		
		//����
		if( woman.isSelected() == true) {
			gender = "��";
		}
		else {
			gender ="��";
		}
		
		//����
		if(staff.isSelected()==true) {
			power="Staff";
		}
		else {
			power="All";
		}
		
		//��ĭ ������ ���� �˸�
		if(name.getText().equals("")) {
			elart.setText("�̸��� �Է��ϼ���");
		}
		
		if(password.getText().equals("")) {
			
			elart.setText("��й�ȣ�� �Է��ϼ���");
		}	
		//����
		if(!birth.getText().equals("") ) {
			try {
				int year = Integer.parseInt(birth.getText().substring(0,4));
				int month = Integer.parseInt(birth.getText().substring(4,6));
				int day = Integer.parseInt(birth.getText().substring(6));
				
				if(month>12 || day>31 || birth.getLength()!=8) {
					elart.setText("�߸��� ��������Դϴ�.");
				}
			}catch (Exception e3) {
				elart.setText("�߸��� ��������Դϴ�.");
			}
		}
		else {
			elart.setText("��������� �Է��ϼ���.");
		}
		
		if(phone.getText().equals("")) {
			elart.setText("����ó�� �Է��ϼ���.");
		}
		if(email1.getText().equals("") || email2.getValue()==null) {
			elart.setText("�̸����� �Է��ϼ���.");
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
				System.out.println("����");
			}
				
		});
		if(elart.getText().equals("")) {
			pt.play();
			submit.getScene().getWindow().hide();
			beforebtn.getScene().getWindow().hide();
		}
		//�ٽ� ��������â �����ֱ�
	}
	public void StaffList() { //��� ������ ��������Ʈ ���� �����ֱ�
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		pt.setOnFinished(e -> {
			try {

				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_staff2.fxml"));
				
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setTitle("��������");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e4) {
				e4.printStackTrace();
			}
		});
		pt.play();
	}
	public void cancelAction(ActionEvent e3) { //��ҹ�ư
		cancel.getScene().getWindow().hide();
		
	}
	
}
