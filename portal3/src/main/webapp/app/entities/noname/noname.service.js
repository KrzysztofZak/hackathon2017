(function() {
    'use strict';
    angular
        .module('spPortalApp')
        .factory('Noname', Noname);

    Noname.$inject = ['$resource'];

    function Noname ($resource) {
        var resourceUrl =  'api/nonames/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
