<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.UsersBundle" />

<html>
<head>
    <title>Register page</title>
</head>
<body>
<div class="container">
    <form action="register" method=post id="register-form" class="form-signin"><!--novalidate="novalidate"-->
        <h2 class="form-signin-heading"><fmt:message key="register.label.greeting" /></h2>

        <div class="form-group">
            <label for="email" class="sr-only"><fmt:message key="register.label.email" /></label>
            <input type="text" name="email" id="email" size="25" class="form-control" placeholder="<fmt:message key="register.label.email" />">
        </div>
        <div class="form-group">
            <label for="username" class="sr-only"><fmt:message key="register.label.username" /></label>
            <input type="text" name="username" id="username" size="25" class="form-control" placeholder="<fmt:message key="register.label.username" />">
        </div>
        <div class="form-group">
            <label for="password" class="sr-only"><fmt:message key="register.label.password" /></label>
            <input type="password" size="15" name="password" id="password" class="form-control"
                   placeholder="<fmt:message key="register.label.password" />">
        </div>
        <div class="form-group">
            <label for="firstname" class="sr-only"><fmt:message key="register.label.firstname" /></label>
            <input type="text" name="firstname" id="firstname" size="25" class="form-control" placeholder="<fmt:message key="register.label.firstname" />">
        </div>
        <div class="form-group">
            <label for="lastname" class="sr-only"><fmt:message key="register.label.lastname" /></label>
            <input type="text" name="lastname" id="lastname" size="25" class="form-control" placeholder="<fmt:message key="register.label.lastname" />">
        </div>

        <input type="submit" value="<fmt:message key="register.label.submit" />" class="btn btn-lg btn-primary btn-block">
        <input type="reset" value="<fmt:message key="register.label.reset" />" class="btn btn-lg btn-primary btn-block">
    </form>
</div>
<script src="webjars/jquery-validation/1.14.0/jquery.validate.min.js"></script>
<script>// jQuery Form Validation code -->
$.validator.setDefaults({
    highlight: function (element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function (element) {
        $(element).closest('.form-group').removeClass('has-error');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function (error, element) {
        if (element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }
});

// When the browser is ready...
$(function () {

    // Setup form validation on the #register-form element
    $("#register-form").validate({
        onkeyup: false,
        // Specify the validation rules
        rules: {
            username: {
                cache: false,
                required: true,
                //minlength: 5,
                remote: {
                    url: "checkusername",
                    type: "post"
                }
            },
            email: {
                required: true,
                email: true

            },
            password: {
                required: true,
                minlength: 5
            },
            agree: "required"
        },

        // Specify the validation error messages
        messages: {
            firstname: "<fmt:message key="register.error.firstname" />",
            lastname: "<fmt:message key="register.error.lastname" />",
            password: {
                required: "<fmt:message key="register.error.password.required" />",
                minlength: "<fmt:message key="register.error.password.minlength" />"
            },
            username: {
                required: "<fmt:message key="register.error.username.required" />",
                minlength: "<fmt:message key="register.error.username.minlength" />",
                remote: "<fmt:message key="register.error.username.remote" />"
            },
            email: "<fmt:message key="register.error.email" />",
            agree: "<fmt:message key="register.error.agree" />"
        },

        submitHandler: function (form) {

            form.submit();

        }
    });
    $("#register-form").change(function () {
        $("#username").removeData("previousValue"); //clear cache when changing group
        $("#register-form").data('validator').element("#username"); //retrigger remote call
        //my validator is stored in .data() on the form
    });


});


</script>
</body>
</html>

