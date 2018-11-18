
function abcSearch(letter) {
    event.preventDefault();
    $.get({
        url: '/author/pages/' + letter,
        success(data) {
            if (data === 0) {
                document.getElementById("table-body").innerHTML='';
                PNotify.removeAll();
                new PNotify({
                    title: 'No results!',
                    type: 'warning',
                    styling: 'bootstrap3',
                    delay:1500
                });

            }
            $('#pagination').empty();
            $('#pagination').removeData("twbs-pagination");
            $('#pagination').unbind("page");
            $('#pagination').twbsPagination({
                next: `<i class="fas fa-angle-right"/>`,
                prev: `<i class="fas fa-angle-left"/>`,
                totalPages: data,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    getAuthorsABC(page, letter);
                }
            });
        }
    });
}
function getAuthorsABC(page, letter) {
    $.ajax({
        url: '/author/get/' + letter + page,
        type: 'GET',
        dataType: 'json',
        success(data) {
            let table = document.getElementById("table-body");
            let tableHead = document.getElementById("table-head");
            tableHead.innerHTML =
                `
            <tr>
            <th scope="col">Number</th>
            <th scope="col">Name</th>
            <th scope="col">Surname</th>
            <th scope="col">List of books</th>
            </tr>
           `;
            let options = "";
            console.log(data);
            $.each(data, function (index, element) {
                options += createAuthorsAbcTable(element, index, page);
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

function createAuthorsAbcTable(elem, index, page) {
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



