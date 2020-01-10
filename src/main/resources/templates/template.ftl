<#macro mainLayout title="Welcome to User Database">
<!doctype html>
<html lang="en">
    <head>
        <title>${title}</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
    <body>
    <div class="container">
        <div class="row m-1">
            <h3>Sport articles</h3>
            <a href="/user?action=edit" class="btn btn-primary float-right" style="margin-left: 650px" role="button">Settings</a>
            <a href="/logout" class="btn btn-primary float-right" style="margin-left: 50px" role="button">Logout</a>
        </div>
        <div class="row m-1">
            <#nested/>
        </div>
    </div>
    </body>
</html>
</#macro>