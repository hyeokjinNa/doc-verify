<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=chrome'>
    <title>결과 확인</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/style.css'>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="/css/dataTables.semanticui.min.css">
    <link rel="stylesheet" type="text/css" href="/css/responsive.bootstrap5.css"/>
    <link rel="stylesheet" type="text/css" href="/css/buttons.dataTables.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <c:import url="/WEB-INF/view/template/link.jsp"/>
    
    <script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" charset="utf8" src="/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>
    <script type="text/javascript" src="js/dataTables.responsive.js"></script>
  	<script type="text/javascript" src="/js/responsive.bootstrap5.js"></script>
  	<script type="text/javascript" src="/js/dataTables.buttons.min.js"></script>
  	<script type="text/javascript" src="/js/buttons.html5.min.js"></script>
  	<script type="text/javascript" src="/js/jszip.min.js"></script>
  	
    <script>
        $.fn.DataTable.ext.pager.numbers_length = 5;

        $(function(){
            let checked;
            let datas = eval('${dataJson}');
            
            $('#resultBox0').show(); // class=active
            $('a:eq(0)').addClass('active');
            
            for(var i=0; i<datas.length; i++) {
            
            	$("#resultTable"+i).DataTable({

	                data : datas[i].doc, // doc 동적으로 바꿔보기
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
	                autoWidth : false,
	                paging : true,
	                pagingType : "simple_numbers_no_ellipses",
	                pageLength : 20, // displayLength
	                lengthMenu: [[10, 20, 30, -1], [10, 20, 30, "ALL"]],
	                //order : [[0, "asc"], [7, "desc"], [2, "asc"]], // 초기 정렬 [[열 번호, 정렬 순서]]
	                // serverSide : true,
	                processing : true,
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
	                            if(!cellData) {
	                                $(td).css('color', '#db2828');
	                            }
	                        },
	                        render : function(data, type, row) {
	                        	return data ? 'O' : 'X';
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
	
	                        } else { // 불일치한 컬럼 표시
	                        	
	                        	const columns = $("#resultTable"+i).DataTable().settings().init().columns;
	                            
	                            for(let i=0; i< columns.length; i++) {
	                            	data.wrongColumns.forEach(wrong => {
	                                    if(columns[i].data == wrong) {
	                                        $('td', row).eq(i).css('color', '#db2828'); // 값 불일치 컬럼 빨간색
	                                    }
	                                })
	                            }
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
	                		filename : '정의서 비교 결과 엑셀',
	                		title : '정의서 비교 결과',
	                		extend : 'excel',
	                		exportOptions: { // 자리수 콤마 넣기
	                			modifier: {
	                				page: 'current'
	                			}
	                		}
	                	}
	                ]
	            });
            }

            $("input[name=matchRadio]").change(function(){ // 새로고침 시 안 먹음
                checked = $(this).val();
                index = $("a").index($(".active"));
                
                if(checked == 'match') {
                    $("#resultTable"+index).DataTable().columns(9).search('O').draw();
                } else if(checked == 'notMatch') {
                    $("#resultTable"+index).DataTable().columns(9).search('X').draw();
                } else if(checked == 'all') {
                    $("#resultTable"+index).DataTable().columns(9).search('').draw();
                }
            });

            $(".table").on('click', 'tr', function(){
            	index = $("a").index($(".active"));
                var data = $("#resultTable"+index).DataTable().row(this).data();
                var tableName = data.tableName;
                
                if(!data.check) {
                	$(".modal-body").text('※ 해당 테이블이 DB에 존재하지 않아 비교가 불가능합니다.');
                	$("#modalWrap").modal('show');
                } else if(!data.match) {
                	var dbData = datas[index].db;
                	var wrongCols = data.wrongColumns;
                	var html = '<table id="modalTable" class="table table-bordered">' +
                		'<thead class="thead-light"><tr><th>불일치 컬럼</th><th>DB</th><th>정의서</th></tr></thead>' +
                		'<tbody>';
                	
                	for(var i=0; i<dbData.length; i++) {
                		if(dbData[i].tableName == data.tableName && dbData[i].physicalName == data.physicalName) {
                			for(var j=0; j<wrongCols.length; j++) {
                				var key = wrongCols[j];
                				
                				html += '<tr>';
                				html += '<td class="table-active">' + key + '</td>';
                				html += '<td>' + dbData[i][key] + '</td>';
                				html += '<td>' + data[key] + '</td>';
                				html += '</tr>';
                			}
                		}
                	}
                	
                	html += '</tbody></table>';
                	
                	$(".modal-body").html(html);
                    $("#modalWrap").modal('show');
                }
            });
            // 일치하지 않는 테이블들 db 조회해서 select로 다 보여주기
            // 검색 가능하게
        })
        
        
        function tabChange(event, index) {
	    	$('.tab-pane').hide();
	    	$('a').removeClass('active');
	    	$('a').eq(index).addClass('active');
	    	$("#resultBox"+index).show();
	    	$("input[name=matchRadio]:radio[value='all']").prop('checked', true);
	    	$("#resultTable"+index).DataTable().columns(9).search('').draw();
	    	$("#resultTable"+index).DataTable().columns.adjust().draw();
	    }
        
        
        $.fn.DataTable.ext.pager.simple_numbers_no_ellipses = function(page, pages) {
		   var numbers = [];
		   var buttons = $.fn.DataTable.ext.pager.numbers_length;
		   var half = Math.floor( buttons / 2 );
		
		   var _range = function ( len, start ){
		      var end;
		   
		      if ( typeof start === "undefined" ){ 
		         start = 0;
		         end = len;
		
		      } else {
		         end = start;
		         start = len;
		      }
		
		      var out = []; 
		      for ( var i = start ; i < end; i++ ){ out.push(i); }
		   
		      return out;
		   };
		    
		
		   if ( pages <= buttons ) {
		      numbers = _range( 0, pages );
		
		   } else if ( page <= half ) {
		      numbers = _range( 0, buttons);
		
		   } else if ( page >= pages - 1 - half ) {
		      numbers = _range( pages - buttons, pages );
		
		   } else {
		      numbers = _range( page - half, page + half + 1);
		   }
		
		   numbers.DT_el = 'span';
		
		   return [ 'previous', numbers, 'next' ];
		};
		
    </script>
</head>
<c:import url="/WEB-INF/view/template/header.jsp"/>
<body>
    <div>
        <h2 id="title" class="center">정의서 DB 일치 결과</h2>
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
        <select name="tableSelect" id="tableSelect" class="form-select">
            <option value="all">전체선택</option>
            <option value="TB_BL01I_001">TB_BL01I_001</option>
        </select>
    </div>
    <div id="resultWrap">
    	<ul class="nav nav-tabs" role="tablist">
    		<c:forEach items="${data}" var="item" varStatus="status">
	    		<li>
	    			<a onclick="tabChange(event, ${status.index})">${item.excelName}</a>
	    		</li>	
	    	</c:forEach>
	    </ul>
    
    	<div class="tab-content">
    		<c:forEach items="${data}" var="item" varStatus="status">
	    		<div id="resultBox${status.index}" class="tab-pane">
	    			<table id="resultTable${status.index}" class="table ui celled">
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
    
    <div class="modal" id="modalWrap" tabindex="-1" role="dialog" aria-labelledby="modalTitle">
    	<div class="modal-dialog" role="document">
    		<div class="modal-content">
    			<div class="modal-header">
    				<h4 class="modal-title" id="modalTitle">정의서 불일치 결과</h4>
    				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    					<span aria-hidden="true">&times;</span>
    				</button>
    			</div>
    			<div class="modal-body"></div>
    		</div>
    	</div>
    </div>
</body>
</html>