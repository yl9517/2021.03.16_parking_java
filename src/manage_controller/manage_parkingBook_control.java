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
	
    private ObservableList<parkBook> parkBookList = FXCollections.observableArrayList(); //������� ����Ʈ (�ΰ� ����)
    private ObservableList<parkBook> daychartList = FXCollections.observableArrayList(); //������Ʈ�� �������� ���� �Ϻ� ����Ʈ (�ΰ� ����)
    private ObservableList<parkBook> monthchartList = FXCollections.observableArrayList(); //������Ʈ�� �������� ���� ���� ����Ʈ (�ΰ� ����)
    
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
			if(!keyword.getText().equals(""))  //�˻�� ������
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
			
			if(key==1) { //�˻� Ȯ�ι�ư ������ ���
				sql = "select * from parkbook where carNum like ?";
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, "%"+keyword.getText()+"%");
				key =0;
			}
			if(key==2) { //Ķ���� ������ ���
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
		
		//�ش��ݷп� db�� ���̰� ����
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
	
	
	public void timeChartAction(ActionEvent e) { //�ð��� ��Ʈ ������
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
			stage.setTitle("�ð��뺰 �������� �� _ ������");
			stage.show();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void ChartParkbook() { //��Ʈ�� ���� ���� ������ ����
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
		Date time = new Date();
		//����
		String today = format.format(time);
		//����
		String month = today.substring(0,7);
		
		connection = handler.getConnnection();
		
		try {
			String sql = "SELECT * FROM parkbook WHERE date=? AND finish=1"; //�ΰ�����, ���ó�¥�ΰ͸� ���
			
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
			
			String sql = "SELECT * FROM parkbook WHERE date LIKE ? AND finish=1"; //�ΰ�����, ���ó�¥�ΰ͸� ���
		
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
	
//	public void SubmitAction() { //�˻��� �Է��� Ȯ�� ��������
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
