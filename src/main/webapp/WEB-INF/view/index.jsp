<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>DocVerify</title>
<c:import url="/WEB-INF/view/template/link.jsp"/>
<link rel="stylesheet" href="/css/index.css">

</head>
<body>

<c:import url="/WEB-INF/view/template/header.jsp"/>
    <div id="content">
        <div id="excelUploadWrap">
            <div id="dbSettingWrap">
                <button id="dbSettingBtn" class="btn btn-primary">DB 설정</button>
            </div>
            <div id="excelDownloadWrap">
            	<form action="/fileDownload" method="get">
            		<input type="hidden" name="fileName" value="테이블컬럼매핑정의서 양식.xlsx">
                	<button id="excelDownBtn" class="btn btn-primary excelDownLoadWrapBtn">정의서 양식 다운</button>
            	</form>
            </div>
            <form action="/excelRegister" method="post" enctype="multipart/form-data">
            	<input id="loginMember" type="hidden" name="user" value="${loginMember}">
	            <div id="excelUploadBox">
	                <h2>정의서 등록</h2><br/>
	                <h3>최소 1개에서 최대 5개의 정의서를 등록해주세요</h3>
	                <ul id="excelBoxList">
	                    <li class="excelBox registerBtn">
	                        <input multiple type="file" id="excelFile" style="display:none;">
	                        <label for="excelFile" id="excelUpload">+</label>
	                    </li>
	                </ul>
	            </div>
	            <div id="btnWrap">
	                <button class="register btn btn-primary">등록</button>
	                <button type="button" class="cancel btn btn-primary">취소</button>
	            </div>
            </form>
        </div>
    </div>

<div class="dbSettingWrap">
    <div class="dbSettingBox">
    	<form action="/dbLogin" method="post">
	        <div class="dbSettingHeader">
	            <h2>DB 설정</h2>
	        </div>
	        <div class="dbSettingContent">
	                <div class="inputWrap">
	                    <h2>url</h2>
	                    <input name="url" class="input-group"/>
	                </div>
	                <div class="inputWrap">
	                    <h2>driver</h2>
	                    <input name="driver" class="input-group" value="com.tmax.tibero.jdbc.TbDriver"/>
	                </div>
	                <div class="inputWrap">
	                    <h2>userName</h2>
	                    <input name="userName" class="input-group"/>
	                </div>
	                <div class="inputWrap">
	                    <h2>password</h2>
	                    <input name="password" class="input-group"/>
	                </div>
	        </div>
	        <div class="dbSettingFooter">
	            <button class="register btn btn-primary">등록</button>
	            <button type="button" class="cancel btn btn-primary">취소</button>
	        </div>
        </form>
    </div>
</div>
<!-- 로딩이미지 -->
<div id="loader"></div>
<c:import url="/WEB-INF/view/template/footer.jsp"/> 



<script type="text/javascript">
    const $dbSetting = $("#dbSettingBtn");
    const $dbLogin = $(".dbSettingFooter .register");
    const $excelUploadCancel = $("#btnWrap .cancel");
    const $dbSettingCancel = $(".dbSettingFooter .cancel");
    const $excelUpload = $("#btnWrap .register");
    const $loader = $("#loader");
    
    let files = [];
    let fileCnt = 0;


    //엑셀 파일 업로드
    $('#excelFile').on("change",function() {
		fileCnt += this.files.length;
		if(fileCnt>5){
			alert("엑셀 파일은 5개까지 등록 가능합니다");
			fileCnt -= this.files.length;
			return false;
		}
        for(let i = 0; i<this.files.length; i++){
        
        const file = this.files[i];
        files.push(file);
      //multipart/form-data에 필요함
        
        $(".registerBtn label").attr('id','excelFileName').text(file.name);
        $(".registerBtn input").attr('name','mfiles');
        $(".registerBtn label").parent().removeClass("registerBtn");
	       $("<img src='/img/excel.png' />").insertAfter("#excelBoxList input:last");
        $("<li class='excelBox registerBtn'><input mutiple type='file' id='excelFile' style='display:none;'><label for='excelFile' id='excelUpload'>+</label></li>").insertAfter("#excelBoxList li:last");

        if($(".excelBox").length==6) {
            $(".registerBtn").css("display","none");
        }//if end

        $(".excelBox img").on("click",function (){
	           	const fileName = $(this).parent().children('label').text();
	       		for(let i = 0; i < files.length; i++){
	       			if(files[i].name == fileName){
	   	    			files.splice(i,1);
	       		}
	       	}
            
	           $(this).parent().remove();
	
	           if($(".excelBox").length<6) {
	               $(".registerBtn").css("display","block");
	           }//if end
        
       })//.imgBox click() end
        
       }//for end
    });//#imgFile change() end
    

    $excelUploadCancel.click(function (e){
        $(".excelBox img").parent().remove();
        $(".registerBtn").css("display","block");
        files = [];
        fileCnt = 0;
    });

    $dbSetting.click(function (e){
        $(".dbSettingWrap").addClass("on");
        e.preventDefault();
        $("html").addClass("popup");
    });

    $dbSettingCancel.click(function (e){
        e.stopPropagation();
        $(".dbSettingWrap").removeClass("on");
        $("html").removeClass("popup");
    });

    $excelUpload.on("click",function(){
        if($("#loginMember").val()==""){
			alert("DB 로그인을 해주세요");
			return false;
        }else{
        	$loader.fadeIn();
        }

    });

   

</script>
</body>
</html>