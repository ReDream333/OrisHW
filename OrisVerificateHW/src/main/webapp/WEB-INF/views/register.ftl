<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container" style="max-width: 420px; margin-top: 40px">
    <h2 class="mb-3">Создать аккаунт</h2>

    <#-- Блок ошибок (если передаёте список errors в модель) -->
    <#if errors?? && errors?has_content>
        <div class="alert alert-danger">
            <ul>
                <#list errors as err>
                    <li>${err}</li>
                </#list>
            </ul>
        </div>
    </#if>

    <!-- action и ссылки прописаны «как есть» -->
    <form action="/register" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Имя пользователя</label>
            <input type="text" id="username" name="username" class="form-control"
                   value="${user.username!}" required autofocus>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">E-mail</label>
            <input type="email" id="email" name="email" class="form-control"
                   value="${user.email!}" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Пароль</label>
            <input type="password" id="password" name="password" class="form-control"
                   required minlength="6">
        </div>

        <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
    </form>

    <hr>
    <p class="text-center">
        Уже есть аккаунт?
        <a href="/login">Войти</a>
    </p>
</div>
</body>
</html>
