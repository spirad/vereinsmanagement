var Vereinsverwaltung = angular.module('Vereinsverwaltung', [ 'ngAnimate',
		'ngRoute', 'ui.bootstrap' ]);

Vereinsverwaltung.controller('MemberCtrl', function($scope, $http, $uibModal,
		$location, $route, memberService) {
	$scope.orderByField = 'lastName';
	$scope.reverseSort = false;

	$http.get('/Vereinsverwaltung/verein/mitglieder').success(function(data) {
		$scope.members = data.mitglieder;
		// console.log($scope.members);
	})

	$scope.selectedMember = memberService.member;
	$scope.selectMember = function(member) {
		memberService.member = member;
		console.log("selected memeber: " + $scope.selectedMember);
		$scope.go('/mitglied_edit');
	}
	
	$scope.saveNewMember = function(member) {
		memberService.member = member;
		console.log("new memeber: " + $scope.selectedMember);
		var promise = memberService.save();
		promise.then(
		// success callback
		function(response) {
			$scope.openJaNeinPopup("lg", response.data.message,
					"Soll ein weiterer User angelegt werden?").then(
					function(selectedItem) {
						if (selectedItem) {
							memberService.member = {};
							$route.reload();
							$scope.go('/mitglied_neu');
						} else {
							$scope.go('/');
						}
					})
		},
		// error callback
		function(response) {
			$scope.openErrorPopup("lg", "Fehler", response.data.message).then(
					function(selectedItem) {
						$scope.go('/');

					})
		});

	}

	$scope.inReadonlyMode = 1;
	$scope.toggleReadonly = function() {
		// if ($scope.readonly =="readonly") $scope.readonly = "";
		console.log("readonly clicked");
		$scope.inReadonlyMode = !$scope.inReadonlyMode;
	}

	$scope.saveChangedMember = function() {
		//if (!memberService.member) memberService.member = $scope.selectedMember;
		var promise = memberService.save();
		promise.then(
		// success callback
		function(response) {
			memberService.member = {};
			$route.reload();
			$scope.go('/');			
		},
		// error callback
		function(response) {
			$scope.openErrorPopup("lg", "Fehler", response.data.message).then(
					function(selectedItem) {
						$scope.go('/');

					})
		});
	}

	$scope.deleteMember = function() {
		$scope.openJaNeinPopup(
				"lg",
				"Wirklich löschen?",
				$scope.selectedMember.firstName + " "
						+ $scope.selectedMember.lastName).then(
				function(selectedItem) {
					if (selectedItem) {
						$route.reload();
						$scope.go('/mitglied_neu');
					} else {
						$scope.go('/');
					}
				})
		// var promise = memberService.save($scope.newMember);
		// promise.then(
		// // success callback
		// function(response) {
		// $scope.openJaNeinPopup("lg", response.data.message,
		// "Soll ein weiterer User angelegt werden?").then(
		// function(selectedItem) {
		// if (selectedItem) {
		// $route.reload();
		// $scope.go('/mitglied_neu');
		// } else {
		// $scope.go('/');
		// }
		// })
		// },
		// // error callback
		// function(response) {
		// $scope.openErrorPopup("lg", "Fehler", response.data.message).then(
		// function(selectedItem) {
		// $scope.go('/mitglied_neu');
		//
		// })
		// });
	}

	$scope.go = function(path) {
		$location.path(path);
	};

	$scope.getMandatnr = function() {
		$http.get('/Vereinsverwaltung/verein/mandatnr').success(function(data) {
			data.mandatNr;
		})
	}

	$scope.animationsEnabled = true;

	$scope.openJaNeinPopup = function(size, header, question) {
		$scope.popup = {
			header : header,
			question : question
		};

		var modalInstance = $uibModal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'PopupJaNeinCtrl.html',
			controller : 'PopupCtrl',
			size : size,
			scope : $scope
		});

		return modalInstance.result;
	};

	$scope.openErrorPopup = function(size, header, error) {
		$scope.popup = {
			header : header,
			error : error
		};

		var modalInstance = $uibModal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'PopupErrorCtrl.html',
			controller : 'PopupCtrl',
			size : size,
			scope : $scope
		});

		return modalInstance.result;
	};

	$scope.toggleAnimation = function() {
		$scope.animationsEnabled = !$scope.animationsEnabled;
	};

});

Vereinsverwaltung.controller('PopupCtrl', function($scope, $uibModalInstance) {

	$scope.ok = function() {
		$uibModalInstance.close(1);
	};

	$scope.cancel = function() {
		$uibModalInstance.close(0);
	};
});

Vereinsverwaltung.factory("memberService", function($http) {
	var member = {};
	return {
		getYear : function() {
			now = new Date();
			return now.getFullYear();
		},
		save : function() {
			console.log(this.member);
			var data = encodeURIComponent(JSON.stringify({
				mandat : (this.member.mandat == undefined) ? 0
						: this.member.mandat,
				title : (this.member.title == undefined) ? ""
						: this.member.title,
				firstName : (this.member.firstName == undefined) ? ""
								: this.member.firstName,
				lastName : this.member.lastName,
				street : (this.member.street == undefined) ? ""
						: this.member.street,
				city : (this.member.city == undefined) ? "" : this.member.city,
				phone : (this.member.phone == undefined) ? ""
						: this.member.phone,
				cellPhone : (this.member.cellPhone == undefined) ? ""
						: this.member.cellPhone,
				email : (this.member.email == undefined) ? ""
						: this.member.email,
				payment : this.member.payment,
				entryYear : this.getYear(),
				PLZ : (this.member.PLZ == undefined) ? "" : this.member.PLZ,
				remark : (this.member.street == undefined) ? "nichts"
						: this.member.remark,
				status : (this.member.status == undefined) ? "unbek."
						: this.member.status,
				geschlecht : (this.member.geschlecht == undefined) ? "unbek."
						: this.member.geschlecht,
				paymentMonth : this.member.paymentMonth
			}));
			console.log(data);
			return $http
					.post("/Vereinsverwaltung/verein/mitglieder/save", data);
		}
	};
});

Vereinsverwaltung.config(function($routeProvider) {
	$routeProvider

	// route for the home page
	.when('/', {
		templateUrl : 'subpages/mitglieder.html',
		controller : 'MemberCtrl'
	})

	// route for the about page
	.when('/info', {
		templateUrl : 'subpages/info.html',
		controller : 'MemberCtrl'
	})
	// route for the about page
	.when('/mitglied_edit', {
		templateUrl : 'subpages/mitglied_edit.html',
		controller : 'MemberCtrl'
	})

	// route for the service page
	.when('/mitglied_neu', {
		templateUrl : 'subpages/mitglied_neu.html',
		controller : 'MemberCtrl'
	});
});
