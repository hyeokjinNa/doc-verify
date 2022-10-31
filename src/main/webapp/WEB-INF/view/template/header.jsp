<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<div class="header" id="header">
	<div class="headerTitleBox">
		<div class="headerTitleText" id="headerTitleText">
			DocVerify
		</div>
		<div class="headerSubTitleText" id="headerSubTitleText">
			project
		</div>
	</div>
	<div class="headerMenuBox">
		
	</div>
	<div class="headerSettingBox">
		<div class="headerSettingLift">
			<img class="userImg" src="/img/user.jpg" alt="사용자 이미지">
		</div>
		<div class="headerSettingRight">
			<div class="headerSettingUserInfo">
				<c:if test="${loginMember == null}">
					<div class="userText">사용자</div>
					<div class="loginBtn">로그인</div>
				</c:if>
				<c:if test="${loginMember != null}">
					<div class="userText">${loginMember.userName}</div>
					<div class="logoutBtn">로그아웃</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<c:if test="${msg!=null}">
	<div id="msgBox">${msg}</div>
	<script>
		setTimeout(function() {
			$("#msgBox").fadeOut();
		}, 1000);
	</script>
</c:if>
<script type="text/javascript">
	$('#headerTitleText').on("click",function(){
		location.href = 'http://localhost:8080/';
	});
	$('#headerSubTitleText').on("click",function(){
		location.href = 'http://localhost:8080/';
	});
	$('.loginBtn').on("click",function() {
		$(".dbSettingWrap").addClass("on");
        $("html").addClass("popup");
	});
	$('.logoutBtn').on("click",function() {
		location.href = 'http://localhost:8080/dbLogout'
	});
</script>