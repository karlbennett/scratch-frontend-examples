<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:layout title="Simpe Webapp (Sign In)" heading="Sign In">
    <p>
        Sign into your account.
    </p>

    <form class="form" method="post">
        <div class="input">
            <label for="username">Username</label><input id="username" name="username" type="text">
        </div>
        <div class="input">
            <label for="password">Password</label><input id="password" name="password" type="password">
        </div>
        <div class="input">
            <input type="submit" value="Sign In">
        </div>
    </form>
</t:layout>