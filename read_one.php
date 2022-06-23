<?php

$host = "localhost";
$db_name = "congestion";
$username = "root";
$password = "";

$con = new PDO("mysql:host={$host}; dbname={$db_name}", $username, $password);

$query =  "SELECT * FROM gps_info";
//$result = mysql_query($query);

$stmt = $con->prepare($query);


//echo "<div>ID: " . $row['id'] . "</div>";
//echo "<div>Latitude: " . $row['Latitude'] . "</div>";
//echo "<div>Longitude: " . $row['Longitude'] . "</div>";
//echo "<div>Time: " . $row['Time'] . "</div>";

$stmt->execute();



while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
{
    echo "<table>";
    echo "<tr><td>ID: " . $row['id'] . "</td></tr>";
    echo "<tr><td>Latitude: " . $row['Latitude'] . "</td></tr>";
    echo "<tr><td>Longitude: " . $row['Longitude'] . "</td></tr>";
    echo "<tr><td>Time: " . $row['Time'] . "</td></tr>";
    echo "</table>";
}




?>