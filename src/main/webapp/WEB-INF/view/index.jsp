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
                <button id="excelDownBtn" class="btn btn-primary excelDownLoadWrapBtn">정의서 양식 다운</button>
            </div>
            <div id="excelUploadBox">
                <h2>정의서 등록</h2><br/>
                <h3>최소 1개에서 최대 5개의 정의서를 등록해주세요</h3>
                <ul id="excelBoxList">
                    <li class="excelBox registerBtn">
                        <input type="file" id="excelFIle" style="display:none;" >
                        <label for="excelFIle" id="excelUpload">+</label>
                    </li>
                </ul>
            </div>
            <div id="btnWrap">
                <button class="register btn btn-primary">등록</button>
                <button class="cancel btn btn-primary">취소</button>
            </div>
        </div>
    </div>

<div class="dbSettingWrap">
    <div class="dbSettingBox">
    	<form action="/register" method="post">
	        <div class="dbSettingHeader">
	            <h2>DB 설정</h2>
	        </div>
	        <div class="dbSettingContent">
	                <div class="inputWrap">
	                    <h2>드라이버</h2>
	                    <input class="input-group "/>
	                </div>
	                <div class="inputWrap">
	                    <h2>아이피</h2>
	                    <input class="input-group"/>
	                </div>
	                <div class="inputWrap">
	                    <h2>아이디</h2>
	                    <input class="input-group"/>
	                </div>
	                <div class="inputWrap">
	                    <h2>비밀번호</h2>
	                    <input class="input-group"/>
	                </div>
	        </div>
	        <div class="dbSettingFooter">
	            <button class="register btn btn-primary">등록</button>
	            <button class="cancel btn btn-primary">취소</button>
	        </div>
        </form>
    </div>
</div>
<c:import url="/WEB-INF/view/template/footer.jsp"/> 



<script type="text/javascript">
    const $dbSetting = $("#dbSettingBtn");
    const $excelUploadRegister = $("#btnWrap .register");
    const $excelUploadCancel = $("#btnWrap .cancel");
    const $dbSettingCancel = $(".dbSettingFooter .cancel");
    let files = [];
    

    //엑셀 파일 업로드
    $('#excelFIle').on("change",function() {

        const file = this.files[0];
        files.push(file);  // 여기서부터해야함

        if (/^application/.test(file.type)&&/.sheet$/.test(file.type)) {
            //multipart/form-data에 필요함

            $("<li class='excelBox'><img src='/img/excel.png' /><label id='excelFileName'>"+file.name+"</label></li>").insertBefore("#excelBoxList li:last");
            //json.url;

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

        } else {
            alert("엑셀 선택해주세요!");
        }
    });//#imgFile change() end

    $excelUploadRegister.click(function (e){
    	const formData = new FormData();
    	for(let i = 0; i < files.length; i++){
			formData.append('files',files[i],files[i].name);
        }
		console.log(formData);
        $.ajax({
        	 url:"/ajax/uploadExcel",
             processData : false,//multipart/form-data
             contentType : false,//multipart/form-data
             data : formData,//multipart/form-data
             type : 'POST',//multipart/form-data
             dataType : "json",
             error : function(xhr, error, code) {
                 alert("에러:" + code);
             },
             success:function(json) {
                 console.log(json)
             }
        });
        
    });

    $excelUploadCancel.click(function (e){
        $(".excelBox img").parent().remove();
        $(".registerBtn").css("display","block");
        files = [];
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

   

</script>
</body>
</html>