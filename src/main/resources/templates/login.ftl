<#import "template.ftl" as layout />
<@layout.mainLayout>
    <div class="row">
        <div class="col-xs-2 col-xs-offset-4">
            <form action="/" method="post">
                <div class="form-group">
                    <label for="name">Login</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Enter Login" value="${(user.login)!}">
                </div>
                <div class="form-group">
                    <label for="city">Password</label>
                    <input type="text" class="form-control" id="password" name="password" placeholder="Enter Password" value="${(user.password)!}">
                </div>
                <input type="hidden" id="id" name="id" value="${(user.id)!}">
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
        <div class="col-xs-2">
            <a href="/user?action=new" class="btn btn-primary float-right" role="button">Create new account</a>
        </div>
    </div>
</@layout.mainLayout>