<!DOCTYPE html>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <title><decorator:title/></title>

    <!-- Bootstrap core CSS -->
    <link href="webjars/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/jumboron.css" rel="stylesheet">

    <script src="webjars/jquery/1.11.1/jquery.js"></script>
    <script src="webjars/jquery-ui/1.11.3/jquery-ui.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/i18n/jquery-ui-i18n.min.js"></script>
    <script src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="webjars/jquery-ui/1.11.3/jquery-ui.css" rel="stylesheet">
    <link href="webjars/font-awesome/4.4.0/css/font-awesome.css" rel="stylesheet"/>
    <script src="webjars/bootstrap-growl/2.0.1/bootstrap-growl.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>


    <![endif]-->
</head>

<body>

<!--
<nav class="navbar navbar-inverse">

</nav>-->

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div>
        <div class="container-fluid">
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#" class="navbar-brand">Project Tourism</a></li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="menu.sightseer"/>
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="excursions">Excursions list</a></li>
                            <li><a href="#">Sign off to excursion</a></li>
                            <li><a href="#">Excursion points</a></li>
                        </ul>
                    </li>
                    <c:if test="${user.isGuide()}">
                        <li><a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message
                                key="menu.guide"/><span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="excursionTrips"><fmt:message key="menu.guide.trips"/></a></li>
                                <li><a href="excursionPlans"><fmt:message key="menu.guide.plans"/></a></li>
                                <li><a href="#">Create an excursion</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${user.isBusDriver()}">
                        <li><a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message
                                key="menu.busdriver"/><span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">My Excursions trips</a></li>
                                <li><a href="#">Propose possible time</a></li>
                                <li><a href="#">Observe existing time</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${user.isAdmin()}">
                        <li><a href="users"><fmt:message key="menu.admin"/></a></li>
                    </c:if>

                </ul>
            </div>
            <!--</div>-->

            <!--<div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar">1</span>
                    <span class="icon-bar">2</span>
                    <span class="icon-bar">3</span>
                </button>

            </div>-->
            <!--<div id="navbar" class="navbar-collapse collapse">-->
            <c:if test="${user!=null}">
                <script type="application/javascript">
                    var Chat = {};

                    Chat.socket = null;

                    Chat.connect = (function (host) {
                        if ('WebSocket' in window) {
                            Chat.socket = new WebSocket(host);
                        } else if ('MozWebSocket' in window) {
                            Chat.socket = new MozWebSocket(host);
                        } else {
                            //Console.log('Error: WebSocket is not supported by this browser.');
                            return;
                        }

                        Chat.socket.onopen = function () {
                            //Console.log('Info: WebSocket connection opened.');
                            //document.getElementById('chat').onkeydown = function (event) {
                            //    if (event.keyCode == 13) {
                            //        Chat.sendMessage();
                            //    }
                            //};
                        };

                        Chat.socket.onclose = function () {
                            document.getElementById('chat').onkeydown = null;
                            //Console.log('Info: WebSocket closed.');
                        };

                        Chat.socket.onmessage = function (message) {
                            //Console.log(message.data);
                            $(document).ready(function () {
                                $.growl({
                                    message: message.data
                                }, {
                                    position: {
                                        from: "bottom",
                                        align: "left"
                                    },
                                   // ele: 'body', // which element to append to
                                   // type: 'info', // (null, 'info', 'danger', 'success')
                                   // offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
                                   // align: 'right', // ('left', 'right', or 'center')
                                   // width: 250, // (integer, or 'auto')
                                    delay: 100000 // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
                                   // allow_dismiss: true, // If true then will display a cross to close the popup.
                                   // stackup_spacing: 10 // spacing between consecutively stacked growls.
                                    //delay: 10000
                                });
                            });
                            /*
                            $.bootstrapGrowl(, {
                                ele: 'body', // which element to append to
                                type: 'info', // (null, 'info', 'danger', 'success')
                                offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
                                align: 'right', // ('left', 'right', or 'center')
                                width: 250, // (integer, or 'auto')
                                delay: 10000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
                                allow_dismiss: true, // If true then will display a cross to close the popup.
                                stackup_spacing: 10 // spacing between consecutively stacked growls.

                            })
                            ;
                            */
                        };
                    });

                    Chat.initialize = function () {
                        if (window.location.protocol == 'http:') {
                            Chat.connect('ws://' + window.location.host + '/websocket/chat');
                        } else {
                            Chat.connect('wss://' + window.location.host + '/websocket/chat');
                        }
                    };

                    Chat.sendMessage = (function () {
                        var message = document.getElementById('chat').value;
                        if (message != '') {
                            Chat.socket.send(message);
                            document.getElementById('chat').value = '';
                        }
                    });

                    /*var Console = {};

                     Console.log = (function (message) {
                     var console = document.getElementById('console');
                     var p = document.createElement('p');
                     p.style.wordWrap = 'break-word';
                     p.innerHTML = message;
                     console.appendChild(p);
                     while (console.childNodes.length > 25) {
                     console.removeChild(console.firstChild);
                     }
                     console.scrollTop = console.scrollHeight;
                     });
                     */
                    Chat.initialize();


                    document.addEventListener("DOMContentLoaded", function () {
                        // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
                        var noscripts = document.getElementsByClassName("noscript");
                        for (var i = 0; i < noscripts.length; i++) {
                            noscripts[i].parentNode.removeChild(noscripts[i]);
                        }
                    }, false);

                </script>
            </c:if>
            <c:if test="${user==null}">


                <form class="navbar-form navbar-right" action="login" method=post>
                    <div class="form-group">
                        <input type="text" placeholder="Email" class="form-control" name="j_username" size="25">
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="Password" class="form-control" size="15"
                               name="j_password">
                    </div>
                    <input type="hidden" name="url" value="<%= request.getRequestURI() %>">
                    <button type="submit" class="btn btn-success"><fmt:message key="header.label.signin"/> <i class="fa fa-sign-in"></i></button>
                    <a href="signin" class="btn btn-success"><fmt:message key="header.label.facebook"/> <i class="fa fa-facebook"></i></a>
                    <a href="register" class="btn btn-success"><fmt:message key="header.label.register"/> <i class="fa fa-sign-up"></i></a>
                </form>
            </c:if>

            <!--/.navbar-collapse -->
            <form id="languages" class="navbar-form navbar-right">
                <c:forEach var="par" items="${paramValues}">
                    <c:if test="${!fn:startsWith(par.key, 'language')}">
                        <input type="hidden" name="${par.key}" value="${par.value[0]}">
                    </c:if>
                </c:forEach>
                <div class="form-group">
                    <select class="form-control" id="language" name="language" onchange="submit()" style="font-family: FontAwesome">
                        <option value="en" ${language == 'en' ? 'selected' : ''}>English &#xf1ab;</option>
                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский &#xf1ab;</option>
                    </select>
                </div>

                <c:if test="${user!=null}">

                    <div class="form-group">
                        <a href="logout" class="btn btn-success"><fmt:message key="header.label.logout"/> <i class="fa fa-sign-out"></i></a>
                        <a class="btn btn-success" > ${user.firstname} ${user.lastname} <i class="fa fa-cog"></i></a>
                    </div>

                </c:if>
            </form>
        </div>
    </div>
</nav>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <decorator:body/>


    </div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-4">
            <h2>О проекте</h2>

            <p>Проект "Туризм в Татарстане" создан для популяризации внутреннего туризма в республике </p>

            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Авторы</h2>

            <p>Авторами предлагаемых экскурсий являются авторизованные экскурсоводы Гильдии Экскурсоводов Татарстана</p>

            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Посетителям</h2>

            <p>Выбирайте понравившуюся вам экскурсию, добавляйтесь в списки планируемых экскурсий! Путешествуйте с
                нами</p>

            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>

    <hr>

    <footer>
        <p>&copy; Project Tourism 2015</p>
        <script>
            $(function () {
                //setting up Datapicker internationalization + defaults
                $.datepicker.setDefaults(
                        $.extend($.datepicker.regional["${language}"]));
                $(".datepicker").datepicker({
                    minDate: null,
                    maxDate: null, dateFormat: 'yy-mm-dd' }).val();

            });


            $(document).ready(function () {
                $("form").submit(function () {
                    var btn = $(this).find("input[type=submit]:focus");
                    $(btn).attr("disabled", "true");
                });
            });
            // disabling form's Submit buttons after the submit
        </script>
    </footer>
</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>

