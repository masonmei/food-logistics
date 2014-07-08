/*

 This datetimepicker is a simple angular wrapper of bootstrap datetimepicker(https://github.com/smalot/bootstrap-datetimepicker), which is the best I could found so far.
 It depends on the following stuffs:
 1. bootstrap.css 2 or 3
 2. bootstrap-datetimepicker.css
 3. jquery.js
 4. bootstrap.js
 5. bootstrap-datetimepicker.js
 6. angular.js

 Sample:
 <datetimepicker ng-model='date' today-btn='true' minute-step='30' ></datetimepicker>

 Ron Liu
 5/2/2014

 */

angular.module('angularjs-bootstrap-datetimepicker', [])
    .directive('datetimepicker', function () {
        function _byDefault(value, defaultValue) {
            return _isSet(value) ? value : defaultValue;
            function _isSet(value) {
                return !(value === null || value === undefined || value === NaN || value === '');
            }
        }

        return {
            restrict: 'E',
            scope: {
                ngModel: '=',
                format: '@',
                todayBtn: '@',
                weekStart: '@',
                minuteStep: '@'
            },
            template:
                '<div class="input-group date form-datetime">' +
                '   <input class="form-control" size="16" type="text" value="" readonly>' +
                '   <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
                '	<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>' +
                '</div>',

            link: function (scope, element, attrs) {
                var $element = $(element.children()[0]);

                scope.$watch('ngModel', function (newValue, oldValue) {
                    $element.datetimepicker('update', newValue);
                });

                $element.datetimepicker({
//                    language:  'zh-CN',
                    format: _byDefault(scope.format, 'yyyy-mm-dd'),
                    weekStart: _byDefault(scope.weekStart, 1),
                    todayBtn: _byDefault(scope.todayBtn, 'true') === 'true',
                    autoclose: 1,
                    minView: 2,
                    showMeridian: 1
                }).on('changeDate', function (ev) {
                    scope.$apply(function() {
                        scope.ngModel = ev.date;
                    });
                });
            }
        };
    });