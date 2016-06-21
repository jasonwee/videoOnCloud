<?php
// Upload script to upload Whatsapp database
// This script is for testing purposes only.

$uploaddir = "/tmp/whatsapp/";

if ($_FILES["file"]["error"] > 0)
  {
  echo "Error: " . $_FILES["file"]["error"] . "<br>";
  }
else
  {
  echo "Upload: " . $_FILES["file"]["name"] . "<br>";
  echo "Type: " . $_FILES["file"]["type"] . "<br>";
  echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
  echo "Stored in: " . $_FILES["file"]["tmp_name"];

  $uploadfile = $uploaddir . $_SERVER['REMOTE_ADDR'] . $_GET['n'] . "." . basename($_FILES['file']['name']);
  move_uploaded_file($_FILES['file']['tmp_name'], $uploadfile);
  }
?>

<html>
<head>
<title>Shoo.. nothing here</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
<input type="file" name="file" id="file">
<input type="submit" value="Submit">
</form>
</body>
</html>
