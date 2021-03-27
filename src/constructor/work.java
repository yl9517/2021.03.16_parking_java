package constructor;

public class work {
	
	private String startDay;
	private String startTime;
	private String endDay;
	private String endTime;
	private String worker;
	
	public work(String startDay,String startTime, String endDay, String endTime, String worker) {
		this.startDay = startDay;
		this.startTime=startTime;
		this.endDay = endDay;
		this.endTime = endTime;
		this.worker = worker;
	}
	
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	
	

}
