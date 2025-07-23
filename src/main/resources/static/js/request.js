var request = function () {
    var hello = function (data) {
        lightyear.notify(data, 'success', 100);
    };
    return {
        hello: function (data) {
            hello(data);
        }
    }
}();