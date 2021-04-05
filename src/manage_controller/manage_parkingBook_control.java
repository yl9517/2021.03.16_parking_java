package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import DBconnect.DBhandle;
import constructor.parkBook;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class manage_parkingBook_control implements Initializable{
    @FXML
    private DatePicker calendar;

    @FXML
    private TextField keyword;

    @FXML
    private Button submit;

    @FXML
    private TableView<parkBook> parkBookView;

    @FXML
    private TableColumn<parkBook, String> col_num, col_date, col_carNum, col_inTime, col_outTime, col_price, col_staff;

    @FXML
    private Button timeChart;
	
    private ObservableList<parkBook> parkBookList = FXCollections.observableArrayList(); //주차장부 리스트 (널값 포함)
    private ObservableList<parkBook> daychartList = FXCollections.observableArrayList(); //막대차트에 보여지기 위한 일별 리스트 (널값 제외)
    private ObservableList<parkBook> monthchartList = FXCollections.observableArrayList(); //막대차트에 보여지기 위한 월별 리스트 (널값 제외)
    
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
    private int key =0;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		calendar.setOnAction(e -> {
			if(calendar.getValue()!=null)
				key=2;
			ShowTable();
			
		});
				
		ShowTable();
		 
		submit.setOnAction(e-> {
			if(!keyword.getText().equals(""))  //검색어가 있을때
				key = 1;
			ShowTable();
			
		});
	}
	public void ShowTable() {
		
		parkBookList.clear();
		connection = handler.getConnnection();
		try {
			String sql = "SELECT * FROM parkbook";
			pst = connection.prepareStatement(sql);
			
			if(key==1) { //검색 확인버튼 눌렀을 경우
				sql = "select * from parkbook where carNum like ?";
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, "%"+keyword.getText()+"%");
				key =0;
			}
			if(key==2) { //캘린더 눌렀을 경우
				sql = "select * from parkbook where date=?";
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, String.valueOf(calendar.getValue()));
				key =0;
			}
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				parkBookList.add(new parkBook(rs.getInt("num"), rs.getString("date"),rs.getString("carNum")
						,rs.getString("inTime"), rs.getString("outTime"), rs.getInt("price"), rs.getString("staff")));
			}
			
		}catch (Exception e) {
		}
		
		//해당콜론에 db값 보이게 지정
		col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
		col_num.setStyle(" -fx-alignment : center; ");
		col_carNum.setCellValueFactory(new PropertyValueFactory<>("carNum"));
		col_carNum.setStyle(" -fx-alignment : center; ");
		col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		col_date.setStyle(" -fx-alignment : center; ");
		col_inTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
		col_inTime.setStyle(" -fx-alignment : center; ");
		col_outTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
		col_outTime.setStyle(" -fx-alignment : center; ");
		col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_price.setStyle(" -fx-alignment : center-right; ");
		col_staff.setCellValueFactory(new PropertyValueFactory<>("staff"));
		col_staff.setStyle(" -fx-alignment : center; ");
		
		
		parkBookView.setItems(parkBookList);

		
	}
	
	
	public void timeChartAction(ActionEvent e) { //시간별 차트 누르면
		ChartParkbook();
		
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/FXML/manage_parkingBook_timeChart.fxml"));

			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			manage_parkingBookChart_control connect = loader.getController();
			connect.SetCountCar(daychartList,monthchartList);
			
			
			stage.setScene(scene);
			stage.setTitle("시간대별 입출차량 수 _ 관리자");
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void ChartParkbook() { //차트에 띄우기 위한 쿼리값 빼기
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
		Date time = new Date();
		//당일
		String today = format.format(time);
		//월별
		String month = today.substring(0,7);
		
		connection = handler.getConnnection();
		
		try {
			String sql = "SELECT * FROM parkbook WHERE date=? AND finish=1"; //널값제외, 오늘날짜인것만 출력
			
			pst = connection.prepareStatement(sql);
			pst.setString(1, today);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				daychartList.add(new parkBook(rs.getInt("num"), rs.getString("date"),rs.getString("carNum")
						,rs.getString("inTime"), rs.getString("outTime"), rs.getInt("price"), rs.getString("staff")));
			}
			
		}catch (Exception e) {
		}
		try {
			
			String sql = "SELECT * FROM parkbook WHERE date LIKE ? AND finish=1"; //널값제외, 오늘날짜인것만 출력
		
			pst = connection.prepareStatement(sql);
			pst.setString(1, "%"+month+"%");
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				monthchartList.add(new parkBook(rs.getInt("num"), rs.getString("date"),rs.getString("carNum")
					,rs.getString("inTime"), rs.getString("outTime"), rs.getInt("price"), rs.getString("staff")));
		}
		
		}catch (Exception e) {
		}
	}
	
//	public void SubmitAction() { //검색어 입력후 확인 눌렀을때
//		parkBookList.clear();
//
//		connection = handler.getConnnection();
//		try {
//			String sql = "select * from parkbook where (date || carNum || price || staff) like ?";
//			pst = connection.prepareStatement(sql);
//			pst.setString(1, "%"+keyword.getText()+"%");
//			
//			ResultSet rs = pst.executeQuery();
//			while(rs.next()) {
//				parkBookList.add(new parkBook(rs.getInt("num"), rs.getString("date"),rs.getString("carNum")
//						,rs.getString("inTime"), rs.getString("outTime"), rs.getInt("price"), rs.getString("staff")));
//			}
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
				
}
