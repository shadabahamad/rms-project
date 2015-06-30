package com.aartek.repositoryImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.aartek.aartek.util.IConstant;
import com.aartek.model.Registration;
import com.aartek.model.Skills;
import com.aartek.repository.RegistrationRepository;

/**
 * 
 * @author Vivek,22/04/2015
 *
 */
@SuppressWarnings("unchecked")
@Repository("registrationRepository")
public class RegistrationRepositoryImpl implements RegistrationRepository {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Save Method
	 */
	@Override
	public void saveCandidate(Registration registration) {
		hibernateTemplate.save(registration);
	}

	/**
	 * get Skill list
	 */
	@Override
	public List<Skills> getSkillList() {
		List<Skills> skillList = (List<Skills>) hibernateTemplate.find("from Skills");
		return skillList;
	}

	/**
	 * Update Candidate
	 */
	@Override
	public void updateCandidate(Registration registration) {
		hibernateTemplate.saveOrUpdate(registration);
	}

	/**
	 * View Candidate
	 */
	@Override
	public List<Registration> getCandidateDetails() {
		List<Registration> candidateList = (List<Registration>) hibernateTemplate
						.find("from Registration  where IS_DELETED=" + IConstant.IS_DELETED
										+ "order by candidateId desc");
		return candidateList;

	}

	/**
	 * Delete Candidate
	 */
	@Override
	public void deleteCandidateById(Integer candidateId) {

		Registration registration = (Registration) hibernateTemplate.get(Registration.class, candidateId);
		registration.setIsDeleted(IConstant.IS_DELETED_DEACTIVE);
		if (null != registration) {
			hibernateTemplate.update(registration);
		}
	}

	/**
	 * Edit Candidates
	 */
	@Override
	public Registration editStudentDetails(Integer candidateId) {
		List<Registration> registration = (List<Registration>) hibernateTemplate
						.find(" from Registration where candidateId=" + candidateId);
		return registration.get(0);

	}

}
