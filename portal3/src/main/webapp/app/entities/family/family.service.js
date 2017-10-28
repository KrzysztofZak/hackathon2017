(function() {
    'use strict';
    angular
        .module('spPortalApp')
        .factory('Family', Family);

    Family.$inject = ['$resource'];

    function Family ($resource) {
        var resourceUrl =  'api/families/:id';

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
