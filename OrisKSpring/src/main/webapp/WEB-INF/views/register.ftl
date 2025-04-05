<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css" />
</head>
<body>
<h1>Регистрация</h1>
<form action="/register" method="post">
    <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </#if>
    <div>
        <label for="username">Имя пользователя:</label>
        <input type="text" id="username" name="username" required />
    </div>
    <div>
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required />
    </div>
    <div>
        <button type="submit">Зарегистрироваться</button>
    </div>
</form>
<p>Уже зарегистрированы? <a href="/login">Войти</a></p>
</body>
</html>
