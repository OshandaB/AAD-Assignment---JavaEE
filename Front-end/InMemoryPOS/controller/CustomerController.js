let btnSave = $("#save");
getAllCustomer();

$("#save").click(function () {
    if (checkAll()) {
        saveCustomer();
    } else {
        alert("Error");
    }

});
$("#getAll").click(function () {
    getAllCustomer();
});

$("#update").click(function () {
    let customerId = $('#floatingInput').val();
    updateCustomer(customerId);
});


$("#delete").click(function () {
    let customerId = $('#floatingInput').val();
    deleteCustomer(customerId);
});
$("#clear").click(function () {
    clearTextField();
});
$("#btnSearch").click(function () {

    let id = $('#search').val();
    $('#custTable').empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/customer?option=search&id=" + id,
        success: function (details) {
            console.log(details);
            console.log(details.id);

            let row = `<tr>
                     <td>${details.id}</td>
                     <td>${details.name}</td>
                     <td>${details.address}</td>
                     <td>${details.salary}</td>
                    </tr>`;

            $('#custTable').append(row);
            setTextFieldss();



        },
        error: function (error) {

            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No result found..!',
            })


        }
    });


});


function saveCustomer() {
    let customerId = $('#floatingInput').val();
    // if (searchCustomer(customerId.trim()) == undefined) {

        let customerName = $('#flotingName').val();
        let customerAdddress = $('#floatingAddress').val();
        let customerSalary = $('#floatingSalary').val();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/pos/customer",
            contentType: 'application/json',  // Set content type to JSON
            data: JSON.stringify({
                id: customerId,
                name: customerName,
                address: customerAdddress,
                salary: customerSalary
            }),  // Convert data to JSON string

            success: function (details) {
                console.log(details);
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Customer has been saved successfully..!',
                    showConfirmButton: true,
                    timer: 2000
                })
                clearTextField();
                getAllCustomer();

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


        //
        // getAllCustomer();
        // loadCustomerIds();

    // } else {
    //     clearTextField();
    //     Swal.fire({
    //         icon: 'warning',
    //         title: 'Oops...',
    //         text: 'This customer doesn\'t exist..!',
    //     })
    // }
}

function updateCustomer(id) {
    if (searchCustomer(id)) {

            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No such Customer..please check the ID..!',
            })
    } else {
    let consent = confirm("Do you really want to update this customer.?");
    if (consent) {
        console.log(id);
        let customerId = $('#floatingInput').val();
        let customerName = $('#flotingName').val();
        let customerAddress = $('#floatingAddress').val();
        let customerSalary = $('#floatingSalary').val();
        $.ajax({
            type: "PUT",
            url: "http://localhost:8080/pos/customer",
            contentType: 'application/json',  // Set content type to JSON
            data: JSON.stringify({id: id, name: customerName, address: customerAddress, salary: customerSalary}),  // Convert data to JSON string

            success: function (details) {
                console.log(details);
                Swal.fire({
                    position: 'top-right',
                    icon: 'success',
                    title: 'Customer has been Updated successfully..!',
                    showConfirmButton: false,
                    timer: 2000
                })
                clearTextField();
                getAllCustomer()

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
            }
        });


    }

    }
}

function deleteCustomer(customerId) {
    let consent =
        Swal.fire({
            position: 'center',
            icon: 'Warning',
            title: 'Do you really want to Delete this customer.?',
            showConfirmButton: true,
            timer: 2000
        });
    if (consent) {

        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/pos/customer?id=" + customerId,

            contentType: 'application/json',  // Set content type to JSON
            // Convert data to JSON string

            success: function (details) {
                Swal.fire({
                    position: 'top-up',
                    icon: 'success',
                    title: 'Customer has been Deleted successfully..!',
                    showConfirmButton: false,
                    timer: 2000
                })
                clearTextField();
                getAllCustomer()
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("AJAX Error: " + textStatus, errorThrown);
            }
        });


    }
    return false;
}


function searchCustomer(id) {


    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/customer?option=search&id=" + id,
        success: function (details) {
            console.log(details);
           return true;


        },
        error: function (error) {
            console.log(error);
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No such Customer..please check the ID..!',
            })
                return false;
        }
    });
}


function getAllCustomer() {
    $('#custTable').empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/pos/customer?option=getAll",
        success: function (details) {
            console.log(details);
            console.log(details.id);
            for (let customer of details) {
                let row = `<tr>
                     <td>${customer.id}</td>
                     <td>${customer.name}</td>
                     <td>${customer.address}</td>
                     <td>${customer.salary}</td>
                    </tr>`;

                $('#custTable').append(row);
                setTextFieldss();
            }

        },
        error: function (error) {

        }
    });

}

function setTextFieldss() {

    $('#custTable>tr').click(function () {
        // alert("hi");
        $('#floatingInput').val($(this).children(":eq(0)").text());
        $('#flotingName').val($(this).children(":eq(1)").text());
        $('#floatingAddress').val($(this).children(":eq(2)").text());
        $('#floatingSalary').val($(this).children(":eq(3)").text());
        console.log($(this).children(":eq(0)").text())

    });
}

doubleclick();

function doubleclick() {
    $('#custTable>tr').dblclick(function () {
        // alert("hi");
        let consent =
            Swal.fire({
                position: 'center',
                icon: 'Warning',
                title: 'Do you really want to Delete this customer.?',
                showConfirmButton: true,
                timer: 2000
            });
        if (consent) {
            $(this).remove();
        }


    });
}