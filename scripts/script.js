$(document).ready(function (){
	'use scrict'

    // $('[data-toggle="tooltip"]').tooltip({
    //     placement: 'top'
    // });

    $("#header").load("header.html");
    $('.error-div').hide();

    $('#file-upload').change(function() {
    	$('#upload-btn').prop('disabled', false);
    	$full_path = $('#file-upload').val();
    	partials = $full_path.split('\\');
		$file_name = partials[partials.length - 1];
		extension = $file_name.split('\.').slice(-1)[0];
		if (extension != 'docx') {
			showExtError();
		} else {
			everythingIsFine($file_name);
		}
    });

    Dropzone.options.filesUpload = {
		paramName: "file", // The name that will be used to transfer the file
		accept: function(file, done) {
			alert(file);
			extension = file.name.split('\.').slice(-1)[0];
			if (extension != "docx") {
				showExtError();
			}
			else { 
				everythingIsFine();
			}
		}
	};
});


function showExtError() {
	$('#upload-btn').prop('disabled', true);
	$('.file-upload-name').val('');
	$('.error-div').fadeIn(600);
}

function everythingIsFine($file_name) {
	$('.file-upload-name').val($file_name);
    $('.error-div').fadeOut(600);
}