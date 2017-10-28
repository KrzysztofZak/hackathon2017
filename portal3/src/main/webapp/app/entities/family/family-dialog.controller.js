(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .controller('FamilyDialogController', FamilyDialogController);

    FamilyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Family'];

    function FamilyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Family) {
        var vm = this;

        vm.family = entity;
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
            if (vm.family.id !== null) {
                Family.update(vm.family, onSaveSuccess, onSaveError);
            } else {
                Family.save(vm.family, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('spPortalApp:familyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
