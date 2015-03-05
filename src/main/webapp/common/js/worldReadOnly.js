myRowStyler = function(index,row){
	if(!row.valid) {
		return inValidWorld;
	}
	return null;
},

worldIdColumn = {field : 'worldId',title : '织图ID',align : 'center', sortable: true, width : 60};