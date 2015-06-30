package com.aartek.repository;

import java.util.List;

import com.aartek.model.Interviewer;
import com.aartek.model.Schedule;

public interface SchedulerRepository {

	public List<Schedule> getScheduledCandidateDetails(
			Integer candidateId);

	public List<Interviewer> getInterviewerDetails(Integer skillId);

	public void saveScheduledCandidate(Schedule schedule);

}
