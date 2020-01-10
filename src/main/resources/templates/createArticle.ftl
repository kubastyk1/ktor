<#import "template.ftl" as layout />
<@layout.mainLayout title="New Article">
    <form action="/article" method="post">
        <div class="form-group">
            <label for="name">Title</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="Enter Title" value="${(article.title)!}">
        </div>
        <div class="form-group">
            <label for="category">Category</label>
            <input type="text" class="form-control" id="category" name="category" placeholder="Enter Category" value="${(article.category)!}">
        </div>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="id" name="id" value="${(article.id)!}">
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@layout.mainLayout>