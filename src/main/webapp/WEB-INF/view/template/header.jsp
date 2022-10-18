<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<div class="header">
	<div class="headerTitleBox">
		<div class="headerTitleText">
			DocVerify
		</div>
		<div class="headerSubTitleText">
			project
		</div>
	</div>
	<div class="headerMenuBox">
		<div class="headerMenuTop"></div>
		<div class="headerMenuBottom">
			<div class="headerMenuItem">
				<span class="headerMenuText">MENU1</span>
				<span class="headerMenuBorder">|</span>
				<span class="headerMenuText">MENU2</span>
				<span class="headerMenuBorder">|</span>
				<span class="headerMenuText">MENU3</span>
			</div>
		</div>
	</div>
	<div class="headerSettingBox">
		<div class="headerSettingTop">
			<span class="userText">사용자</span>
			<span class="logoutBtn">로그아웃</span>
		</div>
		<div class="headerSettingBottom">
		</div>
	</div>
</div>
<c:if test="${msg!=null}">
	<div id="msgBox">${msg}</div>
	<script>
		setTimeout(function() {
			$("#msgBox").fadeOut();
		}, 500);
	</script>
</c:if>