<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=chrome'>
    <title>결과 페이지</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/style.css'>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.semanticui.min.css">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.9/css/responsive.bootstrap5.css"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.2.3/css/buttons.dataTables.min.css"/>
    
    <script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.12.1/pagination/simple_numbers_no_ellipses.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.js"></script>
  	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.9/js/responsive.bootstrap5.js"></script>
  	<script type="text/javascript" src="https://cdn.datatables.net/buttons/2.2.3/js/dataTables.buttons.min.js"></script>
  	<script type="text/javascript" src="https://cdn.datatables.net/buttons/2.2.3/js/buttons.html5.min.js"></script>
  	
    <script>
        $.fn.DataTable.ext.pager.numbers_length = 5;

        $(function(){
            let checked;
            let data = eval('${dataJson}');
            let table;
            
            $('#resultBox1').show(); // class=active
            
            $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
            	$.fn.dataTable.tables({ visible : true, api : true }).columns.adjust();
            });
            
            for(var i=0; i<data.length; i++) {
            
            	table = $("#resultTable"+i).DataTable({

	                data : data[i].doc, // doc 동적으로 바꿔보기
	                columns : [
	                    {data : "tableName"},
	                    {data : "entityName"},
	                    {data : "physicalName"},
	                    {data : "logicalName"},
	                    {data : "dataType"},
	                    {data : "length"},
	                    {data : "precision"},
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
				      	{orderable : true, targets :[0,2]},
	                    {orderable : false, targets :[1,3,4,5,6,7,8,9]},
	                    {
	                    	targets : 0,
	                    	render : function(data, type, row) {
	                    		return row.schema + "." + data;
	                    	}
	                    },
	                    {
	                    	targets : 6,
	                    	render : function(data, type, row) {
	                    		return row.scale == null ? data : data + "," + row.scale;
	                    	}
	                    },
	                    {
	                    	targets : 7,
	                    	render : function(data, type, row) {
	                    		return data == 'Y' ? data : '';
	                    	}
	                    },
	                    {
	                    	targets : 8,
	                    	render : function(data, type, row) {
	                    		return data == 'Y' ? data : '';
	                    	}
	                    },
	                    {
	                        targets : 9,
	                        createdCell : function(td, cellData, rowData, row, col) {
	                            $(td).html('O');
	                            
	                            if(!cellData) {
	                                $(td).css('color', '#db2828');
	                                $(td).html('X');
	                            }
	                        }
	                    }
	                    // {responsivePriority : -3 , targets: 0}, // responsivePriority 숫자가 클수록 먼저 사라짐. 음수는 사라지지 않게
	                    // {responsivePriority : -2 , targets: 1}
					],
	                createdRow : function(row, data, dataIndex, cells) {
	                
	                    if(!data.match) {
	                        $(row).addClass('pointer');
	
	                        if(!data.check) { // 아예 없는 경우
	                            $(row).addClass('red');
	
	                        } else { // 불일치한 컬럼 표
	                        	
	                            //const columns = table.settings().init().columns;
	                            
	                            /*for(let i=0; i< columns.length; i++) {
	                            	data.wrongColumns.forEach(wrong => {
	                                    if(columns[i].data == wrong) {
	                                        $('td', row).eq(i).css('color', '#db2828'); // 값 불일치 컬럼 빨간색
	                                    }
	                                })
	                            }*/
	                        }
	                    }
	                },
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
	                    emptyTable : "데이터가 없습니다",
	                    infoFiltered : "",
	                    infoThousands : ","
				    },
	                stateSave : true, // 현재 상태를 보존
	                initComplete : function(settings, json){
	                    // json.data.forEach(element => {
	                    //     if(element.match == 'X' && element.check == 'O') {
	                    //         //console.log(element.wrongColumns);
	                    //     }
	                    // });
	                    // 맨 처음에 몇개 불일치인지, 전체에서 몇개인지. 퍼센트로도?
	                },
	                dom : 'Bfrtip',
	                buttons : [
	                	{
	                		text : '다운로드',
	                		extend : 'csvHtml5',
	                		fieldSeparator : '\t',
	                		extension : '.csv'
	                	}
	                ]
	            });
            }

            $("input[name=matchRadio]").change(function(){
                checked = $(this).val();
                
                if(checked == 'match') {
                    table.columns(9).search(true).draw();
                } else if(checked == 'notMatch') {
                    table.columns(9).search(false).draw();
                } else {
                    table.columns(9).search('').draw();
                }
            });

            table.on('click', 'tr', function(){
                var data = table.row(this).data();

                if(!data.match) {
                    alert(data.tableName + ' ' + data.entityName);
                    // 불일치한 정보들 보여주기
                }
            });
            
            // tab으로 - 엑셀별로
            // List<Map> - Map이 탭별로. 맵에는 excelName, db, doc
            // 일치하지 않는 테이블들 db 조회해서 select로 다 보여주기
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
    <div id="resultWrap">
    	<ul class="nav nav-tabs" role="tablist">
    		<c:forEach items="${data}" var="item" varStatus="status">
	    		<li>
	    			<a href="#resultTab${status.count}" data-toggle="tab">${item.excelName}</a>
	    		</li>	
	    	</c:forEach>
	    </ul>
    
    	<div class="tab-content">
    		<c:forEach items="${data}" var="item" varStatus="status">
	    		<div id="resultBox${status.count}" class="tab-pane">
	    			<table id="resultTable${status.count}" class="table ui celled">
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
	    	</c:forEach>
    	</div>
    </div>
</body>
</html>