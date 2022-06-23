<!DOCTYPE html>
<html lang="en">
<script>
var heatmapData;
</script>

<?php

// submit is set correctly?
if(isset($_GET['submit']))
{
    $firstTime = $_GET['firstTime'];        // get first time
    $endTime = $_GET['endTime'];            // get end time
    $date = $_GET['date'];                  // get date
    //echo "First time: " . $firstTime . ":00:00";
    //echo "End time: " . $endTime . ":00:00";
    //echo 'Date: ' . $date;
}

else
{
	echo "Error: Data didn't send";
}

// connect to db
$host = "diederich.knight.domains";
$db_name = "diederic_gps_info";
$username = "diederic_dopend";
$password = "Ilovepancakes12";

// === for testing purposes === //
 //echo "<br></br>";
 $dateTime = $date . " " . $firstTime;
 $endDateTime = $date . " " . $endTime;
 //echo "First date: " . $dateTime;
 //echo "<br></br>Second date: " . $endDateTime;

// format the forward slash as a dash
$revisedStartTime = str_replace("/", "-", $dateTime);
$revisedEndTime = str_replace("/", "-", $endDateTime);

// === for testing purposes === //
//echo "<br></br>Revised start: " . $revisedStartTime;
//echo "<br></br>Revised end: " . $revisedEndTime;

// string manipulation on dates and time
$editedStartTime = substr($revisedStartTime, 6, 4) . "-" . substr($revisedStartTime, 0, 2) . "-" . substr($revisedStartTime, 3, 2) . " " . $firstTime . ":00:00";
//echo "<br></br>Edited start: " . $editedStartTime;
$editedEndTime = substr($revisedEndTime, 6, 4) . "-" . substr($revisedEndTime, 0, 2) . "-" . substr($revisedEndTime, 3, 2) . " " . $endTime . ":00:00";
//echo "<br></br>Edited end: " . $editedEndTime;

// attempt to connect to the db
$con = new PDO("mysql:host={$host}; dbname={$db_name}", $username, $password);

// prepare query with our new dateTime objects
$query = "SELECT * FROM gps_info WHERE timeofPing > '$editedStartTime' AND timeofPing < '$editedEndTime'";
//echo "<br></br>Value of query: " . $query;

// execute the query
$stmt = $con->prepare($query);
$stmt->execute();

// prepare to get data
$phpArr = array();
$entry = "";

// read data from query
while($row = $stmt->fetch(PDO::FETCH_ASSOC))
{
    $entry =$row['Latitude'] . " " . $row['Longitude'];
     array_push($phpArr, $entry);
     //var_dump($phpArr);
}
?>

<head>
    <meta charset="utf-8" />
    <title>Google Maps Example</title>
    <style type="text/css">
    body {
        font: normal 14px Verdana;
    }

    h1 {
        font-size: 24px;
    }

    h2 {
        font-size: 18px;
    }

    #sidebar {
        float: right;
        width: 30%;
    }

    #main {
        padding-right: 15px;
        height: 100%;
    }

    .infoWindow {
        width: 220px;
    }
    </style>

    <script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBrbZ70Nb8Q-Nxc3N0ontvQ8LXI2GwcUqg&libraries=visualization">
    </script>
    <script type="text/javascript">
    var map;

    var center = new google.maps.LatLng(44.444533, -88.067153); // center of the map
    var mainHall;
    var jsArr = <?php echo json_encode($phpArr);?>;

    var lat, lon, point;    // lat and lon are strings
                            // point is going to be a LatLng object pushed into a list

    var latNum, lonNum;     // will hold double values

    // === some hard coded data :P === //
    heatmapData = [

        //new google.maps.LatLng(44.444534, -88.067152),
        //new google.maps.LatLng(45, -88),
        // location of main hall
        mainHall = new google.maps.LatLng(44.444139, -88.066644)

    ];

    for (var i = 0; i < jsArr.length; i++) {
        //alert(jsArr[i]);
        var mid = jsArr[i].indexOf(" ");        // look for middle
        //alert(mid);
        lat = jsArr[i].substring(0, mid);                   // get lat
        lon = jsArr[i].substring(mid, jsArr[i].length);     // get lon

        latNum = parseFloat(lat);   // parse lat into double
        //alert(latNum);
        lonNum = parseFloat(lon);   // parse lon into  double
        //alert(lon);
        point = new google.maps.LatLng(latNum, lonNum); // create a new LatLng point
        //alert(point);
        heatmapData.push(point);    // push point into list
    }

    // set heatmap as a new layer on our map with the data
    var heatmap = new google.maps.visualization.HeatmapLayer({
        dissipating: true,      // must be TRUE (will be too dim otherwise)
        data: heatmapData,      // data we use for heatmap
        
    });

    function init() {
        var mapOptions = {
            zoom: 17,                   // zoom  level
            center: center,             // center of map
            mapTypeId: "satellite",     // type of map
            disableDefaultUI: true,     // disable default Google Maps UI
            format: "png"
        }
        // create the map
        map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        // sample marker.  shows center
        var marker = new google.maps.Marker({
            map: map,
            position: center,
        });
        heatmap.setMap(map); // adds the heatmap
	//heatmap.set("opacity", heatmap.get("opacity") ? null : 1);
        //heatmap.set("radius", heatmap.get("radius") ? null : 20);

    }
    </script>

</head>

<body>
    <h1>Look at the campus my dude!</h1>

    <section id="sidebar">
        <div id="directions_panel"></div>
    </section>

    <section id="main">
        <div id="map_canvas" style="width: 100%; height: 100%; height: 1000px;"></div>
    </section>

    <p>Latitude: <span id="lat"></span></p>
    <p>Longitude: <span id="lon"></span></p>
    <!-- Gets data -->
    <button type="button" onclick="init();">Get Data</button>
</body>

</html>

