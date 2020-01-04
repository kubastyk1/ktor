<#import "template.ftl" as layout />
<@layout.mainLayout>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Login</th>
                <th scope="col">Email</th>
                <th scope="col">Password</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.password}</td>
                <td>
                    <a href="/user?action=edit&id=${user.id}" class="btn btn-secondary float-right mr-2" role="button">Edit</a>
                    <a href="/delete?id=${user.id}" class="btn btn-danger float-right mr-2" role="button">Delete</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
    <div class="container">
        <div class="row">
            <a href="/user?action=new" class="btn btn-secondary float-right" role="button">New User</a>
        </div>
    </div>
</@layout.mainLayout>