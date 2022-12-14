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
        	const columnNames = {"tableName":"테이블명", "entityName":"엔티티명", "physicalName":"물리명", "logicalName":"논리명",
        			"dataType":"데이터타입", "length":"길이", "precision":"자리수", "pk":"PK", "notNull":"Not Null", "match":"일치"};
            let checked;
            let datas = eval('${dataJson}');
            
            for(var i=0; i<datas.length; i++) {
            
            	$("#resultTable"+i).DataTable({

	                data : datas[i].doc,
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
	                    {data : "match"},
	                    {data : "check"}
	                ],
	                lengthChange : true, // 표시 건수 기능 - 몇 건씩 보여줄지
	                searching : true, // 검색 기능
	                ordering : true, // 정렬 기능
	                info : true, // 정보 표시 - 몇 건 있는지
	                autoWidth : false,
	                paging : true,
	                pagingType : "full_numbers_no_ellipses",
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
	                    },
	                    {targets : 10, visible : false}
	                    // {responsivePriority : -3 , targets: 0}, // responsivePriority 숫자가 클수록 먼저 사라짐. 음수는 사라지지 않게
	                    // {responsivePriority : -2 , targets: 1}
					],
	                createdRow : function(row, data, dataIndex, cells) {
	                
	                	if(!data.check) {
	                    	this.api().row($(row)).remove().draw();
	                    }
	                
	                    if(!data.match && data.wrongColumns != null) {
	                        $(row).addClass('pointer');
	
	                        const columns = this.api().settings().init().columns;
	                            
                            for(let i=0; i< columns.length; i++) {
                            	data.wrongColumns.forEach(wrong => {
                                    if(columns[i].data == wrong) {
                                        $('td', row).eq(i).css('background', 'rgba(200, 0, 0, 0.5)'); // 값 불일치 컬럼 빨간색
                                    }
                                })
                            }
	                    }
	                    
	                },
	                language:
				    {
				        paginate:
				        {
				            previous : "<",
				            next : ">",
				            first : "<<",
				            last : ">>"
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
	                dom : '<"top"lf>rt<"bottom"Bpi><"clear">',
	                buttons : [
	                	{
	                		text : '다운로드',
	                		filename : '정의서 비교 결과 엑셀',
	                		title : '정의서 비교 결과',
	                		extend : 'excel',
	                		exportOptions: { // 자리수 콤마 넣기
	                			columns : ':visible'
	                		}
	                	},
	                	{
	                		text : '비교되지 않은 테이블',
	                		action : function(e, table, node, config) { // $.fn.dataTable.ext.buttons
	                			
	                			var index = $("a").index($(".active"));
			                	var uncheckData = datas[index].doc;
			                	var array = [];
			                	
			                	uncheckData.forEach(function(item, i) {
			                		if(!item.check) {
			                			array.push(item);
			                		}
			                	});
			                	
			                	$("#docTable").DataTable().destroy();
			                	$("#tableList").hide();
			                	$("#dbTable").hide();
			                	
	                			makeDocDataTable(array);
	                			$("#checkModal").modal('show');
	                		}
	                	}
	                ]
	            });
            } // datatable for문

            $("input[name=matchRadio]").change(function() { // 새로고침 시 안 먹음
                var checked = $(this).val();
                var index = $("a").index($(".active"));
                
                if(checked == 'match') {
                    $("#resultTable"+index).DataTable().columns(9).search('O').draw();
                } else if(checked == 'notMatch') {
                    $("#resultTable"+index).DataTable().columns(9).search('X').draw();
                } else if(checked == 'all') {
                    $("#resultTable"+index).DataTable().columns(9).search('').draw();
                }
            });

            $("#resultWrap .table").on('click', 'tr', function() {
            	var index = $("a").index($(".active"));
                var data = $("#resultTable"+index).DataTable().row(this).data();
                var tableName = data.tableName;
                if(!data.match) {
                	var dbData = datas[index].db;
                	var wrongCols = data.wrongColumns;
                	var html = '<table id="modalTable" class="table table-bordered ui">' +
                		'<thead class="thead-light"><tr><th>불일치 컬럼</th><th>DB</th><th>정의서</th></tr></thead>' +
                		'<tbody>';
                	
                	for(var i=0; i<dbData.length; i++) {
                		if(dbData[i].tableName == data.tableName && dbData[i].physicalName == data.physicalName) {
                			for(var j=0; j<wrongCols.length; j++) {
                				var key = wrongCols[j];
                				var dbWrong = '';
                				var docWrong = '';
                				
                				if(typeof dbData[i][key] != 'undefined') {
                					dbWrong = dbData[i][key];
                				}
                				
                				if(typeof data[key] != 'undefined') {
                					docWrong = data[key];
                				}
                				
                				html += '<tr>';
                				html += '<td class="table-active">' + columnNames[key] + '</td>';
                				html += '<td>' + dbWrong + '</td>';
                				html += '<td>' + docWrong + '</td>';
                				html += '</tr>';
                			}
                		}
                	}
                	
                	html += '</tbody></table>';
                	
                	$("#modalWrap .modal-body").html(html);
                    $("#modalWrap").modal('show');
                }
            });
            
            $('a:eq(0)').click();
            
            $("#docTable").on('click', 'tr', function() {
            	var data = $("#docTable").DataTable().row(this).data();
            	
            	$.ajax({
            		url : "/checkTableList",
            		type : "post",
            		data : {schema : data.schema},
            		success : function(data) {
            			$("#dbTable").DataTable().destroy();
            			$("#dbTable").hide();
            			
            			makeTableListDataTable(data);
            			$("#tableList").show();
            			$("#tableList tr").addClass("pointer");
            			$("#tableList").DataTable().columns.adjust().draw();
            		}
            	});
            });
            
            $("#tableList").on('click', 'tr', function() {
            	var data = $("#tableList").DataTable().row(this).data();
            	
            	$.ajax({
            		url : "/checkDbTable",
            		type : "post",
            		data : data,
            		success : function(data) {
            			$("#tableList").DataTable().destroy();
            			$("#tableList").hide();
            			
            			makeDbDataTable(data);
            			$("#dbTable").show();
            			$("#dbTable").DataTable().columns.adjust().draw();
            		}
            	});
            });
        });
        
        function tabChange(event, index) {
        	var table = $("#resultTable"+index).DataTable();
        	
	    	$('.tab-pane').hide();
	    	$('a').removeClass('active');
	    	$('a').eq(index).addClass('active');
	    	$("#resultBox"+index).show();
	    	$("input[name=matchRadio]:radio[value='all']").prop('checked', true);
	    	table.columns(9).search('').draw();
	    	table.columns.adjust().draw();
	    	
	    	// 불일치 정보 바꿔주기
	        var totalCount = table.data().count();
            var notMatchCount = table.column(9).data().filter(function(value, index) {
            	return !value;
            }).count();
            
            $('#resultPercent span').eq(0).text(notMatchCount); // 개수
            $('#resultPercent span').eq(1).text((notMatchCount/totalCount*100).toFixed(3)); // 비율
	    }
        
        function makeDocDataTable(array) {
        	
        	var docTable = $("#docTable").DataTable({
                data : array,
                columns : [
                    {data : "tableName"},
                    {data : "entityName"},
                    {data : "physicalName"},
                    {data : "logicalName"},
                    {data : "dataType"},
                    {data : "length"},
                    {data : "precision"}
                ],
                lengthChange : false,
                searching : true,
                ordering : false,
                info : false,
                autoWidth : false,
                paging : false,
                processing : true,
                columnDefs : [ // 각 컬럼들에 대한 커스터마이징
					{
			            defaultContent : "", // null값이면 undefined -> 빈 값으로 대체
			            targets : "_all"
			      	},
                    {
                    	targets : 6,
                    	render : function(data, type, row) {
                    		return row.scale == null ? data : data + "," + row.scale;
                    	}
                    }
				],
				language:
			    {
                    search : "검색 : ",
                    zeroRecords : "검색 결과가 없습니다",
                    loadingRecords : "로딩중..."
			    },
			    responsive : true,
			    initComplete : function (settings, json) {  
			        $("#docTable").wrap("<div style='overflow:auto; width:100%;position:relative;'></div>");  
			    }
        	});
        	
        	$("#docTable tr").addClass("pointer");
        }
        
		function makeTableListDataTable(array) {
			$("#tableList").DataTable().destroy();
        	
        	var tableList = $("#tableList").DataTable({
                data : array,
                columns : [
                    {
                    	data : "dbTableName",
                    	render : function(data, type, row) {
                        	return row.schema + "." + data;
                        }
                    }
                ],
                lengthChange : false,
                searching : true,
                ordering : false,
                info : false,
                autoWidth : false,
                paging : false,
                scrollY: "60vh",
                scrollCollapse: true,
                processing : true,
                columnDefs : [ // 각 컬럼들에 대한 커스터마이징
					{
			            defaultContent : "", // null값이면 undefined -> 빈 값으로 대체
			            targets : "_all"
			      	}
				],
				language:
			    {
                    search : "검색 : ",
                    zeroRecords : "검색 결과가 없습니다",
                    loadingRecords : "로딩중..."
			    }
        	});
        }
        
		function makeDbDataTable(array) {
        	
        	var dbTable = $("#dbTable").DataTable({
                data : array,
                columns : [
                    {data : "dbTableName"},
                    {data : "entityName"},
                    {data : "physicalName"},
                    {data : "logicalName"},
                    {data : "dataType"},
                    {data : "length"},
                    {data : "precision"}
                ],
                lengthChange : false,
                searching : true,
                ordering : false,
                info : false,
                autoWidth : false,
                paging : false,
                scrollY: "60vh",
                scrollCollapse: true,
                processing : true,
                columnDefs : [ // 각 컬럼들에 대한 커스터마이징
					{
			            defaultContent : "", // null값이면 undefined -> 빈 값으로 대체
			            targets : "_all"
			      	},
                    {
                    	targets : 6,
                    	render : function(data, type, row) {
                    		return row.scale == null ? data : data + "," + row.scale;
                    	}
                    }
				],
				language:
			    {
                    search : "검색 : ",
                    zeroRecords : "검색 결과가 없습니다",
                    loadingRecords : "로딩중..."
			    }
        	});
        }
        
        
		$.fn.DataTable.ext.pager.full_numbers_no_ellipses = function(page, pages){
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

			   return [ 'first', 'previous', numbers, 'next', 'last' ];
			};
		
    </script>
</head>
<c:import url="/WEB-INF/view/template/header.jsp"/>
<body>
    <div>
        <h2 id="title" class="center">정의서 DB 일치 결과</h2>
    </div>
    <div class="center">
    	<div class="form-check-inline">
	        <input type="radio" id="all" class="form-check-input" name="matchRadio" value="all" checked>
	        <label for="all" class="form-check-label">전체</label>
	        <input type="radio" id="match" class="form-check-input" name="matchRadio" value="match">
	        <label for="match" class="form-check-label">일치</label>
	        <input type="radio" id="notMatch" class="form-check-input" name="matchRadio" value="notMatch">
	        <label for="notMatch" class="form-check-label">불일치</label>
	    </div>
    </div>
    <div id="resultPercent">
    	<p>불일치 개수 : <span></span> 개</p>
    	<p>불일치 비율 : <span></span> %</p>
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
    
    <div class="footer">
    	<button type="button" class="btn btn-primary"><a href="/">뒤로</a></button>
    </div>
    
    <div class="modal" id="modalWrap" tabindex="-1" role="dialog" aria-labelledby="modalTitle">
    	<div class="modal-dialog modal-dialog-centered" role="document">
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
    
    <div class="modal" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="checkModalTitle">
    	<div class="modal-dialog modal-dialog-centered" role="document">
    		<div class="modal-content">
    			<div class="modal-header">
    				<h4 class="modal-title" id="checkModalTitle">비교되지 않은 테이블 리스트</h4>
    				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    					<span aria-hidden="true">&times;</span>
    				</button>
    			</div>
    			<div class="modal-body">
    				<table id="checkModalTable" class="table table-bordered">
			        	<thead class="thead-light">
			        		<tr>
			        			<th class="center">정의서</th>
			        			<th class="center">DB</th>
			        		</tr>
			        	</thead>
			        	<tbody>
			        		<tr>
			        			<td style="width:50%">
			        				<table id="docTable" class="table ui celled">
			                			<thead>
			                				<tr>
			                					<th>테이블명</th>
			                					<th>엔티티명</th>
			                					<th>물리명</th>
			                					<th>논리명</th>
			                					<th>데이터타입</th>
			                					<th>길이</th>
			                					<th>자리수</th>
			                				</tr>
			                			</thead>
			                		</table>
			        			</td>
			        			<td>
			        				<table id="tableList" class="table ui celled">
			        					<thead>
			        						<tr>
			        							<th>테이블명</th>
			        						</tr>
			        					</thead>
			        				</table>
			        				<table id="dbTable" class="table ui celled">
			        					<thead>
			        						<tr>
			        							<th>테이블명</th>
			                					<th>엔티티명</th>
			                					<th>물리명</th>
			                					<th>논리명</th>
			                					<th>데이터타입</th>
			                					<th>길이</th>
			                					<th>자리수</th>
			        						</tr>
			        					</thead>
			        				</table>
			        			</td>
			        		</tr>
			        	</tbody>
			        </table>
    			</div>
    		</div>
    	</div>
    </div>
</body>
</html>