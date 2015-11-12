<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>
<!DOCTYPE html>
<html>
<head>
</head>

<body>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false">
</script>
<style>
    html, body, .container-fluid, .row, .map {
        height: 100%
    }

    #map-canvas {
        width: 100%;
        height: 500px;
    }

    textarea {
        min-height: 110px;
    }

    .noresize {
        resize: none;
    }

    .vresize {
        resize: vertical;
    }

    .hresize {
        resize: horizontal;
    }
</style>
<script>
    function initialize() {
        var mapProp = {
            center: new google.maps.LatLng(55.796215187976436, 49.10868287086487),
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.HYBRID
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"), mapProp);
        //var bounds = new google.maps.LatLngBounds();

        google.maps.event.addListener(map, 'click', function (event) {
            placeMarker(event.latLng);
        });
        var marker;

        marker = new google.maps.Marker({
            <c:if test="${point!=null}">
            position: new google.maps.LatLng(${point.latitude}, ${point.longitude})
            </c:if>
            <c:if test="${point==null}">
            position: new google.maps.LatLng(55.796215187976436, 49.10868287086487)
            </c:if>

            //animation:google.maps.Animation.BOUNCE
        });

        var infowindow = new google.maps.InfoWindow({
            <c:if test="${point!=null}">
            content: 'Latitude: ${point.latitude}' +
                    '<br>Longitude: ${point.longitude}'
            </c:if>
            <c:if test="${point==null}">
            content: 'Move the balloon to the point'
            </c:if>
        });

        infowindow.open(map, marker);

        marker.setMap(map);

        <c:if test="${point!=null}">
        var bounds = new google.maps.LatLngBounds();
        bounds.extend(map.center)
        bounds.extend(marker.position);
        map.fitBounds(bounds);
        </c:if>
        <c:if test="${point==null}">
        </c:if>


        function placeMarker(location) {
            if (marker) {
                marker.setPosition(location);
            } else {
                marker = new google.maps.Marker({
                    position: location,
                    map: map
                });
            }
            var lat = location.lat();
            var long = location.lng();
            document.getElementById('displayLat').value = location.lat();
            document.getElementById('displayLong').value = location.lng();
            infowindow.setContent('Latitude: ' + location.lat() +
                            '<br>Longitude: ' + location.lng()
            );
            infowindow.open(map, marker);
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.open(map, marker);
            });
        }

        //map.fitBounds(bounds);
    }
    google.maps.event.addDomListener(window, 'load', initialize);
</script>
<div class="container-fluid">
    <form action="point" method="post">
        <c:if test="${point!=null}"><input type="hidden" name="id" value="${point.id}"></c:if>
        <c:if test="${route!=null}"><input type="hidden" name="route" value="${route}"></c:if>
        <div class="row">
            <div class="col-md-6 map">
                <div class="map-canvas" id="map-canvas">
                </div>
            </div>
            <div class="col-md-6">
                <h2>Cooridinates</h2>

                <div class="form-group col-md-6">
                    <label for="displayLat" class="sr-only"><fmt:message key="point.label.latitude"/></label>
                    <input name="latitude" type="text" class="form-control input-lg" size="12" maxlength="12"
                           id="displayLat"
                           placeholder="<fmt:message key="point.label.latitude" />" value="${point.latitude}">
                </div>
                <div class="form-group col-md-6">
                    <label for="displayLong" class="sr-only"><fmt:message key="point.label.longitude"/></label>
                    <input name="longitude" type="text" class="form-control input-lg" size="12" maxlength="12"
                           id="displayLong"
                           placeholder="<fmt:message key="point.label.longitude" />" value="${point.longitude}">
                </div>
            </div>
            <div class="col-md-6">
                <h2>Description</h2>

                <div class="form-group col-md-12">

                    <label for="name" class="sr-only"><fmt:message key="point.label.name"/></label>
                    <input name="name" type="text" class="form-control input-lg" maxlength="50" id="name"
                           placeholder="<fmt:message key="point.label.name" />" value="${point.name}">
                </div>
                <div class="form-group col-md-12">

                    <label for="explanation" class="sr-only"><fmt:message key="point.label.explanation"/></label>
                    <textarea name="description" id="explanation"
                              class="form-control vresize"
                              placeholder="<fmt:message key="point.label.explanation" />">${point.description}</textarea>
                </div>
                <div class="form-group col-md-6">
                    <input type="submit"
                           value="<c:if test="${point==null}"><fmt:message key="point.label.add" /></c:if><c:if test="${point!=null}"><fmt:message key="point.label.update" /></c:if>"
                           class="btn btn-lg btn-primary btn-block">
                </div>
                <div class="form-group col-md-6">
                    <input type="reset" value="<fmt:message key="point.label.reset" />"
                           class="btn btn-lg btn-primary btn-block">
                </div>
            </div>
            <div>
                <div class="form-group col-md-6">
                    <a href="excursionPlanDetails?id=${excursionPlan.databaseId}"
                       class="btn btn-lg btn-primary btn-block"><fmt:message key="point.label.back"/></a>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
