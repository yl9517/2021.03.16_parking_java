package basic_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import DBconnect.DBhandle;
import constructor.Car;
import constructor.staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class basic_park_control implements Initializable{

    @FXML
    private Label time,unusePark;

    @FXML
    private Button main;

    @FXML
    private Button floorB1,floorB2,floorB3;

    @FXML
    private Button A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5;

    @FXML
    private Label floor;
    private String thisCarNum="";
    
	private Connection connection;	//db���ᰴü
	private DBhandle handler;
	private PreparedStatement pst;
	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	handler = new DBhandle();
    	
    	SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
		Date date = new Date();
		String nowtime = format1.format(date);
		time.setText(nowtime);
		
    	floor.setText("B1"); //�⺻ ��
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
    
    //���� �� �����͹ޱ�
    public void GetCarNum(String carNum) { //����ȣ �ޱ�
    	thisCarNum = carNum;
    }
    
    public void CarShow() {
    	//��� ��ư�� �⺻ = Ȱ��ȭ
	    Button[] buttons = {A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,C5};
	    for(int i =0; i<buttons.length; i++) {
    		buttons[i].setDisable(false);
    	}
	    
    	Car connect = Car.getInstance();
	  
		
		try { //�����Ǿ��ִ� �ڸ��� ��ư�� ��Ȱ��ȭ
			connection = handler.getConnnection();
			String sql = "SELECT seat FROM parking WHERE floor=? AND carNum IS NOT NULL";//carnum�� null�� �ƴ��ڸ��� ����(�����Ǿ��ִ� �ڸ�)
			pst = connection.prepareStatement(sql);
			
			pst.setString(1, floor.getText());
			ResultSet rs = pst.executeQuery();
			
			int use = 0; //������� �ڸ� �ʱ�ȭ
			
    		while(rs.next()) {
        		for(int i =0; i<buttons.length; i++) {	//��ư���ڸ�ŭ i ������
    				if(buttons[i].getId().equals(rs.getString("seat"))) { //�����Ǿ������� ��Ȱ��ȭ
    					buttons[i].setDisable(true);
    					use++;
    				}
    			}
			}
    		unusePark.setText(String.valueOf(13-use)); //���� ���� ��
    		
		} catch (SQLException e) {
		}
		
		
		 if(connect.isCar==true) { //������ ����
		    //�ڸ���ư ������ ���
			for(int i =0; i<buttons.length; i++) {	//��ư���ڸ�ŭ i ������
				int index=i;
				buttons[i].setOnAction( e3 -> { //���ٽ��� ������ ���� ȣ���̹Ƿ� a=i��� �����������
					
					//�ڸ� ���� ����
					String seat = buttons[index].getId(); //�ڸ�
					Click(seat); 
					
				});
			}
		  }
    }
    
    public void Click(String s) {	//�����ڸ���ư ������ ��
       	
		try {
			//�����ð� ����
			SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
			Date date = new Date();
			String inDate = format1.format(date);
			
		
	    	Stage stage = new Stage(); //�������� ����
	    	
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("/FXML/basic_in.fxml"));
			Parent root = (Parent) loader.load();
	    	Scene scene = new Scene(root);
	    	
	    	
	    	//�����ѱ��
	    	basic_in_control data = loader.getController();
	    	data.info(thisCarNum, floor.getText(), s, inDate,main); //����ȣ,��,�ڸ�,�����ð�,��ư
	    	
	    	stage.setScene(scene);
	    	stage.setResizable(false);
	    	stage.setTitle("���� Ȯ��");
	    	stage.show();
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public void mainAction(ActionEvent e1) {
    	main.getScene().getWindow().hide(); //����â �ݱ�
    	Stage main = new Stage(); //�������� ����
    	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/basic_main.fxml"));
			
	    	Scene scene = new Scene(root);
	    	
	    	main.setScene(scene);
	    	main.setResizable(false);
	    	main.setTitle("�Ϲ� ����");
	    	main.show();
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    
    
}
