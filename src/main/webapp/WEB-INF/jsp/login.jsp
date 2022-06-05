<html>
<head>
    <title>Profile</title>

    <style>
        #signIn {
            display:flex;
            justify-content: center;
            align-items: start;
            position: relative;
        }
    </style>

</head>
<body>

<div id="signIn">
    <form action=signIn method="post">
        Email
        <label>
            <input type="text" name="login" size="100">
        </label>
        <br>

        Password
        <label>
            <input type="text" name="password" size="100">
        </label>
        <br>

        <input type="submit" value="signIn">

    </form>

    <br>

    <div>
        <form action="signUpView">
            <input type="submit" value="signUp">
        </form>
    </div>
    
</div>

</body>
</html>
