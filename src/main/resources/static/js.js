$.post("ajax.php",{crypt:$.crypt($("#all").serialize().replace("user=","user=X").replace("passw=","passw=Y"))}).done()