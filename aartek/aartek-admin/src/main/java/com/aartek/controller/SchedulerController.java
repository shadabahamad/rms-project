package com.aartek.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aartek.model.Interviewer;
import com.aartek.model.Schedule;
import com.aartek.service.SchedulerService;

@Controller
public class SchedulerController {

	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping(value = { "/viewScheduledCandidate" })
	public String viewScheduledCandidate(Map<String, Object> map, ModelMap model, @ModelAttribute Schedule schedule,
					@RequestParam(required = false) Integer candidateId,
					@RequestParam(value = "skillId", required = false) Integer skillId) {
		map.put("Schedule", new Schedule());
		List<Interviewer> interviewerList = schedulerService.getInterviewerDetails(skillId);
		model.addAttribute("interviewerList", interviewerList);
		List<Schedule> scheduledCandidateDetails = schedulerService.getScheduledCandidateDetails(candidateId);
		model.addAttribute("scheduledCandidateDetails", scheduledCandidateDetails);
		return "schedule";
	}

	@RequestMapping(value = "/saveScheduledCandidate", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String saveScheduledCandidate(@ModelAttribute("Schedule") Schedule schedule) {
		schedulerService.saveScheduledCandidate(schedule);
		return "redirect:/welcome.do";
	}                                
}
