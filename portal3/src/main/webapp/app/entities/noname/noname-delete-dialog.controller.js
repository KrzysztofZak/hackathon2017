(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('NonameDeleteController',NonameDeleteController);

    NonameDeleteController.$inject = ['$uibModalInstance', 'entity', 'Noname'];

    function NonameDeleteController($uibModalInstance, entity, Noname) {
        var vm = this;

        vm.noname = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Noname.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
