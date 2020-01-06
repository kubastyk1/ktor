<#import "template.ftl" as layout />
<@layout.mainLayout>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list categories as cat>
            <tr>
                <td>${cat.id}</td>
                <td>${cat.name}</td>
                <td>
                    <a href="/category?id=${cat.id}" class="btn btn-primary float-right mr-2" role="button">SELECT</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</@layout.mainLayout>