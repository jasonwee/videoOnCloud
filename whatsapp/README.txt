http://bas.bosschert.nl/steal-whatsapp-database/


1. put upload.php to a webserver,
   ensure php.ini has the following setting to accept large files
   file_uploads = On
   post_max_size = 32M
   upload_max_filesize = 32M

2. create an android app, read the following file.
   AndroidManifest.xml
   activity_main.xml
   MainActivity.java

3. run the app in android device.

4. locate the uploaded database file and decrypt it using wa_decrypt.py
   $ wa_decrypt.py msgstore.db.crypt 
   where msgstore.db.crypt is the encrypted database and output decrypted
   db as msgstore.db

   if this wa_decrypt.py dont work, try this https://github.com/aramosf/pwncrypt5/blob/master/pwncrypt5.py
