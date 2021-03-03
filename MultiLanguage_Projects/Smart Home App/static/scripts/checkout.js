  $(document).ready(function () {
    $('#same-address').change(function () {
      if (this.checked) {
        $('#billingAddressDiv').hide();
        $('#billingInfoDiv').hide();
      } else {
        $('#billingAddressDiv').show();
        $('#billingInfoDiv').show();
      }
    });
  });
