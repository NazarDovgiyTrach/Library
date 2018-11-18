function setPagination() {
    $.get({
        url: '/author/pages',
        success (data) {
            $('.pagination').twbsPagination({
                next: `<i class="fas fa-angle-right"/>`,
                prev: `<i class="fas fa-angle-left"/>`,
                totalPages: data,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    getAuthors(page);
                }
            });
        }
    });
}

function getAuthors(page) {
    $.ajax({
        url: '/author/get/' + page,
        type: 'GET',
        dataType: 'json',
        success(data) {
            let table = document.getElementById("table-body");
            let options = "";
            console.log(data);
            $.each(data, function (index, element) {
                options += createAuthorsTable(element, index, page);
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

function createAuthorsTable(elem, index, page) {
    let number = (index + 1) + ((page - 1) * 5);
    let result = '';
    $.each(elem.books, function (index, element) {
        result += `<a href="/"> ${element.title}</a><br/>`;
    });
    let tab = `<tr>
    <td>${number}</td>
    <td>${elem.name}</td>
    <td>${elem.surname}</td>
    <td>${result}</td>
   </tr>`
    return tab;
}
$(document).ready(function () {
    setPagination();
});

