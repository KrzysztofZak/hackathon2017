(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('NonameDialogController', NonameDialogController);

    NonameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Noname'];

    function NonameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Noname) {
        var vm = this;

        vm.noname = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.noname.id !== null) {
                Noname.update(vm.noname, onSaveSuccess, onSaveError);
            } else {
                Noname.save(vm.noname, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('spPortalApp:nonameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
