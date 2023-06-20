
const paymentStart = () => {
  console.log("payment started..");
  let amount = $("#payment_field").val();
  console.log(amount);
  if (amount == "" || amount == null) {
    alert("amount is required !!");
    
    return;
  }
  
  $.ajax(
  {
   url: "/user/create_order",
    data: JSON.stringify({ amount: amount, info: "order_request"}),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
     success:function(response){
     //invoked when success
     console.log(response);
     if(response.status == "created"){
     
     let options={
     key:"rzp_test_y5N0aQVLs4HQFj",
     amount:response.amount,
     currency:"INR",
     name:"Indian Railway",
     description:"Book Ticket",
     image:"https://www.irctc.co.in/nget/assets/images/secondry-logo.png",
     order_id:response.id,
     handler:function(response){
     
     console.log(response.razorpay_payment_id)
     console.log(response.razorpay_order_id)
     console.log(response.razorpay_signature)
     console.log("payment successful !!")
    // alert("congrates !! Payment successful !!");
    
    updatePaymentOnServer(
              response.razorpay_payment_id,
              response.razorpay_order_id,
              "paid"
            );
    
    
     },
     
   prefill: {
            name: "",
            email: "",
            contact: "",
          },
          notes: {
            address: "IndianRailway",
          },
          theme: {
            color: "#3399cc",
          },
     
       };
       
       let rzp = new Razorpay(options);
       rzp.on("payment.failed", function (response) {
           console.log(response.error.code);
           console.log(response.error.description);
           console.log(response.error.source);
           console.log(response.error.step);
           console.log(response.error.reason);
           console.log(response.error.metadata.order_id);
           console.log(response.error.metadata.payment_id);

         alert("Oops payment failed  !! ");
          
        });
       
       rzp.open();
      }
     },
        error:function(error){
     //invoked when error
     console.log(error);
     alert("something went wrong !!");
     },
  });
  };
  
function updatePaymentOnServer(payment_id, order_id, status)
{
  $.ajax({
  url:"/user/update_payment",
   data: JSON.stringify({
      payment_id: payment_id,
      order_id: order_id,
      status: status,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      //success message
      swal(
        "Ticket Booked!",
        "Congrats !! Payment is Successful !!!",
        "success"
      );
    },
    error: function (error) {
      swal(
        "Failed !!",
        "Your payment is successful, but we did not get on server, we will contact you as soon as possible",
        "error"
      );
    },
  });
}
 
 