package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.mysql.cj.xdevapi.DbDoc;
import com.sun.javafx.logging.Logger;

import DBconnect.DBhandle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import constructor.*;

public class manage_staff_control implements Initializable{

	@FXML
    private Tab staffManage;

    @FXML
    private TableView<staff> staffview;

    @FXML
    private TableColumn<staff, String> name,password,gender,birth,phone,email,power;

    @FXML
    private Button addStaff,deleteStaff,updateStaff;

    @FXML
    private Button submit;

    @FXML
    private TextField keyword;

    @FXML
    private Tab staffwork;

    @FXML
    private TableView<?> work;

    @FXML
    private TableColumn<?, ?> startDay,startTime,endDay,endTime,worker;

    @FXML
    private Button submit2;

    @FXML
    private TextField keyword2;

    private ObservableList<staff> oblist = FXCollections.observableArrayList(); //���̺� ����Ʈ
    private ObservableList<staff> selectStaff;
	    
	    private Connection connection;
	    private DBhandle handler;
	    private PreparedStatement pst;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
		showstaff();
		
		deleteStaff.setOnAction(e3 ->{
		});
	}
	
	public void showstaff() {
		try {
			connection = handler.getConnnection();
			String sql = "select * from manage";
			
			pst = connection.prepareStatement(sql);
			
			ResultSet rs = pst.executeQuery();
			
			 while (rs.next()) {
	              oblist.add(new staff(rs.getString("name"), rs.getString("password"),rs.getString("gender")
	            		 ,rs.getString("birth"),rs.getString("phone"), rs.getString("email"), rs.getString("power")));
	                //oblist�� staffŬ������ ���� ����Ʈ �̹Ƿ� staff Ŭ�������¸� �߰� �� �� ���� ( => new ��¼��)
	            }
		} catch (SQLException ex) {
		}
		
		//�ش� �ݷҿ� "�������� ��" ���̰� ����
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		password.setCellValueFactory(new PropertyValueFactory<>("password"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		gender.setStyle(" -fx-alignment : center; ");
		birth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		power.setCellValueFactory(new PropertyValueFactory<>("power"));
		
		//staffview�� ����Ʈ�� �־��ֱ�
		staffview.setItems(oblist);
		
	}
	public void worker() {
		
		
	}
	
	public void addAction(ActionEvent e) { //�����߰���ư
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/allmanage_addStaff2.fxml"));
		
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//�����������ϱ�(�ش� �������� ����� �ִ� ��ư ����)
			manage_addStaff_control data = loader.getController();
			data.getbtn(addStaff);
			
			stage.setScene(scene);
			stage.setTitle("���� ���");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateAction(ActionEvent e) { //����������ư
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/allmanage_updateManager.fxml"));
		
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("���� ����");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void deleteAction(ActionEvent e) { //������������
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e2 -> {
			connection = handler.getConnnection();
			String sql = "delete from manage where password=?";
			try {
				pst = connection.prepareStatement(sql);
				pst.setString(1, "123");//�� ����� ��й�ȣ
				
				pst.executeUpdate();
				
				
			}catch (Exception e4) {
				// TODO: handle exception
			}
			
		});
		
	}
	
	
}
