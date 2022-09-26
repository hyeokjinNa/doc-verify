SELECT 
	COL.OWNER AS SCHEMA
	, COL.TABLE_NAME
	, CMT_T.COMMENTS AS ENTITY_NAME
	, COL.COLUMN_NAME AS PHYSICAL_NAME 
	, CMT_C.COMMENTS AS LOGICAL_NAME
	, COL.DATA_TYPE AS DATA_TYPE
	, COL.DATA_LENGTH AS LENGTH
	, COL.DATA_PRECISION AS PRECISION
	, COL.DATA_SCALE AS SCALE
	, CASE WHEN COL.NULLABLE = 'N' THEN 'Y' ELSE 'X' END AS NOT_NULL
	, CASE WHEN CONS.CON_TYPE = 'PRIMARY KEY' THEN 'Y' ELSE 'X' END AS PK
FROM ALL_TAB_COLUMNS AS COL
LEFT OUTER JOIN ALL_TAB_COMMENTS AS CMT_T ON COL.OWNER = CMT_T.OWNER AND COL.TABLE_NAME = CMT_T.TABLE_NAME
LEFT OUTER JOIN ALL_COL_COMMENTS AS CMT_C ON COL.OWNER = CMT_C.OWNER AND COL.TABLE_NAME = CMT_C.TABLE_NAME AND COL.COLUMN_NAME = CMT_C.COLUMN_NAME
LEFT OUTER JOIN (
	SELECT CONS_COL.OWNER, CONS_COL.TABLE_NAME, CONS_COL.COLUMN_NAME, CONS_ALL.CON_TYPE
	FROM ALL_CONS_COLUMNS AS CONS_COL
	LEFT OUTER JOIN ALL_CONSTRAINTS AS CONS_ALL ON CONS_COL.OWNER = CONS_ALL.OWNER AND CONS_COL.CONSTRAINT_NAME = CONS_ALL.CONSTRAINT_NAME
) AS CONS ON COL.OWNER = CONS.OWNER AND COL.TABLE_NAME = CONS.TABLE_NAME AND COL.COLUMN_NAME = CONS.COLUMN_NAME AND CONS.CON_TYPE = 'PRIMARY KEY'
WHERE COL.OWNER = ? AND COL.TABLE_NAME = ?
