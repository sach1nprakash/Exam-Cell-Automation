application.factory("StudentService", function() {

	var student = {};

	student.add = function(det) {
		student.dateOfBirth = det.dateOfBirth.substring(0, 9);
		student.studentId = det.studentId;
		student.studentName = det.studentName;
		student.branch = det.branch;
		student.sem = det.sem;
		student.email = det.email;

	};
	return student;
});

application.controller("StudentController", function($scope, $http, $location,
		StudentService) {

	$scope.studentForm = {}
	$scope.studentForm.message = null
	$scope.studentForm.dateOfBirth = null
	$scope.studentForm.studentId = null
	$scope.studentForm.studentName = null
	$scope.studentForm.branch = null
	$scope.studentForm.sem = null
	$scope.studentForm.email = null

	$scope.studentForm.submitTheForm = function() {
		$scope.studentForm.message = null

		$scope.studentForm.dateOfBirth = $scope.studentForm.dateOfBirth
				.toLocaleString();
		var data = JSON.stringify($scope.studentForm);
		$http.post(URI + "Student/validate", data).then(function(response) {
			alert("Successfully Validated!")
			$scope.studentForm.message = response.data.message;
			$scope.studentForm.studentName = response.data.studentName;
			$scope.studentForm.branch = response.data.branch;
			$scope.studentForm.sem = response.data.sem;
			$scope.studentForm.email = response.data.email;
			console.log(data);
			StudentService.add($scope.studentForm)
			$location.path('/dash')

		}, function(response) {
			alert("Failed to validate!")
			$scope.studentForm.message = response.data.message;

		})
	}

});

application.controller("AdminController", function($scope, $http, $location) {

	$scope.adminForm = {}
	$scope.adminForm.adminId = null
	$scope.adminForm.password = null
	$scope.adminForm.message = null

	$scope.adminForm.submitTheForm = function() {
		$scope.adminForm.message = null
		var data = JSON.stringify($scope.adminForm);
		console.log(data);

		$http.post(URI + "Admin/validate", data).then(function(response) {
			alert("Validated Admin!")
			$scope.adminForm.message = response.data.message;
			$location.path('/adminDash')
		}, function(response) {
			alert('Failed')
			$scope.adminForm.message = response.data.message;

		})
	}
	history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function() {
		history.pushState(null, null, document.URL);
	});

});

application.controller("DashBoardController", function($scope, $http,
		$routeParams, StudentService) {

	$scope.studentDash = {};
	$scope.studentDash.studentId = StudentService.studentId;
	$scope.studentDash.dateOfBirth = StudentService.dateOfBirth;
	$scope.studentDash.studentName = StudentService.studentName;
	$scope.studentDash.branch = StudentService.branch;
	$scope.studentDash.sem = StudentService.sem;
	$scope.studentDash.email = StudentService.email;

	$scope.studentDash.quot;
	$scope.studentDash.announcement = [];
	$scope.studentDash.announcementDates = [];

	$http.get(URI + "Student/fetchannouncements").then(function(response) {

		alert(response.data);
		for (i = 0; i < response.data.length; i++) {
			var annSplit = response.data[i].split("``");
			$scope.studentDash.announcement.push(annSplit[0]);
			$scope.studentDash.announcementDates.push(annSplit[1]);

		}
		console.log($scope.studentDash.announcement);
		console.log($scope.studentDash.announcementDates);

	}, function(response) {

		alert("Could not fetch announcements!");
	});

	$http.get(URI + "Student/getquote").then(function(response) {
		$scope.studentDash.quot = response.data.message;

	}, function(response) {

		alert("Failed!");
	});

	history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function() {
		history.pushState(null, null, document.URL);
	});

});

application.controller("RegistrationController", function($scope, $http,
		$filter, StudentService) {

	$scope.currentSem = StudentService.sem;
	$scope.isDisabled = true;
	$scope.studentSem = {}
	$scope.studentSem.sem = null;
	/* $scope.studentSem.message=null; */
	$scope.studentSem.courses;
	$scope.studentSem.result = [];
	$scope.studentSem.result2 = [];
	$scope.studentSem.studentId = StudentService.studentId;
	$scope.studentSem.branch = StudentService.branch;
	$scope.studentSem.message = null;
	$scope.studentSem.fetch = function() {
		$scope.studentSem.message = null;
		$scope.studentSem.mandatory_list = [];
		$scope.studentSem.other_list = [];
		if ($scope.studentSem.sem <= StudentService.sem) {
			var studSemData = JSON.stringify($scope.studentSem);
			/*
			 * console.log(studSemData); console.log($scope.studentSem.branch);
			 */
			$http.post(URI + "register/listofcourses", studSemData).then(
					function(response) {

						/* console.log(response.data); */
						/* $scope.courses.push(response.data.courses); */
						// ===============================================================================================
						$scope.studentSem.courses = response.data;

						angular.forEach($scope.studentSem.courses, function(
								value, key) {

							if ($scope.studentSem.courses[key] == "mandatory") {
								$scope.studentSem.result.push(key);
								$scope.studentSem.mandatory_list.push({
									key : key,
									value : value
								});
								/* $scope.studentSem.mandatory_list.push(key); */
							} else {
								$scope.studentSem.other_list.push({
									key : key,
									value : value
								});
							}

						})
						/*
						 * console.log($scope.mandatory_list);
						 * console.log($scope.other_list);
						 */

						/* alert($scope.studentSem.courses); */
						$scope.isDisabled = false;

					}, function(response) {

						alert("failed!")
						/* $scope.adminForm.message = response.data.message; */

					})
		} else
			alert("Given Semester cannot be greater than current Semester");
	}

	$scope.reset=function(){
		
		$scope.studentSem.result = [];
		$scope.studentSem.result2 = [];
		$scope.studentSem.courses=null;
		
	}
	$scope.cost = function() {

		$scope.costdata = {}
		$scope.costdata.cost;
		$scope.costdata.ddnumber;
		$scope.costdata.studentId = StudentService.studentId;
		$scope.costdata.registerCourses = [];
		for (i = 0; i < $scope.studentSem.result.length; i++) {
			if ($scope.studentSem.result[i] != null)
				$scope.costdata.registerCourses
						.push($scope.studentSem.result[i]);
		}
		for (i = 0; i < $scope.studentSem.result2.length; i++) {
			if ($scope.studentSem.result2[i] != null)
				$scope.costdata.registerCourses
						.push($scope.studentSem.result2[i]);
		}
		console.log($scope.costdata.registerCourses);
		var cData = JSON.stringify($scope.costdata);
		/* console.log(cData); */
		$http.post(URI + "register/fetchcost", cData).then(function(response) {

			$scope.costdata.cost = response.data.message;
			/* alert("Success!"); */

		}, function(response) {

			alert("Failed!");

		});

	}

	$scope.register = function() {

		$scope.registerdata = {}
		$scope.registerdata.studentId = StudentService.studentId;
		$scope.registerdata.registerCourses = [];
		$scope.registerdata.message = null;
		for (i = 0; i < $scope.studentSem.result.length; i++) {
			if ($scope.studentSem.result[i] != null)
				$scope.registerdata.registerCourses
						.push($scope.studentSem.result[i]);
		}
		for (i = 0; i < $scope.studentSem.result2.length; i++) {
			if ($scope.studentSem.result2[i] != null)
				$scope.registerdata.registerCourses
						.push($scope.studentSem.result2[i]);
		}
		$scope.registerdata.cost = $scope.costdata.cost;
		$scope.registerdata.ddnumber = '' + $scope.costdata.ddnumber;
		var registerData = JSON.stringify($scope.registerdata);
		console.log(registerData);

		$http.post(URI + "register/registerCourses", registerData).then(
				function(response) {
					$scope.studentSem.message = response.data.message;
					$scope.isDisabled = true;
					/* alert("Registered!"); */
					$scope.registerdata.message = response.data.message;
				}, function(response) {
					$scope.registerdata.message = response.data.message;
					alert("Could not register");
				})

	}
});

application
		.controller(
				"HallTicketController",
				function($scope, $http, $filter, StudentService) {

					// alert("Inside hall ticket controller");
					$scope.enDownload = true;
					$scope.hallTicket = {};
					$scope.hallTicket.studentId = StudentService.studentId;
					$scope.hallTicket.branch = StudentService.branch;
					$scope.hallTicket.sem = StudentService.sem;
					$scope.hallTicket.name = StudentService.studentName;

					$scope.hallTicket.cId = []
					$scope.hallTicket.date = []
					$scope.hallTicket.time = []
					$scope.hallTicket.respons_list = []

					var HtData = JSON.stringify($scope.hallTicket);
					console.log(HtData);
					$http
							.post(URI + "Student/gethtdata", HtData)
							.then(
									function(response) {

										/* alert(response.data) */
										/*
										 * $scope.hallTicket.cId =
										 * response.data;
										 */
										console.log(response.data);
										$scope.message = null;
										if (response.data == 'Courses not yet Approved') {
											$scope.message = response.data;
										} else {
											$scope.hallTicket.respons_list = response.data;
											for (i = 0; i < $scope.hallTicket.respons_list.length; i++) {
												$scope.enDownload = false;
												if ($scope.hallTicket.respons_list[i]
														.charAt(0) == "c"
														|| $scope.hallTicket.respons_list[i]
																.charAt(0) == "e"
														|| $scope.hallTicket.respons_list[i]
																.charAt(0) == "a") {
													$scope.hallTicket.cId
															.push($scope.hallTicket.respons_list[i]);
												} else if ($scope.hallTicket.respons_list[i]
														.charAt(0) == "F"
														|| $scope.hallTicket.respons_list[i]
																.charAt(0) == "A") {
													$scope.hallTicket.time
															.push($scope.hallTicket.respons_list[i]);
												} else if ($scope.hallTicket.respons_list[i]
														.charAt(0) == "1"
														|| $scope.hallTicket.respons_list[i]
																.charAt(0) == "2"
														|| $scope.hallTicket.respons_list[i]
																.charAt(0) == "3") {
													$scope.enDownload = true;
													$scope.hallTicket.date
															.push($scope.hallTicket.respons_list[i]);
												}

											}
										}
										/* console.log($scope.hallTicket.time); */

									}, function(response) {

										alert("Failed to fetch courses")

									})
					/*
					 * console.log($scope.hallTicket.cId);
					 * console.log($scope.hallTicket.time);
					 * console.log($scope.hallTicket.date);
					 */

				});

application.controller("AdminContactController", function($scope, $http,
		StudentService) {
	/* alert("Inside Controller") */
	$scope.ack = false;
	$scope.msg = {}
	$scope.msg.messsage;
	$scope.msg.ack;
	$scope.msg.studentId = StudentService.studentId;
	$scope.msg.email = StudentService.email;

	$scope.msg.submitToAdmin = function() {
		var msgdata = JSON.stringify($scope.msg);
		console.log(msgdata);
		$http.post(URI + "Student/toadmin", msgdata).then(function(response) {

			/*
			 * alert(response.data.messsage); alert("Success");
			 */
			console.log(msgdata);
			$scope.ack = true;
			$scope.msg.ack = response.data.messsage;

		}, function(response) {
			alert("Could not submit your message!");

		})
	}
});

application
		.controller(
				"ResultController",
				function($scope, $http, StudentService) {

					/*alert("Inside Results controller");*/
					$scope.resNotpub=false;
					$scope.resPub=false;
					$scope.getResults = {};
					$scope.getResults.studentId = StudentService.studentId;
					$scope.getResults.sem;
					$scope.dispResults = {};
					$scope.dispResults.name = StudentService.studentName
					$scope.dispResults.regno = StudentService.studentId;
					$scope.dispResults.dept = StudentService.branch
					$scope.dispResults.semester = StudentService.sem


					$scope.getResults.res = function() {
						$scope.dispResults.subjs = [];
						$scope.dispResults.mrks = [];
						$scope.dispResults.grade = [];
						$scope.dispResults.result = [];
						$scope.dispResults.sgpa=null;
						$scope.dispResults.cgpa=null;
						$scope.resNotpub=false;
						$scope.resPub=false;
						if (StudentService.sem < $scope.getResults.sem) {
							alert("Pick a semester within your current semester!");
						} else {
							$scope.resNotpub=false;
							$scope.resPub=false;
							var resData = JSON.stringify($scope.getResults);
							console.log($scope.getResults);
							$http
									.post(URI + "Student/getres", resData)
									.then(
											function(response) {

												/*alert(response.data);*/
												if (response.data[0] == "result not yet published") {
														$scope.resNotpub=true;
												}
												
												 else {
													$scope.resPub=true;
													$scope.dispResults.respons_list = response.data;
													for (i = 0; i < $scope.dispResults.respons_list.length; i++) {
														if ($scope.dispResults.respons_list[i]
																.charAt(0) == "s"
																&& $scope.dispResults.respons_list[i]
																		.charAt(1) == "g") {
															var sgSplit = $scope.dispResults.respons_list[i]
																	.split("-");
															$scope.dispResults.sgpa = sgSplit[1];
														} else if ($scope.dispResults.respons_list[i]
																.charAt(0) == "c"
																&& $scope.dispResults.respons_list[i]
																		.charAt(1) == "g") {
															var cgSplit = $scope.dispResults.respons_list[i]
																	.split("-");
															$scope.dispResults.cgpa = cgSplit[1];
														} else if ($scope.dispResults.respons_list[i]
																.charAt(0) == "c"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "e"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "a") {
															$scope.dispResults.subjs
																	.push($scope.dispResults.respons_list[i]);
														} else if ($scope.dispResults.respons_list[i]
																.charAt(0)
																.match(/[0-9]/)) {
															$scope.dispResults.mrks
																	.push($scope.dispResults.respons_list[i]);
														} else if ($scope.dispResults.respons_list[i] == "Pass"
																|| $scope.dispResults.respons_list[i] == "Fail") {
															$scope.dispResults.result
																	.push($scope.dispResults.respons_list[i]);
														} else if ($scope.dispResults.respons_list[i]
																.charAt(0) == "A"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "B"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "C"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "D"
																|| $scope.dispResults.respons_list[i]
																		.charAt(0) == "F") {
															$scope.dispResults.grade
																	.push($scope.dispResults.respons_list[i]);
														}

													}
												}
												console
														.log($scope.dispResults.subjs);
												console
														.log($scope.dispResults.mrks);
												console
														.log($scope.dispResults.grade);
												console
														.log($scope.dispResults.result);
												console
														.log($scope.dispResults.sgpa);
												console
														.log($scope.dispResults.cgpa);

											}, function(response) {

												alert(response.data);

											});
						}

					}

				});

application.controller("ScheduleController", function($scope, $http) {

	alert("Inside sch controller");

	$scope.isSchDisabled = true;
	$scope.messEn = false;
	$scope.schedule = {}
	$scope.schedule.sem;
	$scope.schedule.branch;
	$scope.schedule.message = null;

	$scope.schedule.subjects = null;

	// $scope.schedule.cId=[];
	$scope.schedule.date = [];
	$scope.schedule.time = [];

	$scope.schedule.fetchcourses = function() {
		var toFetch = {}
		toFetch.sem = $scope.schedule.sem;
		toFetch.branch = $scope.schedule.branch;

		var fetch = JSON.stringify(toFetch);
		$http.post(URI + "Admin/courselist", fetch).then(function(response) {

			$scope.isSchDisabled = false;
			$scope.messEn = false;
			/* $scope.courses.push(response.data.courses); */
			if (response.data == "Not yet Listed")
				$scope.schedule.message = "Not yet Listed"
			else
				$scope.schedule.subjects = response.data;

			$scope.isFixDisabled = false;

		}, function(response) {

			alert("failed!")
			/* $scope.adminForm.message = response.data.message; */

		})

	}

	$scope.schedule.createSchedule = function() {

		var scheduleData = JSON.stringify($scope.schedule);
		console.log(scheduleData);
		console.log($scope.schedule.date);
		console.log($scope.schedule.time);
		// console.log($scope.schedule.cId);

		$http.post(URI + "Admin/setschedule", scheduleData).then(
				function(response) {
					alert("success");
					$scope.schedule.message = response.data.message;
					$scope.isSchDisabled = true;
					$scope.messEn = true;
				}, function(response) {
					alert("failure")
					$scope.schedule.message = response.data.message;
				})

	}
});

application.controller("RequestsController", function($scope, $http) {

	$scope.noReqs=false;
	$scope.approve_dd = [];
	$scope.reqstSubs = {}
	$scope.reqstSubs.subjects = []
	$scope.reqstSubs.dates = []
	$scope.reqstSubs.time = []
	$scope.reqstSubs.studentId = []
	$scope.reqstSubs.message = null;
	$scope.req_s = {}
	$scope.req_s.studentId = null;
	$scope.req_s.cId = []
	$scope.req_s.date = []
	$scope.req_s.timing = []

	$scope.dd_list = [];
	$scope.amount_list = [];
	$scope.res;
	$scope.subs = [];
	$scope.res_list = [];
	$scope.res_key = [];
	/* alert("Inside req controller"); */
	$http.get(URI + "Admin/fetchreq").then(
			function(response) {

				if (response.data == "Students Not registered yet") {
					$scope.reqstSubs.message = "Students Not registered yet"
					$scope.noReqs=true;
				} else {
					$scope.reqdata = response.data;
					alert($scope.reqdata);
					var res1 = null;

					for (i = 0; i < $scope.reqdata.length; i++) {

						$scope.res = $scope.reqdata[i].split("-");
						if (i < $scope.reqdata.length - 1) {
							console.log(i + " data " + $scope.reqdata[i + 1]);
							var res1 = $scope.reqdata[i + 1].split("-");
						}
						$scope.res_list.push($scope.res[1])
						/* $scope.reqstSubs.courseId.push($scope.res[1]) */
						if ($scope.res[0] != res1[0]
								|| i == $scope.reqdata.length - 1) {
							$scope.res_key = $scope.res[0];
							$scope.dd_list.push($scope.res[2]);
							$scope.amount_list.push($scope.res[3]);
							/* $scope.req_s.studentId = $scope.res_key; */
							$scope.subs.push({
								key : $scope.res_key,
								value : $scope.res_list
							});
							$scope.reqstSubs.subjects.push($scope.res_list);
							$scope.reqstSubs.studentId.push($scope.res_key);
							res1 = null;
							$scope.res_list = [];
						}
						/* console.log($scope.subs); */
						console.log($scope.dd_list);
						console.log($scope.amount_list);
					}
				}

			}, function(response) {

				alert("Failed!");

			})

	$scope.reqstSubs.createSchedule = function(value) {

		$scope.req_s.courseId = $scope.reqstSubs.subjects[value];
		$scope.req_s.date = $scope.reqstSubs.dates;
		$scope.req_s.timing = $scope.reqstSubs.time;
		$scope.req_s.studentId = $scope.reqstSubs.studentId[value]

		console.log($scope.req_s.studentId)
		console.log($scope.req_s.courseId)
		console.log($scope.req_s.date);
		console.log($scope.req_s.timing);

		var scheduleData = JSON.stringify($scope.req_s);

		$http.post(URI + "Admin/setbacklogschedule", scheduleData).then(
				function(response) {
					alert("success");
					// $scope.schedule.message=response.data.message;
				}, function(response) {
					alert("failure")
					// $scope.schedule.message=response.data.message;
				})
	}
});

application.controller("ViewMessagesController", function($scope, $http,$window) {

	$scope.ismsgEmpty=false;
	/* alert("Inside messages controller") */
	$scope.messagesFinal = [];
	$scope.msgdata = [];
	$scope.email_list=[];

	
	$http.get(URI + "Admin/getqueries").then(
			function(response) {
				alert("Success")
				if (response.data == "NO Queries Asked")
				{
					$scope.ismsgEmpty=true;

				}
				else {
					$scope.msgdata = response.data;
					alert($scope.msgdata);
					$scope.msglist = []
					for (i = 0; i < $scope.msgdata.length; i++) {
						$scope.msgsplit = $scope.msgdata[i].split("``");

						if (i < $scope.msgdata.length - 1) {
							console.log(i + " data " + $scope.msgdata[i + 1]);
							var msgsplit1 = $scope.msgdata[i + 1].split("``");
						}

						$scope.msglist.push($scope.msgsplit[1]);

						/* console.log($scope.msglist); */
						if ($scope.msgsplit[0] != msgsplit1[0]
								|| i == $scope.msgdata.length - 1) {
							$scope.msg_key = $scope.msgsplit[0];
							$scope.email_list.push($scope.msgsplit[2])
							$scope.messagesFinal.push({
								key : $scope.msg_key,
								value : $scope.msglist
							});
							$scope.msglist = [];
						}
					}
				}
				console.log($scope.messagesFinal);
				console.log($scope.email_list);
			}, function(response) {

				alert("Failed")

			})
			

			$scope.sendMail = function(value) {

				$window.open("mailto:" + $scope.email_list[value]+ "?subject=Responding to"+
						"&body=Hi Student,");
			};

});

application
		.controller(
				"PublishResultsController",
				function($scope, $http) {

					alert("Inside publish results controller")

					$scope.publishRes = {};
					$scope.publishRes.sem;
					$scope.publishRes.studentId;
					$scope.publishRes.messages;
					$scope.publishRes.courses = [];
					$scope.publishRes.marks = []
					$scope.publishRes.grade = []
					$scope.publishRes.result = []
					$scope.student_list = []
					
					$scope.courseL = []
					$scope.resultFinal = []
					$scope.tempStudent;
					$scope.tempSubList = []

					$scope.getStudents = function() {
						$scope.student_list=[]
						$scope.Stud = {};
						$scope.Stud.sem = $scope.publishRes.sem;
						var sdata = JSON.stringify($scope.Stud);
						$http
								.post(URI + "Admin/getregcourses", sdata)
								.then(
										function(response) {

											alert(response.data);
											if (response.data == "NO students registerd for this semster")
												$scope.publishRes.messages = "NO students registerd for this semster"
											else {
												$scope.publishResdata = response.data;
												$scope.resultFinal=[];
												for (i = 0; i < $scope.publishResdata.length; i++) {
													$scope.coursesplit = $scope.publishResdata[i]
															.split("-");

													if (i < $scope.publishResdata.length - 1) {
														console
																.log(i
																		+ " data "
																		+ $scope.publishResdata[i + 1]);
														var coursesplit1 = $scope.publishResdata[i + 1]
																.split("-");
													}
													$scope.courseL
															.push($scope.coursesplit[1]);

													if ($scope.coursesplit[0] != coursesplit1[0]
															|| i == $scope.publishResdata.length - 1) {
														
														$scope.student_list
																.push($scope.coursesplit[0]);
														$scope.stu=$scope.coursesplit[0];
														$scope.resultFinal
																.push({
																	key : $scope.stu,
																	value : $scope.courseL
																})
														$scope.tempStudent = $scope.student;
														$scope.courseL=[];
													}
												}
											}
											// console.log($scope.student_list);
											 console.log($scope.resultFinal);
											 console.log($scope.student_list);
										}, function(response) {

											alert("Failed!");
										})

					}

					$scope.displaySubjects = function() {
						$scope.tempSubList=[];
						angular
								.forEach(
										$scope.resultFinal,
										function(value, key) {
											console.log(value.key + ': '
													+ value.value);
											if (value.key == $scope.publishRes.studentId) {
												for (i = 0; i < value.value.length; i++) {
													$scope.tempSubList
															.push(value.value[i]);
												}

											}
										});
						/* console.log($scope.tempSubList); */
					}

					$scope.submitResults = function() {

						$scope.submitRes = {}
						/*
						 * var markstring = []; for (i = 0; i <
						 * $scope.publishRes.marks.length; i++) {
						 * markstring.push($scope.publishRes.marks.value[i] +
						 * '') }
						 */
						$scope.submitRes.marks = []
						$scope.submitRes.studentId = $scope.publishRes.studentId;
						// $scope.submitRes.marks = $scope.publishRes.marks;
						$scope.submitRes.grade = $scope.publishRes.grade;
						$scope.submitRes.result = $scope.publishRes.result;
						$scope.submitRes.courseId = $scope.tempSubList;

						for (i = 0; i < $scope.publishRes.marks.length; i++) {
							$scope.submitRes.marks
									.push($scope.publishRes.marks[i].toString());
						}

						var resultdata = JSON.stringify($scope.submitRes);

						$http.post(URI + "Admin/submitresult", resultdata)
								.then(function(response) {
									alert("Success");

								}, function(response) {

									alert("Fail");
								});

						/*
						 * console.log($scope.publishRes.marks);
						 * console.log($scope.publishRes.grade);
						 * console.log($scope.publishRes.result)
						 */;
					}

				});

application.controller("AnnouncementController", function($scope, $http) {

	/* alert("Inside announcement controller") */

	$scope.announce = {}
	$scope.announce.message;
	$scope.quot = {}
	$scope.quot.message;
	$scope.quotes = [];

	$http.get(URI + "Admin/getquotes").then(function(response) {

		/*alert(response.data);*/
		for (i = 0; i < response.data.length; i++) {
			$scope.quotes.push(response.data[i]);
		}
		console.log($scope.quotes);

	}, function(response) {

		alert("Could not fetch quotes");
	});

	$scope.announce.makeAnnouncement = function() {

		console.log($scope.announce.annMessg);
		var announceData = JSON.stringify($scope.announce);
		$http.post(URI + "Admin/storeannouncement", announceData).then(
				function(response) {

					alert("Stored Announcement");

				}, function(respponse) {

					alert("Failed!");

				});

	}
	$scope.quot.fixQuote = function() {
		console.log($scope.quot.quote);
		var quoateData = JSON.stringify($scope.quot);
		$http.post(URI + "Admin/setquote", quoateData).then(function(response) {
			alert(response.data + " " + "success");

		}, function(response) {

			alert("Could not set quote of the day");
		});

	}

});
