function checkValid() {


    var word = document.forms[0].word.value;
    var description = document.forms[0].description.value;


    if (word == null || word == "") {
        document.getElementById("alert-word").style.display = "block";
        setTimeout(function () {
            document.getElementById("alert-word").style.display = "none";
        }, 3000);
        return false

    } else if (description == null ||description == "") {
        document.getElementById('alert-description').style.display = "block";
        setTimeout(function () {
            document.getElementById('alert-description').style.display = "none";
        }, 3000);
        return false
    } else {
        document.getElementById('alert-succeed').style.display = "block";
        setTimeout(function () {
            document.getElementById('alert-succeed').style.display = "none";
            return true
        }, 3000);

    }

}