<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>
<html>
<head>
    <title>Excursion details</title>
</head>
<body>
<link href="rating/styles/styles.css" rel="stylesheet" type="text/css"/>
<link href="rating/styles/jquery.rating.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="rating/js/jquery.rating-2.0.js"></script>
<script type="text/javascript">
    $(function () {

        $('#rating_1').rating({
            fx: 'full',
            image: 'rating/images/stars.png',
            loader: 'rating/images/ajax-loader.gif',
            url: 'rating',
            titles: ['votes', 'votes', 'votes'],
            localizedYourVote: 'Your votes',
            <c:if test="${((sessionUser==null)||(excursion==null))||(excursion.guideStars!=0)}">
            readOnly: true,
            </c:if>
            callback: function (responce) {

                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val)
            },
            readOnlyFunction: function () {
                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val<c:if test="${((excursion.guideStars!=0)&&(sessionUser!=null))&&(excursion!=null)}"> + '. You have alreade voted. Your vote is ${excursion.guideStars} '</c:if>)
            }
        });

        $('#rating_2').rating({
            fx: 'full',//'half',
            image: 'rating/images/stars.png',
            loader: 'rating/images/ajax-loader.gif',
            url: 'rating',
            titles: ['votes', 'votes', 'votes'],
            localizedYourVote: 'Your votes',
            <c:if test="${((sessionUser==null)||(excursion==null))||(excursion.tripStars!=0)}">
            readOnly: true,
            </c:if>
            callback: function (responce) {
                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val)
            },
            readOnlyFunction: function () {
                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val<c:if test="${((excursion.tripStars!=0)&&(sessionUser!=null))&&(excursion!=null)}"> + '. You have alreade voted. Your vote is ${excursion.tripStars}'</c:if>)
            }
        });

        $('#rating_3').rating({
            fx: 'full',//'float',
            image: 'rating/images/stars.png',
            loader: 'rating/images/ajax-loader.gif',
            minimal: 0.6,
            url: 'rating',
            titles: ['votes', 'votes', 'votes'],
            localizedYourVote: 'Your votes',
            <c:if test="${((sessionUser==null)||(excursion==null))||(excursion.planStars!=0)}">
            readOnly: true,
            </c:if>
            callback: function (responce) {
                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val)
            },
            readOnlyFunction: function () {
                //this.vote_success.fadeOut(2000);
                this.vote_success.html('Total votes: ' + this._data.val<c:if test="${((excursion.planStars!=0)&&(sessionUser!=null))&&(excursion!=null)}"> + '. You have alreade voted. Your vote is ${excursion.planStars}'</c:if>)
            }
        });
    })
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
<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>
    function initialize() {
        var mapProp = {
            center: new google.maps.LatLng(55.796215187976436, 49.10868287086487),
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.HYBRID
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"), mapProp);
        //var bounds = new google.maps.LatLngBounds();
/*
        google.maps.event.addListener(map, 'click', function (event) {
            placeMarker(event.latLng);
        });
 */
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
            content: 'Latitude: ${point.latitude}' +
                    '<br>Longitude: ${point.longitude}'
        });

        var geoString = ${jsonPoints};
        /*
        {
                    "type": "FeatureCollection",
                    "features": [
                        {   "type": "Feature",

                            "geometry": {
                                "coordinates": [
                                    -94.149,
                                    36.33
                                ],
                                "type": "Point"
                            }
                        }
                    ]
                } ;
*/

        var myStyle = {
            strokeColor: "#000000",
            strokeOpacity: 1,
            strokeWeight: 1,
            fillColor: "#AAAAAA",
            fillOpacity: 0.5
        };

        infowindow.open(map, marker);
        var geojson = geoString;
        //map.data.addGeoJson(geoString);
        marker.setMap(map);
        var bounds = new google.maps.LatLngBounds();
        bounds.extend(map.center)
        bounds.extend(marker.position);

        //var geojson = JSON.parse(geoString);
        map.data.addGeoJson(geojson);
        map.data.forEach(function (feature) {
            processPoints(feature.getGeometry(), bounds.extend, bounds);
        });
        map.fitBounds(bounds);

        /**
         * Process each point in a Geometry, regardless of how deep the points may lie.
         * @param {google.maps.Data.Geometry} geometry The structure to process
         * @param {function(google.maps.LatLng)} callback A function to call on each
         *     LatLng point encountered (e.g. Array.push)
         * @param {Object} thisArg The value of 'this' as provided to 'callback' (e.g.
         *     myArray)
         */
        function processPoints(geometry, callback, thisArg) {
            if (geometry instanceof google.maps.LatLng) {
                callback.call(thisArg, geometry);
            } else if (geometry instanceof google.maps.Data.Point) {
                callback.call(thisArg, geometry.get());
            } else {
                geometry.getArray().forEach(function (g) {
                    processPoints(g, callback, thisArg);
                });
            }
        }

        //features.setMap(map);

        map.fitBounds(bounds);
/*
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
            infowindow.open(map, marker);
        }
        */

        map.data.addListener('click', function (event) {
            infowindow.setContent(event.feature.getProperty('name'));
            infowindow.setPosition(event.latLng);
            infowindow.setOptions({pixelOffset: new google.maps.Size(0,-34)});
            infowindow.open(map);
        });
        //map.fitBounds(bounds);
    }
    google.maps.event.addDomListener(window, 'load', initialize);
</script>
<%@ page import = "java.util.ResourceBundle" %> <% ResourceBundle resource = ResourceBundle.getBundle("facebook4j");String appid=resource.getString("oauth.appId");%>
<script>
    window.fbAsyncInit = function() {
        FB.init({
            appId      : <%=appid%>,
            xfbml      : true,
            version    : 'v2.5'
        });
    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>


<div class="col-9 col-sm-9 col-lg-9">
    <div><h2>${excursionDetails.origin.shortExplanation}</h2>
        <blockquote>
            <p>
                ${excursionDetails.origin.explanation}
            </p>
            <footer>${excursionDetails.origin.author.firstname} ${excursionDetails.origin.author.lastname} <fmt:message
                    key="excursiondetail.label.tatarstantourism"/></footer>
        </blockquote>
        <div>

        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="map-canvas" id="map-canvas">
            </div>
        </div>
    </div>

    <div>

        <display:table name="points" id="row" pagesize="10"
                       export="true" sort="list" requestURI="excursionDetails"
                       class="table table-bordered table-striped">

            <display:setProperty name="paging.banner.no_items_found">
                <div class="pagination">No {0} found.</div>
            </display:setProperty>
            <display:setProperty name="paging.banner.one_item_found">
                <div class="pagination">One {0} found.</div>
            </display:setProperty>
            <display:setProperty name="paging.banner.all_items_found">
                <div class="pagination">{0} {1} found, displaying all {2}.</div>
            </display:setProperty>
            <display:setProperty name="paging.banner.some_items_found">
                <div class="pagination">{0} {1} found, displaying {2} to {3}.</div>
            </display:setProperty>
            <display:setProperty name="paging.banner.onepage">
                <div class="pagination">{0}</div>
            </display:setProperty>

            <display:column property="name" titleKey="excursiondetail.label.name"
                            sortable="true" headerClass="sortable"/>
            <display:column property="latitude" titleKey="excursiondetail.label.latitude"
                            sortable="true" headerClass="sortable"/>
            <display:column property="longitude" titleKey="excursiondetail.label.longitude"
                            sortable="true" headerClass="sortable"/>
            <display:column titleKey="trips.label.action"
                            sortable="true" headerClass="sortable">
                <a href="point?id=${row.id}" class="btn btn-success"><fmt:message
                        key="excursiondetail.label.details"/></a>
            </display:column>

        </display:table>
    </div>
</div>


<div
        class="fb-like col-3 col-sm-3 col-lg-3"
        data-share="true"
        data-width="450"
        data-show-faces="true">
</div>


<div class="col-3 col-sm-3 col-lg-3">
    <h2><fmt:message
            key="excursiondetail.label.author"/></h2>
    ${excursionDetails.origin.author.firstname}
    ${excursionDetails.origin.author.lastname}
</div>

<div class="col-3 col-sm-3 col-lg-3">
    <h2><fmt:message
            key="excursiondetail.label.guide"/></h2>
    ${excursionDetails.leadingGuide.firstname}
    ${excursionDetails.leadingGuide.lastname}
</div>

<div class="col-3 col-sm-3 col-lg-3">
    <h2><fmt:message
            key="excursiondetail.label.feedback"/></h2>
    ${excursion.planFeedback}
    ${excursion.planStars}
    <div class="border-wrap">
        <h2><fmt:message
                key="excursiondetail.label.planfeedback"/></h2>

        <div id="rating_3">
            <input type="hidden" name="val" value="${excursionDetails.origin.vote}"/>
            <input type="hidden" name="votes" value="${excursionDetails.origin.voteCount}"/>
            <input type="hidden" name="vote-id" value="${excursion.databaseId}"/>
            <input type="hidden" name="cat_id" value="3"/>
        </div>
    </div>

    ${excursion.tripFeedback}
    ${excursion.tripStars}
    <div class="border-wrap">
        <h2><fmt:message
                key="excursiondetail.label.tripfeedback"/></h2>

        <div id="rating_2">
            <input type="hidden" name="val" value="${excursionDetails.vote}"/>
            <input type="hidden" name="votes" value="${excursionDetails.voteCount}"/>
            <input type="hidden" name="vote-id" value="${excursion.databaseId}"/>
            <input type="hidden" name="cat_id" value="2"/>
        </div>
    </div>


    ${excursion.guideFeedback}
    <div class="border-wrap">
        <h2><fmt:message
                key="excursiondetail.label.guidefeedback"/></h2>

        <div id="rating_1">
            <input type="hidden" name="val" value="${excursionDetails.leadingGuide.vote}"/>
            <input type="hidden" name="votes" value="${excursionDetails.leadingGuide.voteCount}"/>
            <input type="hidden" name="vote-id" value="${excursion.databaseId}"/>
            <input type="hidden" name="cat_id" value="1"/>
        </div>
    </div>


</div>


</body>
</html>
