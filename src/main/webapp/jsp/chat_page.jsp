<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div><h1>CHAT</h1></div>
    <script>
        setInterval(async () => {
            let url = 'chats?command=getmsg';
            let response = await fetch(url);
            let commits = await response.json();
            console.log(commits.name);
            let newDiv = document.createElement('div');
            if(commits.status === 'ok') {
                newDiv.innerHTML = '<h1>Привет! ' + commits.name + '</h1>';
                document.body.append(newDiv);
            }
        }, 500)
    </script>
</body>
</html>
