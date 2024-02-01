generateNextOrderId(function (nextOrderId) {
    $('#oId').val(nextOrderId);
});
$('#Total').text(0.00);
$('#SubTotal').text(0.00);
var date = new Date();
var formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
$('#date').val(formattedDate);
loadCustomerIds();
loadItemCodes();
$('#discount').val(0.00);
$('#Balance').val(0.00);
$('#purchaseOrder').prop("disabled",true);
$('#btnCart').prop("disabled",true);
let oQty;

$('#cmbCust').on('click', function () {
    setCustomerDetails();
  enabledOrDisabledBtn();
  enabledCartBtn()
});


$('#cmbItem').on('click', function () {
    setItemDetails();
    reduceQuanty();
enabledOrDisabledBtn()
    enabledCartBtn()
});
$('#btnCart').on('click', function () {
    $('#tblCart').empty();
    addToCart();

});

$('#purchaseOrder').on('click', function () {
if (parseFloat($('#cash').val())>= parseFloat($('#SubTotal').text())){
    placeOrderB();

    clearPlaceOrderTextFields();
    $('#tblCart').empty();
    cartDetails=[];
    // generateNextOrderId(function (nextOrderId) {
    //     $('#oId').val(nextOrderId);
    // });




}else {
    Swal.fire(
        'Insufficient Credit!',
        'Please Check Cash.....',
        'error'
    )
}
loadOrderDetails();

});
$('#oqty').on("keydown keyup", function (e) {


    enabledOrDisabledBtn();
    enabledCartBtn();
});
$('#discount').on("keydown keyup", function (e) {

    calculateSubTotal();

});
$('#cash').on("keydown keyup", function (e) {

    calculateBalance();
enabledOrDisabledBtn()
});


function loadCustomerIds() {
    $('#cmbCust').empty();
    $('#cmbCust').append(`<option selected disabled>Select Customer</option>`);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/customer?option=getAll",
        success: function (details) {
            console.log(details);
            console.log(details.id);
            for (let customer of details) {
                $('#cmbCust').append(`<option>${customer.id}</option>`);
            }

        },
        error: function (error) {

        }
    });

}

function loadItemCodes() {
    $('#cmbItem').empty();
    $('#cmbItem').append(`<option selected disabled>Select Items</option>`);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/item?option=getAll",
        success: function (details) {
            console.log(details);
            console.log(details.id);
            for (let items of details) {
                $('#cmbItem').append(`<option>${items.code}</option>`);
            }

        },
        error: function (error) {

        }
    });

}

function setCustomerDetails() {



    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/customer?option=search&id=" + $('#cmbCust').val(),
        success: function (details) {
            console.log(details);

            $('#cid').val(details.id);
            $('#cname').val(details.name);
            $('#address').val(details.address);
            $('#salary').val(details.salary);

        },
        error: function (error) {
            console.log(error);

        }
    });
}

function setItemDetails() {

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/item?option=search&id=" + $('#cmbItem').val(),
        success: function (details) {
            console.log(details);

                    $('#itemCode').val(details.code);
                    $('#itemName').val(details.description);
                    $('#paymemt').val(details.unitPrice);
                    $('#qtyH').val(details.qtyOnHand);

        },
        error: function (error) {
            console.log(error);

        }
    });
}

function addToCart() {
    $('#tblCart').empty();
    if (($('#oqty').val().length!=0) && (parseInt($('#oqty').val())<=parseInt($('#qtyH').val())) ){
        let code = $('#itemCode').val();
        let name = $('#itemName').val();
        let price = parseFloat($('#paymemt').val()).toFixed(2);
        oQty = parseInt($('#oqty').val())
        let total = (price * oQty).toFixed(2);
        let exists = isExists($('#cmbItem').val());

        if (exists != null) {
            exists.quantity = exists.quantity + oQty;
            total = (price * exists.quantity).toFixed(2);
            exists.total = total;
        } else {
            var cartTm = Object.assign({}, cartTM);
            cartTm.itemCode = code;
            cartTm.itemName = name;
            cartTm.unitPrice = price;
            cartTm.quantity = oQty;
            cartTm.total = total;
            cartDetails.push(cartTm);
            // for (let i = 0; i < cartDetails.length; i++) {
            //     let row = `<tr>
            //                <td>${cartDetails[i].itemCode}</td>
            //                <td>${cartDetails[i].itemName}</td>
            //                <td>${cartDetails[i].unitPrice}</td>
            //                <td>${cartDetails[i].quantity}</td>
            //                <td>${cartDetails[i].total}</td>
            //               </tr>`;
            //     $('#tblCart').append(row);
            // }
        }
        Swal.fire(
            'Cart Alert!',
            'Cart has been added successfully..!',
            'success'
        )
        reduceQuanty();
        calculateTotal();

        loadAllItems();

        $('#oqty').val("");
    }else {
        Swal.fire(
            'Please Check Quanty!',
            'Cart Added  unSuccessfully..!',
            'error'
        )


        loadAllItems();
    }



}

function isExists(itemCode) {
    for (const item of cartDetails) {
        console.log(item + "obs")
        if (item.itemCode === itemCode) {
            console.log(item.itemCode + "obs")
            return item;
        }
    }
    return null;
}

function loadAllItems() {
    for (let i = 0; i < cartDetails.length; i++) {
        let row = `<tr>
                     <td>${cartDetails[i].itemCode}</td>
                     <td>${cartDetails[i].itemName}</td>
                     <td>${cartDetails[i].unitPrice}</td>
                     <td>${cartDetails[i].quantity}</td>
                     <td>${cartDetails[i].total}</td>
                    </tr>`;
        $('#tblCart').append(row);
    }
}

function getItemDetails() {
    let rows = $("#tblCart").children().length;
    var array = [];
    for (let i = 0; i < rows; i++) {
        let itCode = $("#tblCart").children().eq(i).children(":eq(0)").text();
        let itQty = $("#tblCart").children().eq(i).children(":eq(3)").text();
        let itPrice = $("#tblCart").children().eq(i).children(":eq(4)").text();
        array.push({code: itCode, qty: itQty, price: itPrice});
    }
    return array;
}

// }
function placeOrderB() {

        let orderId = $("#oId").val();
        let customerID = $("#cid").val();
        let orderDate = $("#date").val();
        let orderDetails = getItemDetails();
      console.log(orderId);
    let orderObj ={
        orderId:orderId ,
        customerId: customerID,
        date: orderDate,
    orderDetails:orderDetails};
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/pos/order",
            contentType: 'application/json',
            data: JSON.stringify(orderObj),

            success: function (details) {
                console.log(details);
                // Handle success response
                Swal.fire(
                    'Order Placement successful!',
                    'Order has been placed successfully..!',
                    'success'
                );
                generateNextOrderId(function (nextOrderId) {
                    $('#oId').val(nextOrderId);
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
                Swal.fire(
                    'Order Placement unsuccessful!',
                    'Order has been placed unsuccessfully..!',
                    'error'
                );
            }
        });

}

function reduceQuanty() {
    for (const tm of cartDetails) {
        if (tm.itemCode===$('#cmbItem').val()){
            let newQty = $('#qtyH').val()-oQty;
            $('#qtyH').val(newQty);
        }
    }
}

function calculateTotal() {
    let Total =0;

    for (let i = 0; i <cartDetails.length; i++) {
        Total=Total+parseFloat(cartDetails[i].total)

    }
    $('#Total,#SubTotal').text(Total.toFixed(2));
}
function calculateSubTotal() {
    let subTotal = parseFloat($('#Total').text());
    console.log(subTotal)
    let discount = subTotal*parseFloat($('#discount').val()*0.01);
    console.log(discount)
    let newSubTotal = subTotal-discount;
    console.log(newSubTotal)
    $('#SubTotal').text(newSubTotal.toFixed(2));
}
function calculateBalance() {
    if ($('#cash').val().length!=0 && ($('#SubTotal').text().length!=0)){
        let subTotal = parseFloat($('#SubTotal').text());
        let cash = parseFloat($('#cash').val());
        console.log(cash)
        let balance = cash-subTotal;
        $('#Balance').val(balance.toFixed(2));


    }else {

        $('#Balance').val(0.00);
    }


}
function generateNextOrderId(callback) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/order?option=generateNextId",
        success: function (details) {
            console.log("generate" + details);
            let orderId = details;
            if (orderId != null) {
                let strings = orderId.split("ORD-");
                console.log(orderId);

                let id = parseInt(strings[1]);
                console.log(id);
                ++id;
                let digit = id.toString().padStart(3, '0');

                callback("ORD-" + digit);
            } else {
                callback("ORD-001");
            }
        },
        error: function (error) {
            console.log(error);
            callback("ORD-001");
        }
    });
}
function enabledOrDisabledBtn() {
    if (($('#cmbCust').val() !==null) && ($('#cmbItem').val() !==null) &&  ($('#cash').val().length!=0) ){
        $('#purchaseOrder').prop("disabled",false);
    }else {
        $('#purchaseOrder').prop("disabled",true);
    }
}
function enabledCartBtn() {
    if (($('#cmbCust').val() !==null) && ($('#cmbItem').val() !==null) && ($('#oqty').val().length!=0)  ){
        $('#btnCart').prop("disabled",false);
    }else {
        $('#btnCart').prop("disabled",true);
    }
}