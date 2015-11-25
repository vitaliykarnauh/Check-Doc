$(document).ready(function (){
	'use scrict'

    // $('[data-toggle="tooltip"]').tooltip({
    //     placement: 'top'
    // });
    $("#header").load("header.html");
    $('#file-upload').change(function() {
    	$full_path = $('#file-upload').val();
    	partials = $full_path.split('\\');
		$file_name = partials[partials.length - 1];	
		$('.file-upload-name').val($file_name);
    });
});