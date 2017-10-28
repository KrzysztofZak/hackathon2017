(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('NonameDetailController', NonameDetailController);

    NonameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Noname'];

    function NonameDetailController($scope, $rootScope, $stateParams, previousState, entity, Noname) {
        var vm = this;

        vm.noname = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('spPortalApp:nonameUpdate', function(event, result) {
            vm.noname = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
