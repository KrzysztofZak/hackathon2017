(function() {
    'use strict';

    angular
        .module('spPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('noname', {
            parent: 'entity',
            url: '/noname?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Nonames'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noname/nonames.html',
                    controller: 'NonameController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('noname-detail', {
            parent: 'entity',
            url: '/noname/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Noname'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noname/noname-detail.html',
                    controller: 'NonameDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Noname', function($stateParams, Noname) {
                    return Noname.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'noname',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('noname-detail.edit', {
            parent: 'noname-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noname/noname-dialog.html',
                    controller: 'NonameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noname', function(Noname) {
                            return Noname.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noname.new', {
            parent: 'noname',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noname/noname-dialog.html',
                    controller: 'NonameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                test: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('noname', null, { reload: 'noname' });
                }, function() {
                    $state.go('noname');
                });
            }]
        })
        .state('noname.edit', {
            parent: 'noname',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noname/noname-dialog.html',
                    controller: 'NonameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noname', function(Noname) {
                            return Noname.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noname', null, { reload: 'noname' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noname.delete', {
            parent: 'noname',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noname/noname-delete-dialog.html',
                    controller: 'NonameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Noname', function(Noname) {
                            return Noname.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noname', null, { reload: 'noname' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
