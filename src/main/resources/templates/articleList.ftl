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
            </tr>
            </#list>
        </tbody>
    </table>
    <div class="container">
        <div class="row">
            <a href="/article?action=new" class="btn btn-secondary float-right" role="button">New Article</a>
        </div>
    </div>
</@layout.mainLayout>