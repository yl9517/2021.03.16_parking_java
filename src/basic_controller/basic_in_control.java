package basic_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import DBconnect.DBhandle;
import constructor.Car;
import constructor.Login;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class basic_in_control implements Initializable {
    @FXML
    private Button submit;

    @FXML
    private TextField carNum;

    @FXML
    private Label car_floor,car_seat,time;

    private String saveInDate;
    private String saveInTime;
    private Button beforeBtn;
    
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	handler = new DBhandle();
    	
    	ParkbookDB();
    	ParkingDB();
    }
    public void info(String car, String f, String s, String t,Button btn) {
    	carNum.setText(car);
    	car_floor.setText(f); //층
    	car_seat.setText(s);  //자리
    	time.setText(t.substring(5)); //고객용 입차시간 mm-dd HH:hh
    	saveInDate = t.substring(0,10); //장부저장용 날짜 YYYY-MM-dd
    	saveInTime = t.substring(11); //장부저장용 시간 HH:hh
    	beforeBtn = btn;
    }
    
    public void SubmitAction() { //확인누르면 메인 돌아가기
    	submit.getScene().getWindow().hide();
    	beforeBtn.getScene().getWindow().hide();

    	Car connect = Car.getInstance();
    	
    	connect.isCar=false;
    	
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
    public void ParkbookDB() {
    	PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e3 -> {
	    	try {
	    		Login login = Login.getInstance();
	    		
	        	connection = handler.getConnnection();
	    		String sql="INSERT INTO parkbook(date,carNum,inTime,staff,finish)"+" values(?,?,?,?,?)";
	    		pst = connection.prepareStatement(sql);
	    		
	    		pst.setString(1, saveInDate);
	    		pst.setString(2, carNum.getText());
	    		pst.setString(3, saveInTime);
	    		pst.setString(4, login.isName);
	    		pst.setInt(5,0);
	    		
	    		pst.executeUpdate();
	    	}
	    	catch (Exception e) {
			}
	    });
		pt.play();
    }
    
    public void ParkingDB() {
    	PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e3 -> {
	    	try {
	    		Login login = Login.getInstance();
	    		
	        	connection = handler.getConnnection();
	    		String sql="update parking set carNum=? where floor=? AND seat=?";
	    		pst = connection.prepareStatement(sql);

	    		pst.setString(1, carNum.getText());
	    		pst.setString(2, car_floor.getText());
	    		pst.setString(3, car_seat.getText());
	    		
	    		pst.executeUpdate();
	    	}
	    	catch (Exception e) {
			}
	    });
		pt.play();
    }
    
    
}
