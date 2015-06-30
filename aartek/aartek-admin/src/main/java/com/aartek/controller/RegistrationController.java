package com.aartek.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aartek.aartek.util.IConstant;
import com.aartek.model.Registration;
import com.aartek.model.Skills;
import com.aartek.service.RegistrationService;
import com.aartek.validation.RegistrationValidator;

@Controller
public class RegistrationController {
	private static final Logger logger = Logger.getLogger(RegistrationController.class);
	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private RegistrationValidator registrationValidator;

	/**
	 * Open Candidate Registration Page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/getRegistrationPage" })
	public String getRegistrationPage(Map<String, Object> map, ModelMap model) {
		map.put("Registration", new Registration());
		logger.info("This is Info Message!");
		List<Skills> skillList = registrationService.getSkillList();
		model.addAttribute("skillList", skillList);
		return "registration";
	}

	/**
	 * Save New Candidate
	 * 
	 * @param registration
	 * @param result
	 * @param model
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/{saveCandidate}", method = RequestMethod.POST)
	public String saveCandidate(@RequestParam("fileUpload") MultipartFile file,
					@ModelAttribute("Registration") Registration registration,
					@RequestParam CommonsMultipartFile[] fileUpload, String fileName, BindingResult result,
					ModelMap model) throws Exception {
		MultipartFile f = file;
		String resumeName=f.getOriginalFilename();
		registrationValidator.validate(registration, result);
		if (result.hasErrors()) {
			List<Skills> skillList = registrationService.getSkillList();
			model.addAttribute("skillList", skillList);
			return "registration";
		}
		final MultipartFile filePart = file;
		file.getOriginalFilename();
		registrationService.saveCandidate(registration,resumeName);
		registrationService.uploadResume(registration, filePart, fileUpload, fileName);
		return "redirect:/welcome.do";
	}

	/**
	 * View Candidates
	 * 
	 * @param map
	 * @param model
	 * @param registration
	 * @param registrationId
	 * @return
	 */
	@RequestMapping(value = { "/viewCandidates" })
	public String viewCandidates(Map<String, Object> map, ModelMap model, @ModelAttribute Registration registration,
					@RequestParam(required = false) Integer candidateId) {
		map.put("Registration", new Registration());
		List<Registration> candidateDetails = registrationService.getCandidateDetails();
		model.addAttribute("candidateDetails", candidateDetails);
		return "viewCandidates";
	}

	/**
	 * Download File
	 * 
	 * @param registration
	 * @param request
	 * @param response
	 * @param fileName
	 * @param registrationid
	 * @return
	 */
	@RequestMapping(value = "/downLoadFile", method = RequestMethod.GET)
	public String downLoadFile(@ModelAttribute("Registration") Registration registration, HttpServletRequest request,
					HttpServletResponse response, @RequestParam("fileName") String fileName,
					@RequestParam("candidateId") String candidateId) {
		try {
			File file = new File(IConstant.FILE_PATH + "//" + candidateId + "//" + fileName);
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			PrintWriter out = response.getWriter();
			response.setContentType("application/xlsx");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
			int readData;
			while ((readData = inputStream.read()) != -1) {
				out.write(readData);
			}
			inputStream.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewCandidates";
	}

	/**
	 * Delete Candidates
	 * 
	 * @param registrationId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/deleteCandidate" }, method = { RequestMethod.GET })
	public String deleteCandidate(@RequestParam(required = false) Integer candidateId, ModelMap model) {
		registrationService.deleteCandidateById(candidateId);
		model.addAttribute("message", IConstant.CANDIDATE_DELETE_MESSAGE);
		return "redirect:/viewCandidates";
	}

	/**
	 * Edit Candidates
	 * 
	 * @param model
	 * @param registrationId
	 * @param registration
	 * @param map
	 * @return
	 */
	@RequestMapping(value = { "/editCandidate" }, method = {RequestMethod.GET,RequestMethod.POST,})
	public String editCandidate(ModelMap model, @RequestParam("candidateId") Integer candidateId,
					@ModelAttribute Registration registration, Map<String, Object> map) {
		registration = registrationService.editSudentDetails(candidateId);
		model.addAttribute("Registration", registration);
		List<Skills> skillList = registrationService.getSkillList();
		model.addAttribute("skillList", skillList);
		return "registration";
	}
}