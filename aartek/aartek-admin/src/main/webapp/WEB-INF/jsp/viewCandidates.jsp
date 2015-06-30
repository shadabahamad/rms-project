<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- <%@ taglib uri="http://displaytag.sf.net" prefix="display"%> --%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <spring:eval expression="@propertyConfigurer.getProperty('pp.jspImagePath')" var="imgPath" /> --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<div align="center">
		<h1>Candidate List</h1>
		<table border="1">
			<th>No</th>
			<th>Name</th>
			<th>Email Id</th>
			<th>MobileNo</th>
			<th>Resume</th>
			<th>Schedule</th>
			<th>Edit</th>
			<th>Delete</th>
			<c:forEach var="user" items="${candidateDetails}" varStatus="status">
				<tr>
					<td>${status.index+ 1 }</td>
					<td>${user.firstName}  ${user.lastName}</td>
					<td>${user.emailId}</td>
					<td>${user.mobileNo}</td>
					<td><a href="downLoadFile.do?fileName=${user.resumeName}&candidateId=${user.candidateId}">${user.resumeName}</a></td>

					<td><a href="viewScheduledCandidate.do?candidateId=${user.candidateId}&skillId=${user.skills.skillsId}">Schedule</a></td>
					
					<td><c:if test="${sessionScope.login.adminType!=4}">
							<display:column title="Edit">
								<a href="editCandidate.do?candidateId=${user.candidateId}">Edit</a>
							</display:column></td>

					<td><display:column title="Delete">
							<a
								href="deleteCandidate.do?candidateId=${user.candidateId}"
								onclick="return confirm('Please confirm if you want to delete this Details!');">Delete</a>
						</display:column></td>
					</c:if>
				</tr>
			</c:forEach>

		</table>
	</div>
</body>
</html>