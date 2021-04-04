package constructor;

import javafx.beans.property.SimpleStringProperty;

public class parkBook {

	private int num;
	private String date;
	private String carNum;
	private String inTime;
	private String outTime;
	private int price;
	private String staff;
	
	//수입차트 날짜
	private String year;
	private String month;
	private String day;
	
	
	
	//주차장부 차트
	public parkBook(String year,String month,String day,int price) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.price = price;
	}
	
	public parkBook(int num, String date,String carNum,String inTime,String outTime,int price,String staff) {
		this.num = num;
		this.date = date;
		this.carNum = carNum;
		this.inTime = inTime;
		this.outTime = outTime;
		this.price = price;
		this.staff = staff;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	
	
}
