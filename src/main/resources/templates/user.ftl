<#import "template.ftl" as layout />
<@layout.mainLayout title="New User">
    <form action="/user" method="post">
        <div class="form-group">
            <label for="name">Login</label>
            <input type="text" class="form-control" id="login" name="login" placeholder="Enter Login" value="${(user.login)!}">
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Enter Email" value="${(user.email)!}">
        </div>
        <div class="form-group">
            <label for="city">Password</label>
            <input type="text" class="form-control" id="password" name="password" placeholder="Enter Password" value="${(user.password)!}">
        </div>
        <!--<input type="hidden" id="action" name="action" value="${action}">-->
        <input type="hidden" id="id" name="id" value="${(user.id)!}">
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@layout.mainLayout>