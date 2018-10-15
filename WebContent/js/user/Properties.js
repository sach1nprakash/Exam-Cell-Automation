var config={
		"protocol" : "http",
		"port" : "5898",
		"domain" : "localhost",
		"project" : "Exam_Cell"
}


function getURI(){
	return config.protocol +"://"+ config.domain +":"+ config.port +"/"+ config.project +"/api/" ;
}