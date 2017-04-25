<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="heading" required="true" type="java.lang.String" %>
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
        <c:choose>
            <c:when test="${not empty username}">
                <a href="/profile">${username}</a>
                <a href="/signOut">Sign Out</a>
            </c:when>
            <c:otherwise>
                <a href="/registration">Register</a>
                <a href="/signIn">Sign In</a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="menu">
        <a href="/">Home</a>
    </div>
</div>

<div class="content-container">
    <h1 class="main-heading">${heading}</h1>

    <jsp:doBody />
</div>
</body>
</html>