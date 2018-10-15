//-----------------------REGISTERING THE APPLICATION AND DEPENDENCIES-----------------------

var application = angular.module("Application", [ "ngRoute" ]);

// ----------------------CONFIGURING THE APPLICATION------------------------

application.config([ '$routeProvider', function(routeProvider) {
	routeProvider.when('/', {
		templateUrl : 'partials/Login.html'
	}).when('/admin', {
		templateUrl : 'partials/admin.html',
		controller : 'AdminController'
	}).when('/student', {
		templateUrl : 'partials/student.html',
		controller : 'StudentController'
	}).when('/dash', {
		templateUrl : 'partials/Student/dash.html',
		controller : 'DashBoardController'
	}).when('/profile', {
		templateUrl : 'partials/Student/profile.html',
		controller : 'DashBoardController'
	}).when('/register', {
		templateUrl : 'partials/Student/register.html',
		controller : 'RegistrationController'
	}).when('/stuHall', {
		templateUrl : 'partials/Student/hallTicket.html',
		controller : 'HallTicketController'
	}).when('/result', {
		templateUrl : 'partials/Student/result.html',
		controller : 'ResultController'
	}).when('/AdmCont', {
		templateUrl : 'partials/Student/ContactAd.html',
		controller : 'AdminContactController'
	}).when('/adminDash', {
		templateUrl : 'partials/Admin/adminDash.html',
		controller : 'AnnouncementController'
	}).when('/adminReq', {
		templateUrl : 'partials/Admin/requests.html',
		controller : 'RequestsController'
	}).when('/adminSch', {
		templateUrl : 'partials/Admin/schedule.html',
		controller : 'ScheduleController'
	}).when('/adminMsg', {
		templateUrl : 'partials/Admin/messages.html',
		controller : 'ViewMessagesController'
	}).when('/adminPubRes', {
		templateUrl : 'partials/Admin/publishResults.html',
		controller : 'PublishResultsController'
	}).otherwise({
		redirectTo : '/'
	});
} ]);


var URI = getURI();

// -----------------------
