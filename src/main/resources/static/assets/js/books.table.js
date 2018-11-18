

function setBookPagination() {
    $.get({
        url: '/book/pages',
        success(data) {
            $('.pagination').empty();
            $('.pagination').removeData("twbs-pagination");
            $('.pagination').unbind("page");
            $('.pagination').twbsPagination({
                next: `<i class="fas fa-angle-right"/>`,
                prev: `<i class="fas fa-angle-left"/>`,
                totalPages: data,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    getBooks(page);
                }
            });
        }
    });
}

function getBooks(page) {
    $.ajax({
        url: '/book/get/' + page,
        type: 'GET',
        dataType: 'json',
        success(data) {
            let table = document.getElementById("table-body");
            let options = "";
            console.log(data);
            $.each(data, function (index, element) {
                options += createBooksTable(element, index, page);
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

function createBooksTable(elem, index, page) {
    let number = (index + 1) + ((page - 1) * 5);
    let tab = `<tr>
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

$(document).ready(function () {

    setBookPagination();

});



