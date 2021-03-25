package constructor;

import javafx.beans.property.SimpleStringProperty;

public class staff {

	private String name;
	private String password;
	private String gender;
	private String birth;
	private String phone;
	private String email;
	private String power;
	
	
	public staff(String name, String password, String gender, String birth, String phone, String email, String power) {
		this.name = name;
		this.password = password;
		this.gender= gender;
		this.birth=birth;
		this.phone= phone;
		this.email= email;
		this.power= power;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPower() {
		return power;
	}


	public void setPower(String power) {
		this.power = power;
	}

	
	
}
