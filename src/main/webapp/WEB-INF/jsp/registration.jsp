<html>
<head>
    <title>Profile</title>

    <style>
        #signUp {
            display:flex;
            justify-content: center;
            align-items: start;
            position: relative;
        }
    </style>
</head>
<body>

<div id="signUp">
    <form action="signUp" method="post">
        LastName
        <label>
            <input type="text" name="lastName" size="100">
        </label>
        <br>
    
        FirstName
        <label>
            <input type="text" name="firstName" size="100">
        </label>
        <br>
    
        MiddleName
        <label>
            <input type="text" name="middleName" size="100">
        </label>
        <br>
    
        PhoneNumber
        <label>
            <input type="text" name="phoneNumber" size="20">
        </label>
        <br>
    
        Email
        <label>
            <input type="text" name="email" size="100">
        </label>
        <br>
    
        Password
        <label>
            <input type="text" name="password" size="100">
        </label>
        <br>
    
        <input type="submit" value="signUp">
    </form>
</div>

</body>
</html>
