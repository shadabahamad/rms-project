package com.aartek.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aartek.aartek.util.DateFormat;
import com.aartek.aartek.util.IConstant;
import com.aartek.model.Registration;
import com.aartek.model.Skills;
import com.aartek.repository.RegistrationRepository;
import com.aartek.service.RegistrationService;

/**
 * 
 * @author Vivek,22/04/2015
 *
 */
@Service("registrationService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
	@Autowired
	private RegistrationRepository registrationRepository;

	/**
	 * Save method
	 */
	public void saveCandidate(Registration registration,String resumeName) {
		if (registration.getCandidateId() == null) {
			registration.setIsDeleted(IConstant.IS_DELETED);
			registration.setCreatedDate(DateFormat.getYYYYMMDDDate());
			registration.setResumeName(resumeName);
			registrationRepository.saveCandidate(registration);
		} else {
			registration.setUpdatedDate(DateFormat.getYYYYMMDDDate());
			registration.setIsDeleted(IConstant.IS_DELETED);
		}

	}

	public List<Skills> getSkillList() {
		return registrationRepository.getSkillList();
	}

	/**
	 * Upload Resume
	 */
	public void uploadResume(Registration registration, MultipartFile filePart, CommonsMultipartFile[] fileUpload,
					String fileName) {
		OutputStream outputStream = null;
		InputStream inputStream = null;

		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile multipartFile : fileUpload) {
				fileName = multipartFile.getOriginalFilename();
				try {
					inputStream = filePart.getInputStream();
					File newFile = new File(IConstant.FILE_PATH + registration.getCandidateId());
					File filePath = new File(newFile + File.separator + registration.getCandidateId() + "_"
									+ fileName);
					if (!newFile.exists()) {
						newFile.mkdir();
						filePath.createNewFile();
					}
					outputStream = new FileOutputStream(filePath);
					int read = 0;
					int length = (int) fileName.length();
					byte[] bytes = new byte[length];
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			registration.setResumeName(registration.getCandidateId() + "_" + fileName);
			registrationRepository.updateCandidate(registration);
		}
	}

	/**
	 * View Candidates
	 */
	public List<Registration> getCandidateDetails() {
		List<Registration> list = registrationRepository.getCandidateDetails();
		return list;
	}

	/**
	 * Delete Candidates
	 */
	public void deleteCandidateById(Integer candidateId) {
		registrationRepository.deleteCandidateById(candidateId);
	}

	/**
	 * Edit Candidates
	 */
	public Registration editSudentDetails(Integer candidateId) {
		Registration registration = (Registration) registrationRepository.editStudentDetails(candidateId);
		return registration;
	}

}
