
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
import basic_controller.basic_inNum_control;
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
import javafx.scene.control.Label;
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
    private TableView<work> workview;

    @FXML
    private TableColumn<work, String> startDay,startTime,endDay,endTime,worker;

    @FXML
    private Button submit2;

    @FXML
    private TextField keyword2;
    @FXML
    private Label alert;

    private ObservableList<staff> oblist = FXCollections.observableArrayList(); //테이블 리스트

    private ObservableList<work> workList = FXCollections.observableArrayList(); //테이블 리스트
    
	    private Connection connection;
	    private DBhandle handler;
	    private PreparedStatement pst;
	    
	    private int key = 0;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
		showstaff();
		showworker();
		
		Login login = Login.getInstance();
		
		if(login.isPower.equals("Staff")) {
			addStaff.setDisable(true);
			deleteStaff.setDisable(true);
			updateStaff.setDisable(true);
		}
		
		
		submit.setOnAction(e3 -> {
			if(!keyword.getText().equals(null))  //검색어가 있따면
				key =1;
			showstaff();
		});
		submit2.setOnAction(e3 -> {
			if(!keyword2.getText().equals(null))  //검색어가 있따면
				key =2;
			showworker();
		});
		
	}
	
	public void showstaff() {
		oblist.clear();
		connection = handler.getConnnection();
		try {
			String sql = "select * from manage";
			pst = connection.prepareStatement(sql);
			
			if(key==1) { //검색어 있을 경우
				sql="select * from manage where name like ?";
				pst = connection.prepareStatement(sql);
				pst.setString(1, "%"+keyword.getText()+"%");
			}
			
			ResultSet rs = pst.executeQuery();
			
			 while (rs.next()) {
	              oblist.add(new staff(rs.getString("name"), rs.getString("password"),rs.getString("gender")
	            		 ,rs.getString("birth"),rs.getString("phone"), rs.getString("email"), rs.getString("power")));
	                //oblist는 staff클래스를 담은 리스트 이므로 staff 클래스형태만 추가 될 수 있음 ( => new 어쩌구)
	            }
		} catch (SQLException ex) {
		}
		
		//해당 콜롬에 "지정해준 값" 보이게 세팅
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		password.setCellValueFactory(new PropertyValueFactory<>("password"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		gender.setStyle(" -fx-alignment : center; ");
		birth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		power.setCellValueFactory(new PropertyValueFactory<>("power"));
		
		//staffview에 리스트값 넣어주기
		staffview.setItems(oblist);
	}
	
	
	public void AddAction(ActionEvent e) { //직원추가버튼
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/allmanage_addStaff2.fxml"));
		
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//데이터전송하기(해당 스테이지 숨길수 있는 버튼 전송)
			manage_addStaff_control data = loader.getController();
			data.getbtn(addStaff);
			
			stage.setScene(scene);
			stage.setTitle("직원 등록");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void UpdateAction(ActionEvent e) { //직원수정버튼
		try {
			String isPass = staffview.getSelectionModel().getSelectedItem().getPassword();
			
				Stage stage = new Stage();
				
				FXMLLoader loader = new FXMLLoader();
				
				loader.setLocation(getClass().getResource("/FXML/allmanage_updateManager.fxml"));
				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				
				manage_updateStaff_control connect = loader.getController();
				connect.getDate(isPass, updateStaff); //사람 정보, 버튼 전송
				
				stage.setScene(scene);
				stage.setTitle("직원 수정");
				stage.setResizable(false);
				stage.show();
				
		}catch (Exception e2) {
			alert.setText("직원을 선택하세요");
		}
		
	}
	
	public void DeleteAction(ActionEvent event) { //삭제버튼 누를시
			try {

				String isPass = staffview.getSelectionModel().getSelectedItem().getPassword();
				
				connection = handler.getConnnection();
				String sql = "delete from manage where password=?";
				pst = connection.prepareStatement(sql);
				pst.setString(1, isPass);//에 저장된 비밀번호
				
				pst.executeUpdate();
				
				StaffList(); //리스트 새로 보여주기
				
			}catch (Exception e4) {
				alert.setText("직원을 선택하세요");
		}
    }
	public void StaffList() { //직원리스트 새로 보여주기
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));

		pt.setOnFinished(e -> {
			try {

				deleteStaff.getScene().getWindow().hide();
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
	
	
	
	
	/*근태 테이블뷰*/
	public void showworker() {
		connection = handler.getConnnection();
		workList.clear();
		try {
			String sql = "SELECT * FROM work order by num desc";
			pst = connection.prepareStatement(sql);
			
			if(key==2) { //검색어 있을 경우
				sql="select * from work where worker like ? order by num desc";
				pst = connection.prepareStatement(sql);
				pst.setString(1, "%"+keyword2.getText()+"%");
			}
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				workList.add(new work(rs.getString("startDay"),rs.getString("startTime"),rs.getString("endDay"),rs.getString("endTime"),rs.getString("worker")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//콜론 추가
		startDay.setCellValueFactory(new PropertyValueFactory<>("startDay"));
		startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		endDay.setCellValueFactory(new PropertyValueFactory<>("endDay"));
		endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
		worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
		
		//테이블 보이게
		workview.setItems(workList);
		
		
		
	}

	
	
}
