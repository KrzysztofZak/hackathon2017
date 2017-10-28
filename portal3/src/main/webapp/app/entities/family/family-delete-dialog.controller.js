(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('FamilyDeleteController',FamilyDeleteController);

    FamilyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Family'];

    function FamilyDeleteController($uibModalInstance, entity, Family) {
        var vm = this;

        vm.family = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Family.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
