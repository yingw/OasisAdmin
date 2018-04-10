/**
 * Created by yingu on 2018/2/20.
 */

function ajax_search_submit() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/role/",
        dataType: 'json',
        success: function (data) {
            // alert(JSON.stringify(data, null, 4));

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $('#role_table').dataTable({
                "data": data,
                "columns": [
                    {"data": "id"},
                    {"data": "name"},
                    {"data": "key"},
                ]
            })
        }
    });
}
