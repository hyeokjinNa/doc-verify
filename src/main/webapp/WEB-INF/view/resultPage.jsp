<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>정의서, DB 비교 결과 페이지</title>
	<link rel='stylesheet' type='text/css' media='screen' href='resources/css/style.css'>
	<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.12.1/pagination/simple_numbers_no_ellipses.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.semanticui.min.css">
	<link rel="stylesheet" href="resources/css/bootstrap.css">
	<script type="text/javascript" src="resources/js/bootstrap.js"></script>
	<script>
        $.fn.DataTable.ext.pager.numbers_length = 5;

        $(function(){

            var checked;

            var table = $("#resultTable").DataTable({

                ajax : "./data.json",
                columns : [
                    {data : "tableName"},
                    {data : "entityName"},
                    {data : "physicalName"},
                    {data : "logicalName"},
                    {data : "dataType"},
                    {data : "length"},
                    {data : "preScale"},
                    {data : "pk"},
                    {data : "notNull"},
                    {data : "match"}
                ],
                lengthChange : true, // 표시 건수 기능 - 몇 건씩 보여줄지
                searching : true, // 검색 기능
                ordering : true, // 정렬 기능
                info : true, // 정보 표시 - 몇 건 있는지
                paging : true,
                pagingType : "simple_numbers_no_ellipses",
                pageLength : 20, // displayLength
                lengthMenu: [[10, 20, 30, -1], [10, 20, 30, "ALL"]],
                //order : [[0, "asc"], [7, "desc"], [2, "asc"]], // 초기 정렬 [[열 번호, 정렬 순서]]
                // serverSide : true,
                processing : true,
                // responsive : true,
                columnDefs : [ // 각 컬럼들에 대한 커스터마이징
					{
			            defaultContent : "", // null값이면 undefined -> 빈 값으로 대체
			            targets : "_all"
			      	},
			      	{orderable: true, targets :[0,2]},
                    {orderable: false, targets :[1,3,4,5,6,7,8,9]},
                    {
                        targets : 9,
                        createdCell : function(td, cellData, rowData, row, col) {
                            if(cellData == 'X') {
                                $(td).css('color', 'red');
                            }
                        }
                        // 불일치한 컬럼도 빨간색
                    },
                    {targets : 10, visible : false} // 불일치한 msg 안 보이게
                    // {responsivePriority : -3 , targets: 0}, // responsivePriority 숫자가 클수록 먼저 사라짐. 음수는 사라지지 않게
                    // {responsivePriority : -2 , targets: 1}
				],
                language:
			    {
			        paginate:
			        {
			            previous: "<",
			            next: ">"
			        },
                    lengthMenu : "_MENU_ 개씩 보기",
                    info : "_START_ - _END_ \/ _TOTAL_ ",
                    infoEmpty : "0 건",
                    search : "검색 : ",
                    zeroRecords : "검색 결과가 없습니다",
                    loadingRecords : "로딩중...",
                    processing : "잠시만 기다려 주세요",
                    emptyTable : "데이터가 없습니다",
                    infoFiltered : "(총 _MAX_ 개)",
                    infoThousands : ","
			    },
                stateSave : true, // 현재 상태를 보존
                initComplete : function(settings, json){
                    json.data.forEach(element => {
                        console.log(element)
                    });
                    // 맨 처음에 몇개 불일치인지, 전체에서 몇개인지. 퍼센트로도?
                }
                // drawCallBack : function(settings){
                //     var api = this.api();
                //     console.log(api.rows({page:'current'}).data());
                // }
            });

            $("input[name=matchRadio]").change(function(){
                //checked = $("input[name=matchRadio]:checked").val();
                checked = $(this).val();

                if(checked == 'match') {
                    table.columns(9).search('O').draw();
                } else if(checked == 'notMatch') {
                    table.columns(9).search('X').draw();
                } else {
                    table.columns(9).search('').draw();
                }
            });

            table.on('click', 'tr', function(){
                var data = table.row(this).data(); // table.rows({selected:true}).data()

                if(data.match == 'X') {
                    alert(data.tableName + ' ' + data.entityName);
                }
                // 정의서 값, 디비 값 보여주기 (불일치일때만?)
            });
            
            // 엑셀 다운은 검색 조건 하고 테이블 기준으로, 필터 걸어서 다운받을 수 있게끔 (못하면 바이)
            // 반응형, dom 하기
            // 기준은 정의서 기준. 없으면 "data":null
        })
    </script>
</head>
<body>
	<div>
        <h2 class="center">정의서 일치 확인</h2>
    </div>
    <div class="center">
        <input type="radio" id="all" name="matchRadio" value="all" checked>
        <label for="all">전체</label>
        <input type="radio" id="match" name="matchRadio" value="match">
        <label for="match">일치</label>
        <input type="radio" id="notMatch" name="matchRadio" value="notMatch">
        <label for="notMatch">불일치</label>
    </div>
    <div class="center">
        <select name="tableSelect" id="tableSelect">
            <option value="all">전체선택</option>
            <option value="TB_BL01I_001">TB_BL01I_001</option>
        </select>
    </div>
    <div id="resultBox">
        <table id="resultTable" class="table ui celled">
            <thead>
                <tr>
                    <th>테이블명</th>
                    <th>엔티티명</th>
                    <th>물리명</th>
                    <th>논리명</th>
                    <th>데이터타입</th>
                    <th>길이</th>
                    <th>자리수</th>
                    <th>PK</th>
                    <th>Not Null</th>
                    <th>일치</th>
                </tr>
            </thead>
        </table>
    </div>
</body>
</html>