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

    private Connection connection;	//db���ᰴü
	private DBhandle handler;
	private PreparedStatement pst;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
	}
	
	public void CarAction(ActionEvent e) { //���� ��Ȳ Ŭ����
		
		try {
			car.getScene().getWindow().hide(); //����â �ݱ�
			
			Stage stage = new Stage(); //�������� ����
			stage.initModality(Modality.WINDOW_MODAL); //���� â ����
			
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_park.fxml"));
		
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("B1 ������Ȳ");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void CarInAction(ActionEvent e1) { //�������ý�

		try {
			Stage carIN = new Stage();
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_in.fxml"));
			
			Scene scene = new Scene(root);
			
			carIN.setScene(scene);
			carIN.setTitle("���� ������ȣ �Է�");
			carIN.setResizable(false);
			carIN.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void CarOutAction(ActionEvent e2) { //�������ý�

		try {
			Stage carIN = new Stage();
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_outNum.fxml"));
			
			Scene scene = new Scene(root);
			
			carIN.setScene(scene);
			carIN.setTitle("���� ������ȣ �Է�");
			carIN.setResizable(false);
			carIN.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void ManageAction(ActionEvent e2) { //�����-������ ���ý� ��й�ȣ â �߱�
		
		try {
			
			Stage pass = new Stage(); //�������� ����
			pass.initModality(Modality.WINDOW_MODAL); //���� â ����
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/manage_password.fxml"));
			Parent root = (Parent) loader.load();
		
			Scene scene = new Scene(root);
			
			//�������� â ������ �����ϱ�
			manage_pass_control data = loader.getController();
			data.Getbtn(carOut);
			
			pass.setScene(scene);
			pass.setTitle("������ ��й�ȣ");
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
