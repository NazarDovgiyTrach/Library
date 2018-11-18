function search() {
    let text = document.getElementById('search-book').value;
    $.get({
        url: '/book/search/' + text,
        success(data) {
            if (data.length === 0) {
                document.getElementById("table-body").innerHTML = '';
                new PNotify({
                    title: 'No results!',
                    text: 'Check that the text you entered is correct!',
                    type: 'warn',
                    styling: 'bootstrap3',
                    delay: 1000
                });
            }
            text.innerText = ''
            let pages;
            if (data.length > 5) {
                pages = 1;
            } else {
                pages = (data.length / 5);
            }

            $('#pagination').empty();
            $('#pagination').removeData("twbs-pagination");
            $('#pagination').unbind("page");
            $('#pagination').twbsPagination({
                next: `<i class="fas fa-angle-right"/>`,
                prev: `<i class="fas fa-angle-left"/>`,
                totalPages: pages,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    getResultOfSearch(page, data);
                }
            });
        }
    });
}

function getResultOfSearch(page, data) {
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
        options += createTablesFromSearchResult(element, index, page);
    });
    table.innerHTML = options;

}

function createTablesFromSearchResult(elem, index, page) {
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



