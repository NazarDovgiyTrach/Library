const croppedWidth = 100;
const croppedHeight = 100;
let imageBlob = null;
let cropper;
let image;
let name;
let surname;
let title;
let genre;
let year;
let $input;
let nameError;
let surnameError;
let bookError;
let titleError;


function nameIsValid() {
    if (name.value === '') {
        nameError.innerText = 'Name must contain at least 2 characters!';
        nameError.hidden = false;
        return false;
    }
    else {
        if (!/^[A-Z][a-z]{1,30}([\-][A-Z][a-z]{1,30}|)$/.test(name.value)) {
            nameError.innerText = 'Name can contain only latin letters and - sign!';
            nameError.hidden = false;
            return false;
        }
        else {
            nameError.hidden = true;
            return true;
        }
    }
}

function surnameIsValid() {
    if (surname.value === '') {
        surnameError.innerText = 'Surname must contain at least 2 characters!';
        surnameError.hidden = false;
        return false;
    }
    else {
        if (!/^[A-Z][a-z]{1,30}([\-][A-Z][a-z]{1,30}|)$/.test(surname.value)) {
            surnameError.innerText = 'Surname can contain only latin letters and - sign!';
            surnameError.hidden = false;
            return false;
        }
        else {
            surnameError.hidden = true;
            return true;
        }

    }
}

function fileIsValid() {
    if ($input === null) {
        bookError.innerText = 'Choose a book!';
        bookError.hidden = false;
        return false;
    }
    else {
        nameError.hidden = true;
        return true;
    }
}

function titleIsValid() {
    if (title.value === '') {
        titleError.innerText = 'Title must contain at least 2 characters!';
        titleError.hidden = false;
        return false;
    }
    else {
        surnameError.hidden = true;
        return true;
    }
}

function initCropper(file) {
    if (document.getElementById('image-file').files.length === 0) {
        $('#image-editor').addClass('hidden');
        return;
    }
    $('#image-editor').removeClass('hidden');


    image = document.getElementById('image-from-file');
    const imageUrl = URL.createObjectURL(file);
    image.addEventListener('load', () => URL.revokeObjectURL(imageUrl));
    image.src = imageUrl;

    if (cropper != null) {
        cropper.destroy();
        cropper = null;
    }
    cropper = new Cropper(image, {
        aspectRatio: croppedWidth / croppedHeight,
        viewMode: 1,
        dragMode: 'move',
        rotatable: false,
        modal: false,
        background: false,
        crop(event) {
            viewCroppedImage('image-cropped-preview');
        }
    });
    image.addEventListener('zoom', (event) => {
        if ((event.detail.ratio > event.detail.oldRatio) && (event.detail.ratio > 1)) {
            event.preventDefault();
        }
    });
}


function viewCroppedImage(imgId) {
    let canvas = cropper.getCroppedCanvas({
        width: croppedWidth,
        height: croppedHeight
    });
    if (canvas != null) {
        canvas.toBlob(function (blob) {
            let newImg = document.getElementById(imgId);
            const url = URL.createObjectURL(blob);
            newImg.addEventListener('load', () => function () {
                URL.revokeObjectURL(url);
            });
            newImg.src = url;
        });
    }
    else {
        new PNotify({
            title: locales['error'],
            text: locales['imageError'],
            type: 'error',
            styling: 'bootstrap3',
            delay: 3000
        });
        cancel();
    }

}

function apply() {
    viewCroppedImage('image-cropped-preview2');
    $('#image-editor').addClass('hidden');
    cropper.getCroppedCanvas({
        width: croppedWidth,
        height: croppedHeight
    }).toBlob(function (blob) {
        imageBlob = blob;
        cropper.destroy();
        cropper = null;

    });
    document.getElementById('image-file').value = '';

}

function cancel() {
    imageBlob = null;
    cropper.destroy();
    cropper = null;
    $('#image-editor').addClass('hidden');
    document.getElementById('image-file').value = '';
}

function submit(event) {
    event.preventDefault();
    if (nameIsValid() && surnameIsValid() &&
        fileIsValid() && titleIsValid()) {
        let submitButton = $("#submit-button");
        submitButton.attr("disabled", true);
        let authorDTO = {
            'name': name.value,
            'surname': surname.value,
        };
        let ajaxData = new FormData();
        ajaxData.append('authorDTO', new Blob([JSON.stringify(authorDTO)], {type: 'application/json;charset=UTF-8;'}));
        let bookDTO = {
            'title': title.value,
            'genre': genre.value,
            'year': year.value
        };
        ajaxData.append('bookDTO', new Blob([JSON.stringify(bookDTO)], {type: 'application/json;charset=UTF-8;'}));
        ajaxData.append('book', $input.prop('files')[0]);
        if (imageBlob != null) {
            ajaxData.append('image', imageBlob);
        }

        $.ajax({
            url: '/book/add',
            method: "POST",
            data: ajaxData,
            processData: false,
            contentType: false,
            success(response) {
                PNotify.removeAll();
                new PNotify({
                    title: 'Success!',
                    text: response,
                    type: 'success',
                    styling: 'bootstrap3'
                });
                setTimeout('location="/";', 1250);
            },
            error(error) {
                submitButton.attr("disabled", false);
                let result = '';
                $.each(JSON.parse(error.responseText)['errors'], function (index, element) {
                    result += element['defaultMessage'] + '\n';
                });
                new PNotify({
                    title: 'Error!',
                    text: result,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        });
    }

}


function initModalForm() {
    document.getElementById('add-book-form').addEventListener('submit', submit);
    name = document.getElementById('name');
    surname = document.getElementById('surname');
    title = document.getElementById('title');
    year = document.getElementById('year');
    genre = document.getElementById('genre');
    $input = $("#book");
    nameError = document.getElementById('name-error');
    surnameError = document.getElementById('surname-error');
    bookError = document.getElementById('book-error');
    titleError = document.getElementById('title-error');

}

$(document).ready(function () {
    initModalForm();

});

