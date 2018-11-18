document.getElementById('DETECTIVES').addEventListener('click', genreEve1);
document.getElementById('FOR_KIDS').addEventListener('click', genreEve2);
document.getElementById('DOCUMENTARIES').addEventListener('click', genreEve3);
document.getElementById('HOME_AND_FAMILY').addEventListener('click', genreEve4);
document.getElementById('NOVELS').addEventListener('click', genreEve5);
document.getElementById('SCIENTIFIC_AND_EDUCATIONAL').addEventListener('click', genreEve6);
document.getElementById('POETRY_AND_DRAMATURGY').addEventListener('click', genreEve7);
document.getElementById('ADVENTURE').addEventListener('click', genreEve8);
document.getElementById('FANTASTIC').addEventListener('click', genreEve9);
document.getElementById('HUMOR').addEventListener('click', genreEve10);

let currentGenre;

function genreEve1() {
    currentGenre = 'DETECTIVES/';
    setBookGenrePagination(currentGenre);
}

function genreEve2() {
    currentGenre = 'FOR_KIDS/';
    setBookGenrePagination(currentGenre);
}

function genreEve3() {
    currentGenre = 'DOCUMENTARIES/';
    setBookGenrePagination(currentGenre);
}

function genreEve4() {
    currentGenre = 'HOME_AND_FAMILY/';
    setBookGenrePagination(currentGenre);
}

function genreEve5() {
    currentGenre = 'NOVELS/';
    setBookGenrePagination(currentGenre);
}

function genreEve6() {
    currentGenre = 'SCIENTIFIC_AND_EDUCATIONAL/';
    setBookGenrePagination(currentGenre);
}

function genreEve7() {
    currentGenre = 'POETRY_AND_DRAMATURGY/';
    setBookGenrePagination(currentGenre);
}

function genreEve8() {
    currentGenre = 'ADVENTURE/';
    setBookGenrePagination(currentGenre);
}

function genreEve9() {
    currentGenre = 'FANTASTIC/';
    setBookGenrePagination(currentGenre);
}

function genreEve10() {
    currentGenre = 'HUMOR/';
    setBookGenrePagination(currentGenre);
}

function setBookGenrePagination(genre) {
    $.get({
        url: '/book/pages/' + genre,
        success(data) {
            if (data === 0) {
                document.getElementById("table-body").innerHTML = '';
                PNotify.removeAll();
                new PNotify({
                    title: 'No results!',
                    type: 'warning',
                    styling: 'bootstrap3',
                    delay: 1500
                });
            }
            $('.pagination').empty();
            $('.pagination').removeData("twbs-pagination");
            $('.pagination').unbind("page");
            $('.pagination').twbsPagination({
                next: `<i class="fas fa-angle-right"/>`,
                prev: `<i class="fas fa-angle-left"/>`,
                totalPages: data,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    getBooksGenre(page, genre);
                }
            });
        }
    });
}


function getBooksGenre(page, genre) {

    $.ajax({
        url: '/book/get/' + genre + page,
        type: 'GET',
        dataType: 'json',
        success(data) {
            let table = document.getElementById("table-body");
            let tableHead = document.getElementById("table-head");
            tableHead.innerHTML =
                `
               <tr>
                <th scope="col"></th>
                <th scope="col">Number</th>
                <th scope="col">Title</th>
                <th scope="col">Genre</th>
                <th scope="col">Author</th>
                <th scope="col">Year of publication</th>
            </tr>
           `;
            let options = "";
            console.log(data);
            $.each(data, function (index, element) {
                options += createBooksGenreTable(element, index, page);
            });
            table.innerHTML = options;
        },
        error(jqXHR, textStatus, errorThrown) {
            new PNotify({
                title: 'Error',
                type: 'error',
                styling: 'bootstrap3',
                delay: 1000
            });
        },
        timeout: 120000,
    });
}

function createBooksGenreTable(elem, index, page) {
    let number = (index + 1) + ((page - 1) * 5);
    let tab =
        `<tr>
    <td><img src="${elem.imagePath}"/></td> 
    <td>${number}</td>
    <td>${elem.title}</td>
    <td>${elem.genre.replace(/_/g, " ")}</td>
    <td><a href="/authors">${elem.author.name} ${elem.author.surname}</a></td>
    <td>${elem.year}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    <a class="icon icon-shape bg-green text-white rounded-circle shadow" href="/book/download/${elem.id}">
    <i class="fas fa-download mr-2"></i></a></td>
   </tr>`


    return tab;
}



