<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>SHY</title>
<link rel="stylesheet" href="/css/reset.css">
<link rel="stylesheet" href="/css/bootstrap.css">
<link rel="stylesheet" href="/css/bootstrap-grid.css">
<link rel="stylesheet" href="/css/bootstrap-reboot.css">
<link rel="stylesheet" href="/css/style.css">
<style>
    html.popup{
        overflow-y: hidden;
        overflow-x: hidden;
        padding:17px;
    }
    #content {
        position: absolute;
        width: 100%;
        height: 100%;
    }
    #excelUploadWrap {
        position: absolute;
        top:10%;
        left:10%;
        width: 80%;
        height: 80%;
        border: black 1px solid;
    }
    #dbSettingWrap {
        width: 70%;
        height: 120px;
        float: left;
    }
    #excelDownloadWrap {
        width: 30%;
        height: 120px;
        float: right;
    }
    #excelUploadBox{
        width: 100%;
        min-height: 630px;
        clear: both;
    }
    #excelUploadBox h2{
        font-size: 20px;
        font-weight: 700;
        margin-top : 200px;
        margin-left : 100px;
    }
    #excelUploadBox h3{
        margin-left : 100px;
        margin-bottom: 30px;
        color:#424242;
    }
    #dbSettingBtn {
        position: absolute;
        left:47%;
        top:40px;
    }
    .excelDownLoadWrapBtn {
        margin-left: 240px;
        margin-top: 40px;
    }
    #excelBoxList {
        margin-left : 100px;
    }
    #excelFIle{
        width:350px;
        height:350px;
        outline: none;
        border:1px solid #424242;
    }
    #excelUpload{
        width:344px;
        height:344px;
        border-radius: 10px;
        font-size: 100px;
        text-align: center;
        line-height: 344px;
        cursor: pointer;
        float:left;
    }
    .excelBox{
        width:350px;
        height:350px;
        border-radius: 10px;
        margin-right: 30px;
        float:left;
        cursor: pointer;
        box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2), 0 0 20px 0 rgba(0, 0, 0, 0.19);
    }
    .excelBox:hover{
        transform: scale(1.1);
    }
    .excelBox img{
        width:350px;
        height:350px;
    }
    #excelFileName{
        margin-top: 15px;
        font-size: 20px;
        font-weight: 700;
    }
    #btnWrap .register{
        position: absolute;
        left:46%;
        bottom:50px;
    }
    #btnWrap .cancel{
        position: absolute;
        left:50%;
        bottom:50px;
    }
    .dbSettingWrap{
        position: fixed;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,.75);
        display: none;
        left: 0;
        top:0;
        z-index: 1;
    }
    .dbSettingWrap.on{
        display: block;
    }
    .dbSettingWrap a{
        color: black;
        text-decoration: none;
        user-select: none;
    }
    .dbSettingBox{
        user-select: none;
        width: 400px;
        height: 500px;
        border: 1px solid #bebebe;
        border-radius: 10px;
        position: absolute;
        left: 50%;
        top:50%;
        margin: -281px 0 0 -201px;
        background-color: #fff;
    }
    .dbSettingHeader{
        font-size: 20px;
        font-weight: 700;
        margin-top: 25px;
        margin-left: 165px;
    }
    .dbSettingContent h2{
        font-size: 20px;
        font-weight: 700;
    }
    .inputWrap h2{
        margin-top: 30px;
    }
    .inputWrap input{
        margin-top: 10px;
    }
    .dbSettingFooter .register{
        position: absolute;
        left:35%;
        bottom:20px;
    }
    .dbSettingFooter .cancel{
        position: absolute;
        left:55%;
        bottom:20px;
    }

</style>
</head>
<body>
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
        <div class="dbSettingHeader">
            <h2>DB 설정</h2>
        </div>
        <div class="dbSettingContent">
            <form>
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
            </form>
        </div>
        <div class="dbSettingFooter">
            <button class="register btn btn-primary">등록</button>
            <button class="cancel btn btn-primary">취소</button>
        </div>
    </div>
</div>



<script src="/js/jquery-3.3.1.js"></script>
<script src="/js/bootstrap.bundle.js"></script>
<script src="/js/bootstrap.js"></script>
<script>
    const $dbSetting = $("#dbSettingBtn");
    const $excelUploadCancel = $("#btnWrap .cancel");
    const $dbSettingCancel = $(".dbSettingFooter .cancel");


    //엑셀 파일 업로드
    $('#excelFIle').on("change",function() {

        const file = this.files[0];

        if (/^application\/.test(file.type)&&/.sheet$/.test(file.type)) {

            //multipart/form-data에 필요함
            const formData = new FormData();

            formData.append("uploadImg", file, file.name);
            formData.append("type", "E");//E는 excel의 줄임말

            //여기서 ajax로 파일 업로드 수행
            $.ajax({
                url:"/data/uploadImage.json",
                processData : false,//multipart/form-data
                contentType : false,//multipart/form-data
                data : formData,//multipart/form-data
                type : 'POST',//multipart/form-data
                dataType : "json",
                error : function(xhr, error, code) {
                    alert("에러:" + code);
                },
                success:function(json) {
                    $("<li class='excelBox'><img src='"+json.url+"' /><label id='excelFileName'>"+file.name+"</label></li>").insertBefore("#excelBoxList li:last");
                    //json.url;

                    if($(".excelBox").length==6) {
                        $(".registerBtn").css("display","none");
                    }//if end

                    $(".excelBox img").on("click",function (){
                        $(this).parent().remove();

                        if($(".excelBox").length<6) {
                            $(".registerBtn").css("display","block");
                        }//if end
                    })//.imgBox click() end
                }
            });//$.ajax end

        } else {
            alert("엑셀 선택해주세요!");
        }
    });//#imgFile change() end

    $excelUploadCancel.click(function (e){
        $(".excelBox img").parent().remove();
        $(".registerBtn").css("display","block");
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