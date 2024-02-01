getAllItems();
$('#saveItem').click(function () {
    if (checkAllItems()){
        saveItems();
    }else{
        alert("Error");
    }
});
$('#updateItem').click(function () {
    let itemCode = $('#code').val();

    updateItems(itemCode);
});
$('#getAllItem').click(function () {
    getAllItems();
});


$('#deleteItem').click(function () {
    let itemCode = $('#code').val();

    let response = deleteItems(itemCode);
    if (response) {
        Swal.fire({
            position: 'top-up',
            icon: 'success',
            title: 'Item has been Deleted successfully..!',
            showConfirmButton: false,
            timer: 2000
        });
        clearItemTextFields();
        getAllItems();
    } else {
        Swal.fire({
            position: 'top-up',
            icon: 'error',
            title: 'Delete Not Successfull!!',
            showConfirmButton: false,
            timer: 2000
        })
        clearItemTextFields();
    }

});
$('#clearItem').click(function () {
    clearItemTextFields();
});
$("#btnItemSearch").click(function () {
    let id = $('#txtItemSrh').val();
    $('#tblItems').empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/item?option=search&id=" + id,
        success: function (details) {
            console.log(details);
            console.log(details.id);

            let row = `<tr>
                     <td>${details.code}</td>
                     <td>${details.description}</td>
                     <td>${details.unitPrice}</td>
                     <td>${details.qtyOnHand}</td>
                    </tr>`;

            $('#tblItems').append(row);
            setTextFieldss();
            $('#txtItemSrh').val("");


        },
        error: function (error) {

            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No result found..!',
            })
        getAllItems();

        }
    });
});

function saveItems() {
    let itemCode = $('#code').val();
    // if (searchItems(itemCode)==undefined){
        let itemName = $('#name').val();
        let price = $('#price').val();
        let qty = $('#qty').val();
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/pos/item",
        contentType: 'application/json',  // Set content type to JSON
        data: JSON.stringify({
            code: itemCode,
            description: itemName,
            qtyOnHand: qty,
            unitPrice: price
        }),  // Convert data to JSON string

        success: function (details) {
            console.log(details);
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Item has been saved successfully..!',
                showConfirmButton: true,
                timer: 2000
            })
            clearItemTextFields();
            getAllItems();
            loadItemCodes()

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
            if (jqXHR.status == 409) {
                Swal.fire({
                    position: 'center',
                    icon: 'warning',
                    title: jqXHR.responseText,
                    showConfirmButton: true,
                    timer: 2000
                })
            }
        }
    });

    // }else {
    //     clearItemTextFields();
    //     Swal.fire({
    //         icon: 'warning',
    //         title: 'Oops...',
    //         text: 'This Item doesn\'t exist..!',
    //     })
    // }

}

function updateItems(code) {
    if (searchItems(code)) {
        alert("No such Items..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this Items.?");
        if (consent) {
            let item = searchItems(code);
            let itemName = $('#name').val();
            let price = $('#price').val();
            let qty = $('#qty').val();
            $.ajax({
                type: "PUT",
                url: "http://localhost:8080/pos/item",
                contentType: 'application/json',  // Set content type to JSON
                data: JSON.stringify({code: code,  description: itemName, qtyOnHand: qty, unitPrice: price}),  // Convert data to JSON string

                success: function (details) {
                    console.log(details);
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Item has been Updated successfully..!',
                        showConfirmButton: false,
                        timer: 2000
                    })
                    clearItemTextFields();
                    getAllItems();

                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
                }
            });

        }
    }
}

function searchItems(code) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/item?option=search&id=" + code,
        success: function (details) {
            console.log(details);
            return true;


        },
        error: function (error) {
            console.log(error);
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No such Items..please check the ID..!',
            })

        }
    });
}

function deleteItems(itemCode) {
    let consent = confirm("Do you really want to delete this Items.?");
    if (consent) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/pos/item?id=" + itemCode,

            contentType: 'application/json',  // Set content type to JSON
            // Convert data to JSON string

            success: function (details) {
                Swal.fire({
                    position: 'top-up',
                    icon: 'success',
                    title: 'Item has been Deleted successfully..!',
                    showConfirmButton: false,
                    timer: 2000
                })
                clearItemTextFields();
                getAllItems();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("AJAX Error: " + textStatus, errorThrown);
            }
        });
    }

}
function getAllItems() {

    $("#tblItems").empty();


    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/item?option=getAll",
        success: function (details) {
            console.log(details);

            for (let item of details) {
                let row = `<tr>
                     <td>${item.code}</td>
                     <td>${item.description}</td>
                     <td>${item.unitPrice}</td>
                     <td>${item.qtyOnHand}</td>
                    </tr>`;

                $("#tblItems").append(row);
                setTextFields();
                doubleClickItem();
            }

        },
        error: function (error) {

        }
    });



}
function setTextFields() {
    $('#tblItems>tr').click(function () {
        // alert("hi");
        $('#code').val($(this).children(":eq(0)").text());
        $('#name').val($(this).children(":eq(1)").text());
        $('#price').val($(this).children(":eq(2)").text());
        $('#qty').val($(this).children(":eq(3)").text());
        console.log($(this).children(":eq(0)").text())

    });
}
doubleClickItem();
function doubleClickItem() {
    $('#tblItems>tr').dblclick(function () {
        // alert("hi");
        let consent =
            Swal.fire({
                position: 'center',
                icon: 'Warning',
                title: 'Do you really want to Delete this customer.?',
                showConfirmButton: true,
                timer: 2000
            });
        if (consent){
            $(this).remove();
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Removed Successfully',
                showConfirmButton: false,
                timer: 2000
            });
        }


    });
}


