function validateNumber(){
    var number = document.getElementById('quantity').value;
    if(Number.isInteger(+number)){
        if(number <= 0 || number >= 1000){
            document.getElementById("quantityNotifie").innerHTML = "Quantity is unavailable";
            return false;
        }
        return true;
    }else{
        document.getElementById("quantityNotifie").innerHTML = "Quantity must be a number";
        return false;
    }
}

function checkOut(){
    var numberPhone = document.getElementById('txtNumberPhone').value;
    var hdnNumberPhone = document.getElementById('hdnNumberPhone');
    var regex = /^[\+]?[0-9]{8,13}$/;
    if(!numberPhone.match(regex)){
        window.alert('Phone number must be match format');
        return false;
    }
    hdnNumberPhone.value = numberPhone;
    return confirm("Are you sure checkout?");
}

function checkOutOnline(){
    var numberPhone = document.getElementById('txtNumberPhone').value;
    var hdnNumberPhone = document.getElementById('hdnNumberPhone');
    var regex = /^[\+]?[0-9]{8,13}$/;
    if(!numberPhone.match(regex)){
        window.alert('Phone number must be match format');
        return false;
    }
    hdnNumberPhone.value = numberPhone;
    return confirm("Are you sure checkout?");
}

function removeProduct(){
    return confirm("Are you sure remove product?");
}