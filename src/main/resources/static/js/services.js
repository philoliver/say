'use strict';

/* Services */

angular.module('myApp.services', ['ngResource'])
.factory('PostService', function ($resource) {
    "use strict";

    return $resource('/rest/posts/:postID', {}, {});
}).factory('PassphraseService', function ($http) {
    "use strict";

    return({
        check: function(phrase){
        	var request = $http({method: "get", url: "rest/passphrase/check",
                params: {
                    passphrase: phrase
                }
            });

            return( request.then() );
        }
    });

}).factory('SubscriptionService', function ($http) {
    "use strict";

    return({
    	subscribe: function(emailAddress){
        	var request = $http({method: "get", url: "rest/subscription/subscribe",
                params: {
                    email: emailAddress
                }
            });

            return( request.then() );
        }
    });

});
