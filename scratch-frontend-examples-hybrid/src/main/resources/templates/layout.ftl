<#macro layout title heading>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="/webjars/webcomponentsjs/0.7.24/webcomponents-lite.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/all.css">
    <link rel="import" href="/components/page-sign-in">

    <title>${title}</title>
</head>
<body>
<div class="header-container">
    <page-sign-in></page-sign-in>
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