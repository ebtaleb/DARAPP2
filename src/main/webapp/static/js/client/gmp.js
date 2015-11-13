function loadScript() {
    var a = document.createElement("script");
    a.type = "text/javascript";
    a.src = "http://maps.google.com/maps/api/js?libraries=geometry&output=embed&callback=initializeMap";;
    document.head.appendChild(a)
}

if (window.location.pathname == "/app/newevent") {
    window.onload = loadScript;
} else {
    //alert("awright");
}

var map;
var ptHash = new Object();
var editPoints = [];
var polyLineSegmentsArr = new Array();
var uiPolyline;

$(document).ready(function() {
    $("#startRecording").click(startRecordingFromOtherLoc)
});

function startRecordingFromOtherLoc() {
    bRecordPoints = true;
    $("#startRecording").val("Recording...")
}

function initializeMap() {
    map = new google.maps.Map(document.getElementById("map-canvas"), {
        center: new google.maps.LatLng(48.8566667, 2.3509871),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        draggableCursor: "crosshair",
        draggingCursor: "crosshair",
        scaleControl: true,
        disableDoubleClickZoom: true,
        scrollwheel: true,
        tilt: 0,
        zoomControl: true,
        zoomControlOptions: {
            position: google.maps.ControlPosition.RIGHT_TOP,
            style: google.maps.ZoomControlStyle.LARGE
        },
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.SATELLITE, google.maps.MapTypeId.TERRAIN],
            style: google.maps.MapTypeControlStyle.DROPDOWN
        }
    });

    google.maps.event.addListener(map, "move", function() {
        redrawLinesAndMarkers(gLatLngArray)
    });

    google.maps.event.addListener(map, "dblclick", function(g) {
        map.panTo(g.latLng);
        addLeg(g.latLng.lng(), g.latLng.lat(), NOT_LOADING_FROM_QUERY);
        redrawLinesAndMarkers(gLatLngArray);
    });

    google.maps.event.addListener(map, "zoomend", function(h, g) {
        prepMarkerArray();
        redrawLinesAndMarkers(gLatLngArray)
    });
}

function redrawLinesAndMarkers(a) {
    drawPolyLine(a);
    drawMarkers(a)
}

var pointTypeArray = new Array(0);
var xArray = new Array(0);
var yArray = new Array(0);
var gLatLngArray = new Array(0);
var legArray = new Array(0);
var distancesArray = new Array(0);
var mileMarkersToDraw = new Array(0);
var bRecordPoints = false;
var REMOVE = 0;
var ADD = 1;
var EDIT = 2;
var METRIC = "1";
var DISTANCE = "2";
var START = "start";
var STOP = "stop";
var CLICKED_POINT = "0";
var GEOCODED_POINT = "1";
var CLICKED_LEG = "0";
var GEOCODED_LEG = "1";
var LOADING_FROM_QUERY = true;
var NOT_LOADING_FROM_QUERY = false;
var currentWeightUnits = getCurrentUnits();
var showMarkers = true;

function addLeg(a, g, query_flag) {
    if ((uiPolyline != undefined) && (uiPolyline.getEditable())) {
        setupEditablePolyline(uiPolyline)
    }
    if (bRecordPoints) {
        if (shouldUseDirectionsForDrawingLeg(query_flag, xArray.length)) {
            addLegsFromDirectionsObject(a, g)
        } else {
            addSingleLeg(a, g);
            if (!query_flag) {
                pointTypeArray.push(CLICKED_POINT)
            }
        }
        drawMarkers(gLatLngArray)
    }
}

function addLegsFromDirectionsObject(d, l) {
    lastPoint = gLatLngArray[gLatLngArray.length - 1];
    var b = google.maps.TravelMode.WALKING;
    var a = {
        origin: new google.maps.LatLng(lastPoint.lat(), lastPoint.lng()),
        destination: new google.maps.LatLng(l, d),
        travelMode: b
    };
    var h = new google.maps.DirectionsService();
    h.route(a, function(o, m) {
        if (m == google.maps.DirectionsStatus.OK) {
            var p = o.routes[0].overview_path;
            for (var n = 1; n < p.length; n++) {
                addSingleLeg(p[n].lng(), p[n].lat());
                if (n == p.length - 1) {
                    pointTypeArray.push(CLICKED_POINT)
                } else {
                    pointTypeArray.push(GEOCODED_POINT)
                }
            }
            drawPolyLine(gLatLngArray);
            drawMarkers(gLatLngArray)
        }
    })
}

function resetDistanceMarkers() {
    mileMarkersToDraw = [];
    for (var a = 2; a < distancesArray.length; a++) {
        prepMarkersForLeg(a)
    }
}

function insertVertex(a, b, h) {
    xArray.splice(a, 0, b.lng());
    yArray.splice(a, 0, b.lat());
    gLatLngArray.splice(a, 0, b);
    pointTypeArray.splice(a, 0, h);
    distancesArray.splice(a, 0, 0);
    updateDistances(xArray, yArray, EDIT);
    resetDistanceMarkers();
    drawMarkers(gLatLngArray)
}

function editVertex(a, b) {
    xArray[a] = b.lng();
    yArray[a] = b.lat();
    gLatLngArray[a] = b;
    updateDistances(xArray, yArray, EDIT);
    resetDistanceMarkers();
    drawMarkers(gLatLngArray)
}

function addSingleLeg(a, g) {
    xArray.push(a);
    yArray.push(g);
    gLatLngArray.push(new google.maps.LatLng(g, a));
    updateDistances(xArray, yArray, ADD);
    prepMarkersForLeg(distancesArray.length);
}

function getBearing() {
    var g = yArray[yArray.length - 2];
    var d = yArray[yArray.length - 1];
    var b = xArray[xArray.length - 2];
    var a = xArray[xArray.length - 1];
    return (Math.atan2(Math.sin(a - b) * Math.cos(d), Math.cos(g) * Math.sin(d) - Math.sin(g) * Math.cos(d) * Math.cos(a - b))) % (2 * Math.PI)
}
function prepMarkerArray() {
    removeAllMileMarkers();
    mileMarkersToDraw = new Array(0);
    for (var a = 2; a <= distancesArray.length; a++) {
        prepMarkersForLeg(a)
    }
}

function prepMarkersForLeg(a) {
    var g;
    var h = returnDistanceInChosenUnits(distancesArray[a - 1]);
    if (distancesArray.length < 1) {
        g = 0
    } else {
        g = returnDistanceInChosenUnits(distancesArray[a - 2])
    }
    var d = Math.floor(g);
    var j = Math.floor(h);
    if (d < j) {
        for (var b = d + 1; b <= j; b++) {
            calcMarkerLatLng(parseFloat(b) - g, a - 1)
        }
    }
}

function getSlopeOfLeg(d, a) {
    var g = d[a - 1];
    var b = d[a - 2];
    var h;
    if (b.lng() == g.lng()) {
        h = 1e-8
    } else {
        h = b.lng() - g.lng()
    }
    return ((b.lat() - g.lat()) / (h))
}

function getLatLngDistanceOfLeg(a) {
    var d = a - 2;
    var b = a - 1;
    return Math.sqrt(Math.pow(gLatLngArray[d].lng() - gLatLngArray[b].lng(), 2) + Math.pow(gLatLngArray[d].lat() - gLatLngArray[b].lat(), 2))
}

function calcMarkerLatLng(a, q) {
    var r = a / returnDistanceInChosenUnits(legArray[q - 1]);
    var s = getLatLngDistanceOfLeg(q);
    var h = r * s;
    var m = gLatLngArray[q - 2].lng();
    var j = gLatLngArray[q - 2].lat();
    var n = getSlopeOfLeg(gLatLngArray, q);
    var p = h * (1 / (Math.sqrt(1 + Math.pow(n, 2))));
    var b = h * (n / ((Math.sqrt(1 + Math.pow(n, 2)))));
    var o = parseFloat(gLatLngArray[q - 1].lng());
    var g = parseFloat(gLatLngArray[q - 2].lng());
    if (g > o) {
        p = -p;
        b = -b
    }
    var l = parseFloat(m) + parseFloat(p);
    var d = parseFloat(j) + parseFloat(b);
    mileMarkersToDraw.push(new google.maps.LatLng(d, l))
}

function updateDistances(h, g, j) {
    if (j == ADD) {
        fLastLeg = getLastLegDistance(h, g);
        var d = (distancesArray.length == 0) ? 0 : distancesArray[distancesArray.length - 1];
        distancesArray.push(d + fLastLeg);
        legArray.push(fLastLeg)
    } else {
        if (j == REMOVE) {
            distancesArray.pop();
            legArray.pop()
        } else {
            if (j == EDIT) {
                var b = 0;
                for (var a = 0; a < distancesArray.length; a++) {
                    prevLeg = getPreviousLegDistance(h, g, a);
                    b = prevLeg + b;
                    distancesArray[a] = b
                }
            }
        }
    }
    updateDistanceBoxes()
}

function updateDistanceBoxes() {
    $("#mileage").html(parseInt(returnDistanceInChosenUnits(distancesArray[distancesArray.length - 1]) * 10000) / 10000);
}

function returnDistanceInMiles(h, j, l, b) {
    var g = new google.maps.LatLng(h, j);
    var d = new google.maps.LatLng(l, b);
    var a = google.maps.geometry.spherical.computeDistanceBetween(g, d) * 0.000621371192;
    return (a)
}

function getPreviousLegDistance(h, b, a) {
    var g = 0;
    previousIdx = a - 1;
    if (a > 0) {
        var d;
        g = returnDistanceInMiles(b[a], h[a], b[previousIdx], h[previousIdx])
    }
    return g
}

function getLastLegDistance(g, a) {
    var d = 0;
    lastPointIdx = g.length - 1;
    secondToLastPointIdx = g.length - 2;
    if (g.length > 1) {
        var b;
        d = returnDistanceInMiles(a[lastPointIdx], g[lastPointIdx], a[secondToLastPointIdx], g[secondToLastPointIdx])
    }
    return d
}

function popInternalArraysForLeg() {
    xArray.pop();
    yArray.pop();
    gLatLngArray.pop();
    updateDistances(xArray, yArray, REMOVE);
    prepMarkerArray();
    pointTypeArray.pop();
}

function removeLastLeg() {
    if ((uiPolyline != undefined) && (uiPolyline.getEditable())) {
        setupEditablePolyline(uiPolyline)
    }
    if (xArray.length > 0) {
        if (gLatLngArray.length == 1) {
            popInternalArraysForLeg()
        } else {
            if (pointTypeArray[gLatLngArray.length - 2] == CLICKED_POINT) {
                popInternalArraysForLeg()
            } else {
                while (pointTypeArray[gLatLngArray.length - 2] == GEOCODED_POINT) {
                    popInternalArraysForLeg()
                }
                popInternalArraysForLeg()
            }
        }
        drawPolyLine(gLatLngArray);
        drawMarkers(gLatLngArray);
    } else {
        alert("Il n'y a plus de points à enlever.");
        setRecordingStateAndButtons()
    }
}

function createPointListForRoute(a) {
    newData = [];

    for(var i = 0; i < a.length; i++) {
        var obj = new Object();
        obj['lat'] = a[i].lat();
        obj['lon'] = a[i].lng();
        obj['pt'] = pointTypeArray[i];
        newData.push(obj);
    }

    return JSON.stringify(newData);
}

function clearLinkHandler() {
    if (bRecordPoints) {
        if (confirm("Are you sure you want to clear the route you've created?\nClicking OK to will clear all points and stop recording.\nClicking Cancel will continue recording and leave points as they are. \nIf you have been saving this route, further changes will be saved with a different route id.")) {
            distancesArray.splice(1, distancesArray.length - 1);
            legArray.splice(0, legArray.length);
            gLatLngArray.splice(0, gLatLngArray.length);
            xArray.splice(0, xArray.length);
            yArray.splice(0, yArray.length);
            mileMarkersToDraw.splice(0, mileMarkersToDraw.length);
            pointTypeArray.splice(0, pointTypeArray.length);
            clearEditPoints();
            setRecordingStateAndButtons();
        }
    } else {
        alert("No points to clear")
    }
}

function clearMarkers() {
    for (var a in ptHash) {
        if (ptHash.hasOwnProperty(a)) {
            ptHash[a].setMap(null);
            delete ptHash[a]
        }
    }
}

function clearPolylines() {
    for (var a = polyLineSegmentsArr.length - 1; a >= 0; a--) {
        polyLineSegmentsArr[a].setMap(null);
        polyLineSegmentsArr.pop()
    }
}

function clearOverlays() {
    clearPolylines();
    clearMarkers()
}

function setRecordingStateAndButtons() {
    bRecordPoints = false;
    clearOverlays();
    $("#startRecording").val("Start recording");
}

function drawMarkers(g) {
    clearMarkers();
    if (g.length > 0) {
        if (showMarkers) {
            drawStopStartMarker(g[0], START);
            drawStopStartMarker(g[g.length - 1], STOP);
            for (var a = 0; a < mileMarkersToDraw.length; a++) {
                var d = {
                    url: "/",
                    size: new google.maps.Size(20, 34),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(9, 34)
                };
                drawMileMarker(mileMarkersToDraw[a], d, a)
            }
        }
    }
}

function drawStopStartMarker(a, b) {
    ptHash[b] = new google.maps.Marker({
        position: a,
        map: map
    })
}
function removeAllMileMarkers() {
    for (var a = 0; a < mileMarkersToDraw.length; a++) {
        if (ptHash[a] != undefined) {
            ptHash[a].setMap(null);
            delete ptHash[a]
        }
    }
}
function drawMileMarker(h, d, b) {
    var a = false;
    var g = false;
    if (ptHash[b] == undefined) {
        ptHash[b] = new google.maps.Marker({
            position: h,
            icon: d,
            map: map
        })
    }
}

function drawPolyLine(d, b) {
    clearPolylines();
    var a = d.slice(0);
    uiPolyline = new google.maps.Polyline({
        path: a,
        strokeColor: "#0000FF",
        zIndex: 5,
        map: map
    });
    polyLineSegmentsArr.push(uiPolyline);
    google.maps.event.addListener(uiPolyline, "click", function() {
        setupEditablePolyline(this)
    });
    if ((b !== undefined) && (b == true)) {
        setupEditablePolyline(uiPolyline)
    }
    google.maps.event.addListener(uiPolyline.getPath(), "set_at", function(j) {
        editVertex(j, this.getArray()[j]);
        drawPolyLine(d, true);
    });
    google.maps.event.addListener(uiPolyline.getPath(), "insert_at", function(j) {
        var h;
        for (var g = 0; g < editPoints.length; g++) {
            h = editPoints[g];
            if (h.vertex >= j) {
                break
            }
        }
        var l = (h.prevLeg == GEOCODED_LEG) ? GEOCODED_POINT : CLICKED_POINT;
        insertVertex(j, uiPolyline.getPath().getAt(j), l);
        drawPolyLine(d, true)
    })
}

function clearEditPoints() {
    while (editPoints.length > 0) {
        var a = editPoints.pop();
        a.setMap(null)
    }
}

function setupEditablePolyline(d) {
    d.setEditable(!d.getEditable());
    if (!d.getEditable()) {
        clearEditPoints()
    } else {
        var b = -1;
        clearEditPoints();
        for (var a = 0; a < pointTypeArray.length; a++) {
            if (pointTypeArray[a] == CLICKED_POINT) {
                b++;
                editPoints.push(new google.maps.Marker({
                    position: gLatLngArray[a],
                    draggable: true,
                    vertex: a,
                    markerIndex: b,
                    zIndex: 6,
                    icon: {
                        path: google.maps.SymbolPath.CIRCLE,
                        fillOpacity: 1,
                        fillColor: "0000ff",
                        strokeOpacity: 1,
                        strokeColor: "0000ff",
                        strokeWeight: 1,
                        scale: 10
                    }
                }));
                if (a > 0) {
                    if (pointTypeArray[a - 1] == GEOCODED_POINT) {
                        editPoints[b].prevLeg = GEOCODED_LEG
                    } else {
                        editPoints[b].prevLeg = CLICKED_LEG
                    }
                }
                if (a < (pointTypeArray.length - 1)) {
                    if (pointTypeArray[a + 1] == GEOCODED_POINT) {
                        editPoints[b].nextLeg = GEOCODED_LEG
                    } else {
                        editPoints[b].nextLeg = CLICKED_LEG
                    }
                }
                editPoints[b].setMap(map);
                google.maps.event.addListener(editPoints[b], "dragend", function(q) {
                    var n = this;
                    var g = q.latLng;
                    if (((n.prevLeg == CLICKED_LEG) && (n.nextLeg == CLICKED_LEG)) || ((n.prevLeg == undefined) && (n.nextLeg == CLICKED_LEG)) || ((n.prevLeg == CLICKED_LEG) && (n.nextLeg == undefined))) {
                        editVertex(n.vertex, g);
                        drawPolyLine(gLatLngArray, true);
                    } else {
                        var p = google.maps.TravelMode.WALKING;
                        if (n.prevLeg == undefined) {
                            originLatLng = q.latLng
                        } else {
                            if (n.prevLeg == GEOCODED_LEG) {
                                originVertex = editPoints[n.markerIndex - 1].vertex;
                                originLatLng = gLatLngArray[originVertex]
                            } else {
                                originLatLng = q.latLng
                            }
                        }
                        if (n.nextLeg == undefined) {
                            destinatonLatLng = q.latLng
                        } else {
                            if ((n.nextLeg != undefined) && (n.nextLeg == GEOCODED_LEG)) {
                                destinationVertex = editPoints[n.markerIndex + 1].vertex;
                                destinatonLatLng = gLatLngArray[destinationVertex]
                            } else {
                                destinatonLatLng = q.latLng
                            }
                        }
                        var h = {
                            origin: originLatLng,
                            destination: destinatonLatLng,
                            travelMode: p
                        };
                        if (((n.prevLeg != undefined) && (n.prevLeg == GEOCODED_LEG)) && ((n.nextLeg != undefined) && (n.nextLeg == GEOCODED_LEG))) {
                            var m = [];
                            m.push({
                                location: q.latLng,
                                stopover: true
                            });
                            h.waypoints = m
                        }
                        var o = new google.maps.DirectionsService();
                        o.route(h, function(z, t) {
                            if (t == google.maps.DirectionsStatus.OK) {
                                function v(H, G, I) {
                                    var B = [];
                                    yArrayChunk = [];
                                    gLatLngArrayChunk = [];
                                    pointTypeArrayChunk = [];
                                    distancesArrayChunk = [];
                                    for (var K = 0; K < G.length; K++) {
                                        var J = G[K].path;
                                        for (var A = 0; A < J.length - 1; A++) {
                                            B.push(J[A].lng());
                                            yArrayChunk.push(J[A].lat());
                                            gLatLngArrayChunk.push(J[A]);
                                            pointTypeArrayChunk.push(GEOCODED_POINT);
                                            distancesArrayChunk.push(0)
                                        }
                                    }
                                    pointTypeArrayChunk[0] = CLICKED_POINT;
                                    var C = I - H;

                                    function D(S, L, R, P) {
                                        var N = S.slice(0);
                                        S.splice(L, S.length - L);
                                        var M = L + R;
                                        var Q = N.length - M;
                                        var O = N.splice(M, Q);
                                        $.merge(S, P);
                                        $.merge(S, O);
                                        return S
                                    }
                                    xArray = D(xArray, H, C, B);
                                    yArray = D(yArray, H, C, yArrayChunk);
                                    gLatLngArray = D(gLatLngArray, H, C, gLatLngArrayChunk);
                                    pointTypeArray = D(pointTypeArray, H, C, pointTypeArrayChunk);
                                    distancesArray = D(distancesArray, H, C, distancesArrayChunk);
                                    updateDistances(xArray, yArray, EDIT);
                                    resetDistanceMarkers();
                                    prepMarkerArray();
                                    drawMarkers(gLatLngArray)
                                }
                                var r = z.routes[0].legs;
                                var w = n.vertex;
                                var s = (editPoints[n.markerIndex + 1]) ? editPoints[n.markerIndex + 1].vertex : undefined;
                                var u = (editPoints[n.markerIndex - 1]) ? editPoints[n.markerIndex - 1].vertex : undefined;
                                if ((n.prevLeg == undefined) || (n.nextLeg == undefined)) {
                                    if (n.prevLeg == undefined) {
                                        editVertex(n.vertex, g);
                                        v(w, r[0].steps, s)
                                    } else {
                                        if (n.nextLeg == undefined) {
                                            editVertex(n.vertex, g);
                                            v(u, r[0].steps, w)
                                        }
                                    }
                                } else {
                                    if ((n.nextLeg == CLICKED_LEG) && (n.prevLeg == GEOCODED_LEG)) {
                                        editVertex(n.vertex, g);
                                        v(u, r[0].steps, w)
                                    } else {
                                        if ((n.nextLeg == GEOCODED_LEG) && (n.prevLeg == CLICKED_LEG)) {
                                            editVertex(n.vertex, g);
                                            v(w, r[0].steps, s)
                                        } else {
                                            if ((n.nextLeg == GEOCODED_LEG) && (n.prevLeg == GEOCODED_LEG)) {
                                                v(w, r[1].steps, s);
                                                editVertex(n.vertex, g);
                                                v(u, r[0].steps, w)
                                            }
                                        }
                                    }
                                }
                                drawPolyLine(gLatLngArray, true)
                            }
                        })
                    }
                })
            }
        }
    }
}

function shouldUseDirectionsForDrawingLeg(query_flag, array_length) {
    return (array_length > 0 && (!query_flag)) ? true : false;
}

function getCurrentUnits() {
    return METRIC;
}

function getCurrentMultiplier(a) {
    var b = getCurrentUnits();
    var d;
    if (a == DISTANCE) {
        if (b == METRIC) {
            d = 1.609345
        } else {
            d = 1
        }
    }
    return d
}

function returnDistanceInChosenUnits(a) {
    var b = getCurrentMultiplier(DISTANCE);
    return a * b
}
