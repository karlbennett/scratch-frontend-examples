<#macro scaffold title heading>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <link rel="stylesheet" type="text/css" href="/css/form.css">

    <title>${title}</title>
</head>
<body>
<div class="header-container">
    <div class="signin">
        <#if username??>
            <a href="/profile">${username}</a>
            <a href="/signOut">Sign Out</a>
        <#else>
            <a href="/registration">Register</a>
            <a href="/signIn">Sign In</a>
        </#if>
    </div>
    <div class="menu">
        <a href="/">Home</a>
    </div>
</div>

<div class="content-container">
    <h1 class="main-heading">${heading}</h1>

    <#nested>
</div>
</body>
</html>
</#macro>