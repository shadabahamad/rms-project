package com.aartek.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.aartek.model.Registration;

@Component
public class RegistrationValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return Registration.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Registration registration = (Registration) target;

		ValidationUtils.rejectIfEmpty(errors, "firstName", "error.firstName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.lastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "emailId", "error.emailId.empty");
		ValidationUtils.rejectIfEmpty(errors, "mobileNo", "error.mobileNo.empty");
		// ValidationUtils.rejectIfEmpty(errors, "resumeName",
		// "error.resumeName.empty");
		if (registration.getEmailId() != null && registration.getEmailId().trim().length() > 0) {
			boolean b = ValidationUtil.validateEmail(registration.getEmailId());
			if (registration.getEmailId().contains("@") != true && !b) {
				errors.rejectValue("emailId", "error.emailId.first.rule");
			} else if (registration.getEmailId().contains(".com") != true
							&& registration.getEmailId().contains(".net") != true
							&& registration.getEmailId().contains(".co.in") != true && !b) {
				errors.rejectValue("emailId", "error.emailId.second.rule");
			} else if (!b) {
				errors.rejectValue("emailId", "error.emailId.required");
			}
		}

		if (registration.getSkills().getSkillsId() == 0) {
			errors.rejectValue("skills.skillsId", "error.skills.skillsId.empty");
		}

		if (registration.getMobileNo() != null && registration.getMobileNo().trim().length() > 0) {
			boolean b = ValidationUtil.validatingNumberFiled(registration.getMobileNo());
			if (registration.getMobileNo().contains("$") != true && !b) {
				errors.rejectValue("mobileNo", "error.mobileNo.rul");
			} else if (registration.getMobileNo().length() < 10 && registration.getMobileNo().length() > 0) {
				errors.rejectValue("mobileNo", "error.mobileNo.length");
			}
		}

		if (registration.getFirstName() != null && registration.getFirstName().trim().length() > 0) {
			boolean b = ValidationUtil.validateingAlphabetsFiled(registration.getFirstName());
			if (registration.getFirstName().contains("$") != true && !b) {
				errors.rejectValue("firstName", "error.firstName.length");

			}

		}
		if (registration.getLastName() != null && registration.getLastName().trim().length() > 0) {
			boolean b = ValidationUtil.validateingAlphabetsFiled(registration.getLastName());
			if (registration.getLastName().contains("$") != true && !b) {
				errors.rejectValue("lastName", "error.lastName.length");

			}

		}

	}

}
