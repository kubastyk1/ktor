<#import "template.ftl" as layout />
<@layout.mainLayout>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Title</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list articles as article>
            <tr>
                <td>${article.id}</td>
                <td>${article.title}</td>
                <td>
                    <a href="/article?action=edit&id=${article.id}" class="btn btn-secondary float-right mr-2" role="button">Edit</a>
                    <a href="/articleDelete?id=${article.id}" class="btn btn-danger float-right mr-2" role="button">Delete</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</@layout.mainLayout>