<?php

$host = "diederich.knight.domains";
$db_name = "diederic_gps_info";
$username = "diederic_dopend";
$password = "Ilovepancakes12";

$con = mysqli_connect($host, $username, $password, $db_name);

// grab the data
$lat = $_POST['Latitude'];
$lon = $_POST['Longitude'];

// no longer needed.  faster to do here
//$time = $_POST['timeOfPing'];

// get correctly formatted date
$time = date("Y-m-d H:i:s");

// create the sql query
$sql = "INSERT INTO gps_info (Latitude, Longitude, timeOfPing) VALUES ('$lat', '$lon', '$time')";
echo 'Value of sql: ' + $sql;

// execute the query
if(mysqli_query($con, $sql))
{
    echo 'Data Submit Successful';      // successfully inserted
}

else{
    echo 'Try again';       // error: didn't connect
}
mysqli_close($con);         // close connection


// $con = new PDO("mysql:host={$host}; dbname={$db_name}", $username, $password);



// echo "Value of lat: " . $lat;

// $sql = "INSERT INTO gps_info (Latitude,Longitude,Time) VALUES ($lat,$lon,$time);";

// $stmt = $con->prepare($sql);
// $stmt->execute();

// $con->close();
?>