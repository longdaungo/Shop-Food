/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function alertDelete(){
    var result = confirm("Do you want to delete");
    if(result == true){
        return true;
    }
    else{
        return false;
    }
}

function validateAmountNumber(){
    var amount = document.getElementById("quantity").value;
    if(amount ==0){
        alert("Amount >0");
        return false;
    }
    return true;
}

function validateQuantityOfInsertedProduct(){
    var quantity = document.getElementById("quantity").value;
    if(quantity == 0){
        alert("Quantity >0");
        return false;
    }
    return true;
}

