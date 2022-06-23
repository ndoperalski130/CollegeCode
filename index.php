<!DOCTYPE html>
<html lang="en">
<script>
var heatmapData;
</script>
<?php 

//  info for connection to the database
$host = "diederich.knight.domains";
$db_name = "diederic_gps_info";
$username = "diederic_dopend";
$password = "Ilovepancakes12";

//  attempt to connect to the db
$con = new PDO("mysql:host={$host}; dbname={$db_name}", $username, $password);

// queue up a query
$query =  "SELECT * FROM gps_info";
$stmt = $con->prepare($query);

// execute the query
$stmt->execute();

// prepare to receive data
$phpArr = array();
$entry = "";

// loop over the query rows to get the data
while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
 {
//     echo "<table>";
//     echo "<tr><td>ID: " . $row['id'] . "</td></tr>";
//     echo "<tr><td>Latitude: " . $row['Latitude'] . "</td></tr>";
//     echo "<tr><td>Longitude: " . $row['Longitude'] . "</td></tr>";
//     echo "<tr><td>Time: " . $row['timeOfPing'] . "</td></tr>";
//     echo "</table>";
     $entry =$row['Latitude'] . " " . $row['Longitude'];
     array_push($phpArr, $entry);
//     var_dump($phpArr);
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

    var lat, lon, point;

    var latNum, lonNum;

    // === some hard coded data :P === //
    heatmapData = [

        //new google.maps.LatLng(44.444534, -88.067152),
        //new google.maps.LatLng(45, -88),
        // location of main hall
        mainHall = new google.maps.LatLng(44.444139, -88.066644)

    ];

    for (var i = 0; i < jsArr.length; i++) {
        //alert(jsArr[i]);
        var mid = jsArr[i].indexOf(" ");        // find index of the space
        //alert(mid);
        lat = jsArr[i].substring(0, mid);                   // first part of lat lon
        lon = jsArr[i].substring(mid, jsArr[i].length);     // second part of lat lon

        latNum = parseFloat(lat);   //' parse the lat
        //alert(latNum);
        lonNum = parseFloat(lon);   // parse the lon
        //alert(lon);
        point = new google.maps.LatLng(latNum, lonNum); // create a new latlng point
        //alert(point);
        heatmapData.push(point);    // push it into the list
    }

    

    function init() {
        var mapOptions = {
            zoom: 17,                   // zoom level
            center: center,             // center of the map
            mapTypeId: "satellite",     // define the default map type
            disableDefaultUI: true,     // disable the default UI for Google Maps
        }
        // create the map
        map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        // sample marker.  shows center
        var marker = new google.maps.Marker({
            map: map,           // map object to use
            position: center,   // where to center the map
        });

        // set heatmap as a new layer on our map with the data
    var heatmap = new google.maps.visualization.HeatmapLayer({
        dissipating: true,  // dissipating  NEEDS to be true otherwise circles will be too dim
        data: heatmapData,  // data we're using
        maxIntensity: 10    // default
    });
        heatmap.setMap(map); // adds the heatmap
        
    }

    
    </script>

</head>

<body>
    <h1>Look at the campus my dude!</h1>

    <section id="sidebar">
        <div id="directions_panel"></div>
    </section>

    <section id="main">
        <div id="map_canvas" style="width: 50%; height: 100%; height: 500px;"></div>
    </section>
    <!-- Gets data -->
    <button type="button" onclick="init();">Get Data</button>
    <br></br>
    <br></br>
    <!-- Gets time constraints -->
    <p> Select time constraints:</p>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src = "https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src = "https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
    $(function()
    {
        // date picker object
        $("#datepicker").datepicker({
            onSelectionChange: function(dateText) {
                console.log("Selected date: "  + dateText);
            }
        });
    });
    </script>
    <form action = "getMethod.php" method="get"> 
    
        <select name = "firstTime" id = "firstTime">
            <?php 
            // create a list of 23 times
            for($i=0; $i < 24; $i++)
            {

                if($i < 10)
                {
                    echo "<option value='$i'> 0$i:00:00 </option>";
                }
                else 
                {
                    echo "<option value='$i'> $i:00:00 </option>";
                }

            }


            ?>
        </select>
        <select name = "endTime" id = "endTime">
            <?php 
            // create the second list of 23 items
            for($i=0; $i < 24; $i++)
            {

                if($i < 10)
                {
                    echo "<option value='$i'> 0$i:00:00 </option>";
                }
                else 
                {
                    echo "<option value='$i'> $i:00:00 </option>";
                }

            }
	    // post values to next page
            $firstTime = $_POST['firstTime'];
            $endTime = $_POST['endTime'];
           // $date = date("Y-m-d",(strtotime($dateTime)));
            $date = $_POST['date'];

	    // only need first two of string
            $firstVal = substr($firstTime, 2);
            $endVal = substr($endTime, 2);

	    // numeric conversion
            $firstTime = strval($firstVal);
            $endTime = strval($endVal);

            if($endTime < $firstTime)
            {
                $errorMessage = "<li>The end time can't be before the start time!</li>";
                echo $errorMessage;
		$endTime = 23;
            }

            ?>
        </select>
        
        <p>Select date: <input type="text"  id="datepicker" name = "date" value = "<?php echo date ('m/d/Y');?> "</p>
	<br></br>
        <input type = "submit" value = "Submit" name = "submit">
    
    
    </p>
    
    </form>
    
    
    <button type = "button" value = "Right now." onclick = "changeDate()">Right now</button>

    <script>
    function changeDate() {
	// get the current date
        var today = new Date();

	// formats the month
	var month = today.getMonth() + 1;
	if(month < 10)
		month = '0' + month;

	// formats the day
	var day = today.getDate();
	if(day < 10)
		day = '0' + day;


	var date = month + '/' + day + '/' + today.getFullYear();
        var time = today.getHours();

	// set elements to be correct
        document.getElementById("firstTime").selectedIndex = time;
	if (time == 23)
		document.getElementById("endTime").selectedIndex = time;
	else
		document.getElementById("endTime").selectedIndex = time + 1;
	document.getElementById("datepicker").value = date; 
	
    }
    </script>

</body>

</html>