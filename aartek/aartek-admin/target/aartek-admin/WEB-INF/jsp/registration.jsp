<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<script type="text/javascript" src="js//registration.js"></script>
<title>Register Candidate</title>
</head>
<body>
	<sec:authorize access="hasRole('HR_Executive')">
		<div>
			<form:form action="saveCandidate.do" method="post"
				modelAttribute="Registration" enctype="multipart/form-data">
				<h4>Register new Candidate</h4>
				<table>
					<tr>
						<td><label>First Name:</label></td>
						<td><form:input path="firstName" id="firstName"
								maxlength="30" /></td>
						<td><form:errors path="firstName" cssClass="error"></form:errors></td>
						<form:hidden path="candidateId" />
						<form:hidden path="createdDate"/>
						
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><form:input path="lastName" id="lastName" maxlength="30" /></td>
						<td><form:errors path="lastName" cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td><label>Email Id:</label></td>
						<td><form:input path="emailId" id="emailId" maxlength="30"/></td>
						<td><form:errors path="emailId" cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td><label>Mobile No.:</label></td>
						<td><form:input path="mobileNo" id="primaryMobileNo"
								maxlength="10" /></td>
						<td><form:errors path="mobileNo" cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td><label>Open Position:</label></td>
						<td><form:select path="skills.skillsId">
								<form:option value="0" label="Select" />
								<c:forEach items="${skillList}" var="skillList">
									<form:option value="${skillList.skillsId}"
										label="${skillList.skills}" />
								</c:forEach>

							</form:select></td>
						<td><form:errors path="skills.skillsId" cssClass="error"></form:errors></td>
					</tr>
				
					<tr>
						<td>Select Resume : <input type="file" path="fileName" name="fileUpload" id="fileUpload" />
						</td>
						<%-- <td><form:errors path="resumeName" cssClass="error"></form:errors></td> --%>
					</tr>
					<tr>
						<td><input type="Submit" value="Register" size="200" ></td>
					</tr>
				</table>
			</form:form>
		</div>
	</sec:authorize>
</body>
</html>