/*<![CDATA[*/
document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelector('#alertModal')) {
        var alertModal = new bootstrap.Modal(document.getElementById('alertModal'));
        alertModal.show();
    }
});
/*]]>*/
