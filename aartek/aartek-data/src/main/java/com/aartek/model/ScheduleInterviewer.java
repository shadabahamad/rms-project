package com.aartek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;



@Entity
@Table(name = "schedule_interviewer")
public class ScheduleInterviewer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="S_NO")
	private int serialNO;
	
	@ManyToOne
	@JoinColumn(name = "SCHEDULE_ID")
	private Schedule schedule;
	
	@ManyToOne
	@JoinColumn(name = "INTERVIWER_ID")
	private Interviewer interviewer;

	public int getSerialNO() {
		return serialNO;
	}

	public void setSerialNO(int serialNO) {
		this.serialNO = serialNO;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Interviewer getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(Interviewer interviewer) {
		this.interviewer = interviewer;
	}

}
