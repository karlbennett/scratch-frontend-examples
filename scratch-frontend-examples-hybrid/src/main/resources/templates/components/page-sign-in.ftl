<link rel="import" href="/webjars/polymer/1.8.0/polymer.html">

<dom-module id="page-sign-in">
    <template>
        <div class="signin">
        <#if username??>
            <a href="/profile">${username}</a>
            <a href="/signOut">Sign Out</a>
        <#else>
            <a href="/registration">Register</a>
            <a href="/signIn">Sign In</a>
        </#if>
        </div>
    </template>

    <script>
        Polymer({
            is: "page-sign-in"
        });
    </script>
</dom-module>