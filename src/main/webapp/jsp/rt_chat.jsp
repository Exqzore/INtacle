<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Подключение Bootstrap чтобы все выглядело красиво -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <title>Чат программа</title>
    <!-- Свои стили -->
    <style>
        body {
            background: #fcfcfc;
        }
    </style>
</head>

<body>
<!-- Создание меню на сайте (без функций) -->
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <h5 class="my-0 mr-md-auto">Ntacle</h5>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="#">Главная</a>
        <a class="p-2 text-dark" href="#">Про нас</a>
        <a class="p-2 text-dark" href="#">Поддержка</a>
    </nav>
    <a class="btn btn-outline-primary" href="#">Регистрация</a>
</div>
<!-- Основная часть страницы -->
<div class="container">
    <div class="py-5 text-center">
        <h2>Чат программа</h2>
        <p class="lead">Укажите ваше имя и начинайте переписку</p>
    </div>
    <div class="row">
        <div class="col-6">
            <!-- Форма для получения сообщений и имени -->
            <h3>Форма сообщений</h3>
            <form id="messForm">
                <label for="name">Имя</label>
                <input type="text" name="name" id="name" placeholder="Введите имя" class="form-control">
                <br>
                <label for="message">Сообщение</label>
                <textarea name="message" id="message" class="form-control" placeholder="Введите сообщение"></textarea>
                <br>
                <input type="submit" value="Отправить" class="btn btn-danger">
            </form>
        </div>
        <div class="col-6">
            <h3>Сообщения</h3>
            <!-- Вывод всех сообщений будет здесь -->
            <div id="all_mess"></div>
        </div>
    </div>
</div>
<!-- Подключаем jQuery, а также Socket.io -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/socket.io/socket.io.js"></script>
<script>
    // У каждого пользователя будет случайный стиль для блока с сообщенями,
    // поэтому в этом кусочке кода мы получаем случайные числа
    let random = Math.floor(Math.random() * 5) + 1
    // Устаналиваем класс в переменную в зависимости от случайного числа
    // Эти классы взяты из Bootstrap стилей
    let alertClass
    switch (random) {
        case 1:
            alertClass = 'secondary'
            break
        case 2:
            alertClass = 'danger'
            break;
        case 3:
            alertClass = 'success'
            break;
        case 4:
            alertClass = 'warning'
            break;
        case 5:
            alertClass = 'info'
            break
        case 6:
            alertClass = 'light'
            break
    }

    // Функция для работы с данными на сайте
    $(function () {
        // Включаем socket.io и отслеживаем все подключения
        let socket = new WebSocket("ws://localhost:8080/intacle/greeting")
        socket.onmessage = function (event) {
            console.log(event.data)
            let json = JSON.parse(event.data)
            $all_messages.append("<div class='alert alert-" + json.className + "'><b>" + json.name + "</b>: " + json.mess + "</div>")
            //$all_messages.append("<div><b>" + event.data + "</b></div>")
        }
        // Делаем переменные на:
        let $form = $("#messForm") // Форму сообщений
        let $name = $("#name") // Поле с именем
        let $textarea = $("#message") // Текстовое поле
        let $all_messages = $("#all_mess") // Блок с сообщениями

        // Отслеживаем нажатие на кнопку в форме сообщений
        $form.submit(function (event) {
            // Предотвращаем классическое поведение формы
            event.preventDefault();
            // В сокет отсылаем новое событие 'send mess',
            // в событие передаем различные параметры и данные
            socket.send('{"mess":"' + $textarea.val() + '","name":"' + $name.val() + '","className":"' +  alertClass + '"}')
            // Очищаем поле с сообщением
            $textarea.val('')
        })

        // Здесь отслеживаем событие 'add mess',
        // которое должно приходить из сокета в случае добавления нового сообщения

    })
</script>
</body>

</html>
