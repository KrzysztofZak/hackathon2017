(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('FamilyDetailController', FamilyDetailController);

    FamilyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Family'];

    function FamilyDetailController($scope, $rootScope, $stateParams, previousState, entity, Family) {
        var vm = this;

        vm.family = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('spPortalApp:familyUpdate', function(event, result) {
            vm.family = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
