<#import "scaffold.ftl" as m>
<@m.scaffold title="Simple Webapp (Registration)" heading="Registration">
<p>
    Register a new account with your choice of username and password.
</p>

<form class="form" method="post">
    <div class="input">
        <label for="username">Username</label><input id="username" name="username" type="text">
    </div>
    <div class="input">
        <label for="password">Password</label><input id="password" name="password" type="password">
    </div>
    <div class="input">
        <input type="submit" value="Register">
    </div>
</form>
</@m.scaffold>